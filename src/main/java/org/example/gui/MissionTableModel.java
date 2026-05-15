package org.example.gui;

import org.example.entity.MissionEntity;
import org.example.entity.SorcererEntity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MissionTableModel extends AbstractTableModel {
    private final String[] columns = {"ID", "Локация", "Проклятье", "Статус", "Участники"};
    private List<MissionEntity> missions = new ArrayList<>();

    public void setMissions(List<MissionEntity> missions) {
        this.missions = missions;
        fireTableDataChanged();
    }

    public MissionEntity getMission(int row) {
        return missions.get(row);
    }

    @Override
    public int getRowCount() { return missions.size(); }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public String getColumnName(int col) { return columns[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        MissionEntity m = missions.get(row);
        switch (col) {
            case 0: return m.getMissionId();
            case 1: return m.getLocation();
            case 2: return m.getCurse() != null ? m.getCurse().getName() : "Нет";
            case 3: return m.getOutcome();
            case 4: return m.getSorcerers().stream().map(SorcererEntity::getName).collect(Collectors.joining(", "));
            default: return "";
        }
    }
}