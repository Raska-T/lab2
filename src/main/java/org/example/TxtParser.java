package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.regex.*;

public class TxtParser implements MissionParser {

    private String getValue(String line) {
        int colonIndex = line.indexOf(':');
        if (colonIndex >= 0 && colonIndex < line.length() - 1) {
            return line.substring(colonIndex + 1).trim();
        }
        return "";
    }

    public Mission parse(File file) {
        Mission mission = new Mission();

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            for (String line : lines) {
                if (line.isEmpty()) continue;

                if (line.startsWith("missionId:")) {
                    mission.setMissionID(getValue(line));
                }

                else if (line.startsWith("date:")) {
                    mission.setDate(getValue(line));
                }

                else if (line.startsWith("location:")) {
                    mission.setLocation(getValue(line));
                }

                else if (line.startsWith("outcome:")) {
                    mission.setOutcome(getValue(line));
                }

                else if (line.startsWith("damageCost:")) {
                    mission.setDamageCost(Integer.parseInt(getValue(line)));
                }

                else if (line.startsWith("note:")) {
                    mission.setInfo(getValue(line));
                }

                else if (line.startsWith("curse.name:")) {
                    if (mission.getCurse() == null) mission.setCurse(new Curse());
                    mission.getCurse().setName(getValue(line));
                }

                else if (line.startsWith("curse.threatLevel:")) {
                    if (mission.getCurse() == null) mission.setCurse(new Curse());
                    mission.getCurse().setThreatLevel(getValue(line));
                }

                else if (line.matches("sorcerer\\[\\d+\\]\\.name:.*")) {
                    Sorcerer s = new Sorcerer();
                    s.setName(getValue(line));
                    mission.getSorcerers().add(s);
                }

                else if (line.matches("sorcerer\\[\\d+\\]\\.rank:.*")) {
                    Pattern p = Pattern.compile("sorcerer\\[(\\d+)\\]\\.rank:(.*)");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int index = Integer.parseInt(m.group(1));
                        String rank = m.group(2).trim();
                        if (mission.getSorcerers().size() > index) {
                            mission.getSorcerers().get(index).setRank(rank);
                        }
                    }
                }

                else if (line.matches("technique\\[\\d+\\]\\.name:.*")) {
                    Techique t = new Techique();
                    t.setName(getValue(line));
                    mission.getTechiques().add(t);
                }

                else if (line.matches("technique\\[\\d+\\]\\.type:.*")) {
                    Pattern p = Pattern.compile("technique\\[(\\d+)\\]\\.type:(.*)");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int index = Integer.parseInt(m.group(1));
                        String type = m.group(2).trim();
                        if (mission.getTechiques().size() > index) {
                            mission.getTechiques().get(index).setType(type);
                        }
                    }
                }

                else if (line.matches("technique\\[\\d+\\]\\.owner:.*")) {
                    Pattern p = Pattern.compile("technique\\[(\\d+)\\]\\.owner:(.*)");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int index = Integer.parseInt(m.group(1));
                        String owner = m.group(2).trim();
                        if (mission.getTechiques().size() > index) {
                            mission.getTechiques().get(index).setOwner(owner);
                        }
                    }
                }

                else if (line.matches("technique\\[\\d+\\]\\.damage:.*")) {
                    Pattern p = Pattern.compile("technique\\[(\\d+)\\]\\.damage:(.*)");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int index = Integer.parseInt(m.group(1));
                        int damage = Integer.parseInt(m.group(2).trim());
                        if (mission.getTechiques().size() > index) {
                            mission.getTechiques().get(index).setDamage(damage);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return mission;
    }

}