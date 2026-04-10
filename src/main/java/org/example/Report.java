package org.example;

public class Report implements  ReportGenerator{
    @Override
    public void generate(Mission mission) {
        System.out.println("Основная информация о миссии: ");
        System.out.println("Outcome: " + mission.getOutcome());

        if (mission.getCurse() != null) {
            System.out.println("CurseName: " + mission.getCurse().getName());
            System.out.println("CurseThreatLevel: " + mission.getCurse().getThreatLevel());
        }

        if (mission.getSorcerers() != null && !mission.getSorcerers().isEmpty()) {
            for (Sorcerer s : mission.getSorcerers()) {
                System.out.println("SorcererName: " + s.getName());
                System.out.println("SorcererRank: " + s.getRank());
            }
        }

        if (mission.getTechiques() != null && !mission.getTechiques().isEmpty()) {
            for (Techique t : mission.getTechiques()) {
                System.out.println("TechniqueName: " + t.getName());
                System.out.println("TechniqueType: " + t.getType());
                System.out.println("TechniqueOwner: " + t.getOwner());
                System.out.println("TechniqueDamage: " + t.getDamage());
            }
        }
    }
}

