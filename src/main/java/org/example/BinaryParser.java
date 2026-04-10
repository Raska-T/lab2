package org.example;

import java.io.*;
import java.nio.file.*;

public class BinaryParser implements MissionParser {

    public Mission parse(File file) {
        MissionBuilders builder = new MissionBuilders();

        try {
            byte[] data = Files.readAllBytes(file.toPath());
            String content = new String(data, "UTF-8");

            if (content.startsWith("\uFEFF")) {
                content = content.substring(1);
            }

            String[] lines = content.split("\n");

            for (String line : lines) {
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 0) continue;

                String command = parts[0].trim();

                switch (command) {
                    case "MISSION_CREATED":
                        if (parts.length >= 4) {
                            builder.setMissionId(parts[1].trim());
                            builder.setDate(parts[2].trim());
                            builder.setLocation(parts[3].trim());
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
                            builder.setCurse(curse);
                        }
                        break;

                    case "SORCERER_ASSIGNED":
                        if (parts.length >= 3) {
                            Sorcerer sorcerer = new Sorcerer();
                            sorcerer.setName(parts[1].trim());
                            sorcerer.setRank(parts[2].trim());
                            builder.addSorcerer(sorcerer);
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
                            builder.addTechnique(technique);
                        }
                        break;

                    case "MISSION_RESULT":
                        if (parts.length >= 2) {
                            String outcomeStr = parts[1].trim().toUpperCase();
                            try {
                                builder.setOutcome(Outcome.valueOf(outcomeStr));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Неизвестный outcome: '" + outcomeStr + "'");
                            }
                            if (parts.length >= 3 && parts[2].contains("damageCost=")) {
                                String damageStr = parts[2].substring(parts[2].indexOf("=") + 1).trim();
                                try {
                                    builder.setDamageCost(Integer.parseInt(damageStr));
                                } catch (NumberFormatException e) {
                                    System.out.println("Ошибка парсинга damageCost: " + damageStr);
                                }
                            }
                        }
                        break;

                    case "TIMELINE_EVENT":
                    case "ENEMY_ACTION":
                    case "CIVILIAN_IMPACT":
                    default:
                        builder.addExtendedData(command, line);
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения бинарного файла: " + e.getMessage());
        }

        return builder.build();
    }
}