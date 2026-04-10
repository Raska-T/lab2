package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;

public class YamlParser implements MissionParser {

    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    public Mission parse(File file) {
        MissionBuilders builder = new MissionBuilders();

        try {
            JsonNode t = objectMapper.readTree(file);

            builder.setMissionId(getText(t, "missionId"));
            builder.setDate(getText(t, "date"));
            builder.setLocation(getText(t, "location"));

            String outcomeStr = getText(t, "outcome");
            if (!outcomeStr.isEmpty()) {
                builder.setOutcome(Outcome.valueOf(outcomeStr));
            }

            if (t.has("curse")) {
                JsonNode curseNode = t.get("curse");
                Curse curse = new Curse();
                curse.setName(getText(curseNode, "name"));

                String threatLevelStr = getText(curseNode, "threatLevel");
                if (!threatLevelStr.isEmpty()) {
                    curse.setThreatLevel(CurseThreatLevel.valueOf(threatLevelStr));
                }

                builder.setCurse(curse);
            }

            if (t.has("sorcerers")) {
                JsonNode sorcerersNode = t.get("sorcerers");
                for (JsonNode sNode : sorcerersNode) {
                    Sorcerer sorcerer = new Sorcerer();
                    sorcerer.setName(getText(sNode, "name"));
                    sorcerer.setRank(getText(sNode, "rank"));
                    builder.addSorcerer(sorcerer);
                }
            }

            if (t.has("techniques")) {
                JsonNode techniquesNode = t.get("techniques");
                for (JsonNode tNode : techniquesNode) {
                    Techique technique = new Techique();
                    technique.setName(getText(tNode, "name"));
                    technique.setType(getText(tNode, "type"));
                    technique.setOwner(getText(tNode, "owner"));
                    if (tNode.has("damage")) {
                        technique.setDamage(tNode.get("damage").asInt());
                    }
                    builder.addTechnique(technique);
                }
            }

            if (t.has("comment")) {
                builder.setInfo(t.get("comment").asText());
            }

            if (t.has("economicAssessment")) {
                builder.addExtendedData("economicAssessment", t.get("economicAssessment"));
            }
            if (t.has("enemyActivity")) {
                builder.addExtendedData("enemyActivity", t.get("enemyActivity"));
            }
            if (t.has("environmentConditions")) {
                builder.addExtendedData("environmentConditions", t.get("environmentConditions"));
            }

        } catch (Exception e) {
            System.out.println("Ошибка парсинга YAML: " + e.getMessage());
        }

        return builder.build();
    }

    private String getText(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : "";
    }
}