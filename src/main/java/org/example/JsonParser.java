package org.example;

import org.example.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonParser implements MissionParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mission parse(File file) {
        Mission mission = new Mission();

        try {
            JsonNode root = objectMapper.readTree(file);

            mission.setMissionID(getText(root, "missionId"));
            mission.setDate(getText(root, "date"));
            mission.setLocation(getText(root, "location"));
            mission.setOutcome(getText(root, "outcome"));
            mission.setDamageCost(root.has("damageCost") ? root.get("damageCost").asInt() : 0);


            if (root.has("curse")) {
                JsonNode curseNode = root.get("curse");
                Curse curse = new Curse();
                curse.setName(getText(curseNode, "name"));
                curse.setThreatLevel(getText(curseNode, "threatLevel"));
                mission.setCurse(curse);
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
                    technique.setDamage(tNode.has("damage") ? tNode.get("damage").asInt() : 0);
                    mission.getTechiques().add(technique);
                }
            }


            if (root.has("comment")) {
                mission.setInfo(root.get("comment").asText());
            }

        } catch (Exception e) {
            System.out.println("Ошибка парсинга JSON: " + e.getMessage());
        }

        return mission;
    }

    private String getText(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : "";
    }
}