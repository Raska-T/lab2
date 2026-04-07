package org.example;

import org.example.*;
import org.example.*;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static MIssionStorage storage = MIssionStorage.getInstance();
    private static MissionLogic logger = MissionLogic.getInstance();
    private static Parsers factory = new Parsers();

    public static void main(String[] args) {

        loadHistoryIntoMemory();

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    loadMissionFromFile();
                    break;
                case "2":
                    viewAllMissions();
                    break;
                case "3":
                    viewMissionDetails();
                    break;
                case "4":
                    System.out.println("Конец работы");
                    return;
            }
        }
    }

    private static void printMenu() {
        System.out.println("МЕНЮ");
        System.out.println("1. Загрузить миссию из файла");
        System.out.println("2. Показать все загруженные миссии");
        System.out.println("3. Просмотреть детали миссии");
        System.out.println("4. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void loadHistoryIntoMemory() {
        List<Mission> history = logger.loadHistory();
        for (Mission mission : history) {
            storage.addMission(mission);
        }
        System.out.println("Загружено " + history.size() + " миссий из истории");
    }

    private static void loadMissionFromFile() {
        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);

        try {
            MissionParser parser = factory.getParser(file);
            if (parser == null) {
                System.out.println("Неподдерживаемый формат файла");
                return;
            }

            Mission mission = parser.parse(file);

            storage.addMission(mission);
            logger.saveMission(mission);

            System.out.println("Миссия успешно загружена");
            mission.printMission();

        } catch (Exception e) {
            System.out.println("Ошибка при загрузке миссии: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void viewAllMissions() {
        List<Mission> missions = storage.getAllMissions();

        if (missions.isEmpty()) {
            System.out.println("Нет загруженных миссий");
            return;
        }

        System.out.println("все загруженные миссии");

        for (int i = 0; i < missions.size(); i++) {
            Mission m = missions.get(i);
            System.out.printf("║ %d. %-12s | %-12s | %-25s | %-8s║%n",
                    i + 1,
                    m.getMissionID(),
                    m.getDate(),
                    m.getLocation().length(),
                    m.getOutcome());
        }
    }

    private static void viewMissionDetails() {
        List<Mission> missions = storage.getAllMissions();

        if (missions.isEmpty()) {
            System.out.println("Нет загруженных миссий");
            return;
        }

        viewAllMissions();

        System.out.print("Введите номер миссии для просмотра: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < missions.size()) {
                Mission mission = missions.get(index);
                mission.printMission();
            } else {
                System.out.println("Неверный номер миссии");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите число");
        }
    }
}