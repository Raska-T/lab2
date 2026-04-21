package org.example.report;

import org.example.model.Mission;

public class SmallReport implements ReportGenerator {
    @Override
    public void generate(Mission mission) {
        System.out.println("Краткий отчет:");
        System.out.println("MissionID:  " + mission.getMissionID());
        System.out.println("CurseName: " + mission.getCurse().getName());
        System.out.println("CurseThreatLevel: " + mission.getCurse().getThreatLevel());
        System.out.println("Date: " + mission.getDate());
        System.out.println("Location: " + mission.getLocation());
        System.out.println("Outcome: " + mission.getOutcome());
    }
}
