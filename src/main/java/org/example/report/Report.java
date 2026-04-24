package org.example.report;

import org.example.model.Mission;
import org.example.model.Sorcerer;
import org.example.model.Techique;
import org.example.model.Timeline;

public class Report implements ReportGenerator {
    @Override
    public void generate(Mission mission) {
        System.out.println("Основная информация о миссии: ");
        System.out.println("MissionID:  " + mission.getMissionID());
        System.out.println("Location: " + mission.getLocation());
        System.out.println("Date: " + mission.getDate());
        System.out.println("Outcome: " + mission.getOutcome());
        System.out.println("CurseName: " + mission.getCurse().getName());
        System.out.println("CurseThreatLevel: " + mission.getCurse().getThreatLevel());
        System.out.println("DamageCost: " + mission.getDamageCost());


        for (Timeline e : mission.getTimelineEvents()) {
            System.out.println("  Timestamp: " + e.getTimestamp());
            System.out.println("  Type: " + e.getType());
            System.out.println("  Description: " + e.getDescription());
        }

        for (Sorcerer s : mission.getSorcerers()) {
            System.out.println("SorcererName: " + s.getName());
            System.out.println("SorcererRank: " + s.getRank());
        }

        for (Techique t : mission.getTechiques()) {
            System.out.println("TechniqueName: " + t.getName());
            System.out.println("TechniqueType: " + t.getType());
            System.out.println("TechniqueOwner: " + t.getOwner());
            System.out.println("TechniqueDamage: " + t.getDamage());
        }
    }
}