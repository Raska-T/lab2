package org.example.parser;

import org.example.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class XmlParser implements MissionParser {

    public Mission parse(File file) {

        Mission mission = new Mission();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            mission.setMissionID(getTagValue("missionId", document));
            mission.setDate(getTagValue("date", document));
            mission.setLocation(getTagValue("location", document));

            String outcomeStr = getTagValue("outcome", document);
            if (!outcomeStr.isEmpty()) {
                mission.setOutcome(Outcome.valueOf(outcomeStr));
            }

            String damageCostStr = getTagValue("damageCost", document);
            if (!damageCostStr.isEmpty()) {
                mission.setDamageCost(Integer.parseInt(damageCostStr));
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
            else{
                Curse defaultCurse = new Curse();
                defaultCurse.setName("Неизвестно");
                defaultCurse.setThreatLevel(CurseThreatLevel.HIGH);
                mission.setCurse(defaultCurse);
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

                String damageStr = getElementValue(tElement, "damage");
                if (!damageStr.isEmpty()) {
                    technique.setDamage(Integer.parseInt(damageStr));
                }
                mission.getTechiques().add(technique);
            }

            String info = getTagValue("comment", document);
            if (!info.isEmpty()) {
                mission.setInfo(info);
            }

        } catch (Exception e) {
            System.out.println("Ошибка парсинга XML: " + e.getMessage());
        }
        return mission;
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