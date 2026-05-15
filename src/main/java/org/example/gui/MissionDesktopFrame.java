package org.example.gui;

import org.example.entity.MissionEntity;
import org.example.entity.SorcererEntity;
import org.example.service.MissionService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class MissionDesktopFrame extends JFrame {
    private final MissionService missionService;
    private final MissionTableModel tableModel = new MissionTableModel();
    private final JTable table;

    public MissionDesktopFrame(MissionService missionService) {
        this.missionService = missionService;

        setTitle("Архив миссий");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton loadBtn = new JButton("Загрузить миссию");
        JButton reportBtn = new JButton("Отчет");
        JButton refreshBtn = new JButton("Обновить");
        JButton editBtn = new JButton("Изменить");

        loadBtn.addActionListener(e -> loadMission());
        reportBtn.addActionListener(e -> showReport());
        refreshBtn.addActionListener(e -> refreshMissions());
        editBtn.addActionListener(e -> editMission());

        panel.add(loadBtn);
        panel.add(reportBtn);
        panel.add(editBtn);
        panel.add(refreshBtn);
        add(panel, BorderLayout.SOUTH);

        refreshMissions();
    }

    private void refreshMissions() {
        tableModel.setMissions(missionService.getAllMissions());
    }

    private void loadMission() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                MultipartFile multipartFile = new MockMultipartFile(
                        file.getName(),
                        file.getName(),
                        "application/octet-stream",
                        new FileInputStream(file)
                );
                missionService.saveMission(multipartFile);
                refreshMissions();
                JOptionPane.showMessageDialog(this, "Миссия загружена!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка: " + ex.getMessage());
            }
        }
    }

    private void showReport() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Выберите миссию");
            return;
        }

        String missionId = tableModel.getMission(row).getMissionId();

        String[] options = {"Полный отчет", "Краткий отчет"};
        int choice = JOptionPane.showOptionDialog(this,
                "Выберите тип отчета для миссии " + missionId,
                "Отчет",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        String type;
        if (choice == 0) {
            type = "full";
        } else if (choice == 1) {
            type = "small";
        } else {
            return;
        }

        try {
            String report = missionService.generateReport(missionId, type);
            JTextArea textArea = new JTextArea(report);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(800, 600));
            JOptionPane.showMessageDialog(this, scrollPane, "Отчет по миссии " + missionId, JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + ex.getMessage());
        }
    }

    private void editMission() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Выберите миссию для редактирования");
            return;
        }

        MissionEntity mission = tableModel.getMission(row);
        String oldMissionId = mission.getMissionId();

        JTextField missionIdField = new JTextField(mission.getMissionId(), 20);
        JTextField locationField = new JTextField(mission.getLocation(), 20);
        JTextField curseField = new JTextField(mission.getCurse() != null ? mission.getCurse().getName() : "", 20);

        StringBuilder sorcerersNames = new StringBuilder();
        for (SorcererEntity s : mission.getSorcerers()) {
            if (sorcerersNames.length() > 0) sorcerersNames.append(", ");
            sorcerersNames.append(s.getName());
        }
        JTextField sorcerersField = new JTextField(sorcerersNames.toString(), 20);

        JComboBox<String> outcomeBox = new JComboBox<>(new String[]{"SUCCESS", "FAILURE", "PARTIAL_SUCCESS", "UNKNOWN"});
        outcomeBox.setSelectedItem(mission.getOutcome());

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("ID миссии:"));
        panel.add(missionIdField);
        panel.add(new JLabel("Локация:"));
        panel.add(locationField);
        panel.add(new JLabel("Проклятье:"));
        panel.add(curseField);
        panel.add(new JLabel("Участники (через запятую):"));
        panel.add(sorcerersField);
        panel.add(new JLabel("Статус:"));
        panel.add(outcomeBox);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Редактирование миссии " + oldMissionId,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String newMissionId = missionIdField.getText().trim();
                String location = locationField.getText().trim();
                String curseName = curseField.getText().trim();
                String sorcerers = sorcerersField.getText().trim();
                String outcome = (String) outcomeBox.getSelectedItem();

                if (newMissionId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID миссии не может быть пустым!");
                    return;
                }

                if (location.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Локация не может быть пустой!");
                    return;
                }

                if (!newMissionId.equals(oldMissionId)) {
                    try {
                        missionService.getMissionById(newMissionId);
                        JOptionPane.showMessageDialog(this, "Миссия с ID '" + newMissionId + "' уже существует!");
                        return;
                    } catch (Exception e) {}
                }

                missionService.updateMissionWithId(oldMissionId, newMissionId, curseName, sorcerers, outcome, location);
                refreshMissions();
                JOptionPane.showMessageDialog(this, "Миссия обновлена!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка: " + ex.getMessage());
            }
        }
    }
}