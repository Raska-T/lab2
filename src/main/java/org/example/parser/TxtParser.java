package org.example.parser;

import org.example.model.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TxtParser implements MissionParser {

    public Mission parse(File file) {
        Mission mission = new Mission();

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
                        mission.getSorcerers().add(currentSorcerer);
                    } else if (currentSection.equals("TECHNIQUE")) {
                        currentTechnique = new Techique();
                        mission.getTechiques().add(currentTechnique);
                    } else if (currentSection.equals("CURSE")) {
                        curse = new Curse();
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
                    }
                }
                else if (curse != null && !currentSection.equals("MISSION")) {
                    switch (key) {
                        case "name": curse.setName(value); break;
                        case "threatLevel":
                            try { curse.setThreatLevel(CurseThreatLevel.valueOf(value.toUpperCase())); }
                            catch (IllegalArgumentException e) {}
                            break;
                    }
                }
                else {
                    switch (key) {
                        case "missionId": mission.setMissionID(value); break;
                        case "date": mission.setDate(value); break;
                        case "location": mission.setLocation(value); break;
                        case "outcome":
                            try { mission.setOutcome(Outcome.valueOf(value.toUpperCase())); }
                            catch (IllegalArgumentException e) {}
                            break;
                        case "damageCost":
                            try { mission.setDamageCost(Integer.parseInt(value)); }
                            catch (NumberFormatException e) {}
                            break;
                        case "note": mission.setInfo(value); break;
                    }
                }
            }

            if (curse != null) {
                mission.setCurse(curse);
            }
            else{
                Curse defaultCurse = new Curse();
                defaultCurse.setName("Неизвестно");
                defaultCurse.setThreatLevel(CurseThreatLevel.HIGH);
                mission.setCurse(defaultCurse);
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return mission;
    }
}