package org.example.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.example.model.*;

import java.io.File;

public class YamlParser implements MissionParser {

    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    public Mission parse(File file) {
        Mission mission = new Mission();

        try {
            JsonNode root = objectMapper.readTree(file);


            mission.setMissionID(getText(root, "missionId"));
            mission.setDate(getText(root, "date"));
            mission.setLocation(getText(root, "location"));

            String outcomeStr = getText(root, "outcome");
            if (!outcomeStr.isEmpty()) {
                mission.setOutcome(Outcome.valueOf(outcomeStr));
            }

            if (root.has("curse")) {
                JsonNode curseNode = root.get("curse");
                Curse curse = new Curse();
                curse.setName(getText(curseNode, "name"));

                String threatLevelStr = getText(curseNode, "threatLevel");
                if (!threatLevelStr.isEmpty()) {
                    curse.setThreatLevel(CurseThreatLevel.valueOf(threatLevelStr));
                }
                mission.setCurse(curse);
            }
            else{
                Curse defaultCurse = new Curse();
                defaultCurse.setName("Неизвестно");
                defaultCurse.setThreatLevel(CurseThreatLevel.HIGH);
                mission.setCurse(defaultCurse);
            }

            if (root.has("damageCost")) {
                mission.setDamageCost(root.get("damageCost").asInt());
            }

            if (root.has("sorcerers")) {
                JsonNode sorcerersNode = root.get("sorcerers");
                for (JsonNode sNode : sorcerersNode) {
                    Sorcerer sorcerer = new Sorcerer();
                    sorcerer.setName(getText(sNode, "name"));
                    sorcerer.setRank(getText(sNode, "rank"));
                    mission.getSorcerers().add(sorcerer);
                }
            }

            if (root.has("techniques")) {
                JsonNode techniquesNode = root.get("techniques");
                for (JsonNode tNode : techniquesNode) {
                    Techique technique = new Techique();
                    technique.setName(getText(tNode, "name"));
                    technique.setType(getText(tNode, "type"));
                    technique.setOwner(getText(tNode, "owner"));
                    if (tNode.has("damage")) {
                        technique.setDamage(tNode.get("damage").asInt());
                    }
                    mission.getTechiques().add(technique);
                }
            }

            if (root.has("comment")) {
                mission.setInfo(root.get("comment").asText());
            }

            if (root.has("economicAssessment")) {
                mission.addExtendedData("economicAssessment", root.get("economicAssessment"));
            }
            if (root.has("enemyActivity")) {
                mission.addExtendedData("enemyActivity", root.get("enemyActivity"));
            }
            if (root.has("environmentConditions")) {
                mission.addExtendedData("environmentConditions", root.get("environmentConditions"));
            }

        } catch (Exception e) {
            System.out.println("Ошибка парсинга YAML: " + e.getMessage());
        }

        return mission;
    }

    private String getText(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : "";
    }
}