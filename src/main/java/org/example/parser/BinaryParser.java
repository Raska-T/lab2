package org.example.parser;

import org.example.model.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BinaryParser implements MissionParser {

    public Mission parse(File file) {
        Mission mission = new Mission();

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            for (String line : lines) {
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 0) continue;

                String command = parts[0].trim();

                switch (command) {
                    case "MISSION_CREATED":
                        if (parts.length >= 4) {
                            mission.setMissionID(parts[1].trim());
                            mission.setDate(parts[2].trim());
                            mission.setLocation(parts[3].trim());
                        }
                        break;

                    case "CURSE_DETECTED":
                        if (parts.length >= 3) {
                            Curse curse = new Curse();
                            curse.setName(parts[1].trim());
                            String threatLevelStr = parts[2].trim().toUpperCase();
                            try {
                                curse.setThreatLevel(CurseThreatLevel.valueOf(threatLevelStr));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Неизвестный уровень угрозы: '" + threatLevelStr + "', используем HIGH");
                                curse.setThreatLevel(CurseThreatLevel.HIGH);
                            }
                            mission.setCurse(curse);
                        }
                        break;

                    case "SORCERER_ASSIGNED":
                        if (parts.length >= 3) {
                            Sorcerer sorcerer = new Sorcerer();
                            sorcerer.setName(parts[1].trim());
                            sorcerer.setRank(parts[2].trim());
                            mission.getSorcerers().add(sorcerer);
                        }
                        break;

                    case "TECHNIQUE_USED":
                        if (parts.length >= 5) {
                            Techique technique = new Techique();
                            technique.setName(parts[1].trim());
                            technique.setType(parts[2].trim());
                            technique.setOwner(parts[3].trim());
                            try {
                                technique.setDamage(Integer.parseInt(parts[4].trim()));
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка парсинга damage: " + parts[4]);
                                technique.setDamage(0);
                            }
                            mission.getTechiques().add(technique);
                        }
                        break;

                    case "MISSION_RESULT":
                        if (parts.length >= 2) {
                            String outcomeStr = parts[1].trim().toUpperCase();
                            try {
                                mission.setOutcome(Outcome.valueOf(outcomeStr));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Неизвестный outcome: '" + outcomeStr + "'");
                            }
                            if (parts.length >= 3 && parts[2].contains("damageCost=")) {
                                String damageStr = parts[2].substring(parts[2].indexOf("=") + 1).trim();
                                try {
                                    mission.setDamageCost(Integer.parseInt(damageStr));
                                } catch (NumberFormatException e) {
                                    System.out.println("Ошибка парсинга damageCost: " + damageStr);
                                }
                            }
                        }
                        break;

                    default:
                        System.out.println("Неизвестная команда: " + command);
                        break;
                }
            }

            if (mission.getCurse() == null){
                Curse defaultCurse = new Curse();
                defaultCurse.setName("Неизвестно");
                defaultCurse.setThreatLevel(CurseThreatLevel.HIGH);
                mission.setCurse(defaultCurse);
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения бинарного файла: " + e.getMessage());
        }

        return mission;
    }
}