package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TxtParser implements MissionParser {

    public Mission parse(File file) {
        MissionBuilders builder = new MissionBuilders();

        Curse curse = null;
        Sorcerer currentSorcerer = null;
        Techique currentTechnique = null;
        String currentSection = "";

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line.substring(1, line.length() - 1);
                    currentSorcerer = null;
                    currentTechnique = null;

                    if (currentSection.equals("SORCERER")) {
                        currentSorcerer = new Sorcerer();
                        builder.addSorcerer(currentSorcerer);
                    } else if (currentSection.equals("TECHNIQUE")) {
                        currentTechnique = new Techique();
                        builder.addTechnique(currentTechnique);
                    } else if (currentSection.equals("CURSE")) {
                        curse = new Curse();
                        builder.setCurse(curse);
                    }
                    continue;
                }

                int equalIndex = line.indexOf('=');
                if (equalIndex <= 0) continue;

                String key = line.substring(0, equalIndex).trim();
                String value = line.substring(equalIndex + 1).trim();

                if (currentSorcerer != null) {
                    switch (key) {
                        case "name": currentSorcerer.setName(value); break;
                        case "rank": currentSorcerer.setRank(value); break;
                        default: builder.addExtendedData(currentSection + "." + key, value);
                    }
                }
                else if (currentTechnique != null) {
                    switch (key) {
                        case "name": currentTechnique.setName(value); break;
                        case "type": currentTechnique.setType(value); break;
                        case "owner": currentTechnique.setOwner(value); break;
                        case "damage":
                            try { currentTechnique.setDamage(Integer.parseInt(value)); }
                            catch (NumberFormatException e) {}
                            break;
                        default: builder.addExtendedData(currentSection + "." + key, value);
                    }
                }
                else if (curse != null && !currentSection.equals("MISSION")) {
                    switch (key) {
                        case "name": curse.setName(value); break;
                        case "threatLevel":
                            try { curse.setThreatLevel(CurseThreatLevel.valueOf(value.toUpperCase())); }
                            catch (IllegalArgumentException e) {}
                            break;
                        default: builder.addExtendedData("curse." + key, value);
                    }
                }
                else {
                    switch (key) {
                        case "missionId": builder.setMissionId(value); break;
                        case "date": builder.setDate(value); break;
                        case "location": builder.setLocation(value); break;
                        case "outcome":
                            try { builder.setOutcome(Outcome.valueOf(value.toUpperCase())); }
                            catch (IllegalArgumentException e) {}
                            break;
                        case "damageCost":
                            try { builder.setDamageCost(Integer.parseInt(value)); }
                            catch (NumberFormatException e) {}
                            break;
                        case "note": builder.setInfo(value); break;
                        default: builder.addExtendedData(key, value);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return builder.build();
    }
}