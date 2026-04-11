package org.example;

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