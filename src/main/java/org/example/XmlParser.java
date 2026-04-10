package org.example;

import org.example.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class XmlParser implements MissionParser {

    public Mission parse(File file) {
        MissionBuilders mission = new MissionBuilders();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            mission.setMissionId(getTagValue("missionId", document));
            mission.setDate(getTagValue("date", document));
            mission.setLocation(getTagValue("location", document));
            String outcomeStr = getTagValue("outcome", document);
            if (!outcomeStr.isEmpty()) {
                mission.setOutcome(Outcome.valueOf(outcomeStr));
            }

            String damageCost = getTagValue("damageCost", document);
            if (!damageCost.isEmpty()) {
                mission.setDamageCost(Integer.parseInt(damageCost));
            }

            NodeList curseNodes = document.getElementsByTagName("curse");
            if (curseNodes.getLength() > 0) {
                Element curseElement = (Element) curseNodes.item(0);
                Curse curse = new Curse();
                curse.setName(getElementValue(curseElement, "name"));
                String threatLevelStr = getElementValue(curseElement, "threatLevel");
                if (!threatLevelStr.isEmpty()) {
                    curse.setThreatLevel(CurseThreatLevel.valueOf(threatLevelStr));
                }
                mission.setCurse(curse);
            }

            NodeList sorcererNodes = document.getElementsByTagName("sorcerer");
            for (int i = 0; i < sorcererNodes.getLength(); i++) {
                Element sElement = (Element) sorcererNodes.item(i);
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(getElementValue(sElement, "name"));
                sorcerer.setRank(getElementValue(sElement, "rank"));
                mission.getSorcerers().add(sorcerer);
            }

            NodeList techniqueNodes = document.getElementsByTagName("technique");
            for (int i = 0; i < techniqueNodes.getLength(); i++) {
                Element tElement = (Element) techniqueNodes.item(i);
                Techique technique = new Techique();
                technique.setName(getElementValue(tElement, "name"));
                technique.setType(getElementValue(tElement, "type"));
                technique.setOwner(getElementValue(tElement, "owner"));

                String damage = getElementValue(tElement, "damage");
                if (!damage.isEmpty()) {
                    technique.setDamage(Integer.parseInt(damage));
                }
                mission.getTechiques().add(technique);
            }

            NodeList commentNodes = document.getElementsByTagName("comment");
            if (commentNodes.getLength() > 0) {
                mission.setInfo(commentNodes.item(0).getTextContent());
            }

        } catch (Exception e) {
            System.out.println("Ошибка парсинга XML: " + e.getMessage());
        }

        return mission.build();
    }

    private String getTagValue(String tag, Document document) {
        NodeList nodes = document.getElementsByTagName(tag);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }
}