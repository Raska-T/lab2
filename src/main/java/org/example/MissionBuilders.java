package org.example;

import java.util.*;

public class MissionBuilders {
    private String missionId;
    private String date;
    private String location;
    private Outcome outcome;
    public Curse curse;
    private int damageCost;
    private List<Sorcerer> sorcerers = new ArrayList<>();
    private List<Techique> techniques = new ArrayList<>();
    private String info;
    private Map<String, Object> extendedData = new HashMap<>();

    public MissionBuilders setMissionId(String missionId) {
        this.missionId = missionId;
        return this;
    }

    public MissionBuilders setDate(String date) {
        this.date = date;
        return this;
    }

    public MissionBuilders setLocation(String location) {
        this.location = location;
        return this;
    }

    public MissionBuilders setOutcome(Outcome outcome) {
        this.outcome = outcome;
        return this;
    }

    public MissionBuilders setCurse(Curse curse) {
        this.curse = curse;
        return this;
    }

    public MissionBuilders setDamageCost(int damageCost) {
        this.damageCost = damageCost;
        return this;
    }

    public MissionBuilders addSorcerer(Sorcerer sorcerer) {
        this.sorcerers.add(sorcerer);
        return this;
    }

    public MissionBuilders addTechnique(Techique technique) {
        this.techniques.add(technique);
        return this;
    }

    public Curse getCurse() {
        return curse;
    }

    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }

    public List<Techique> getTechiques() {
        return techniques;
    }

    public MissionBuilders setInfo(String info) {
        this.info = info;
        return this;
    }

    public MissionBuilders addExtendedData(String key, Object value) {
        this.extendedData.put(key, value);
        return this;
    }

    public Mission build() {
        if (missionId == null || missionId.isEmpty()) {
            System.out.println("Ошибка: missionId не заполнен");
            return null;
        }
        if (date == null || date.isEmpty()) {
            System.out.println("Ошибка: date не заполнен");
            return null;
        }
        if (location == null || location.isEmpty()) {
            System.out.println("Ошибка: location не заполнен");
            return null;
        }
        if (outcome == null) {
            System.out.println("Ошибка: outcome не заполнен");
            return null;
        }
        if (curse == null) {
            System.out.println("Ошибка: curse не заполнен");
            return null;
        }
        if (curse.getName() == null || curse.getName().isEmpty()) {
            System.out.println("Ошибка: curse.name не заполнен");
            return null;
        }
        if (curse.getThreatLevel() == null) {
            System.out.println("Ошибка: curse.threatLevel не заполнен");
            return null;
        }

        Mission mission = new Mission();
        mission.setMissionID(missionId);
        mission.setDate(date);
        mission.setLocation(location);
        mission.setOutcome(outcome);
        mission.setCurse(curse);
        mission.setDamageCost(damageCost);
        mission.setSorcerers(sorcerers);
        mission.setTechiques(techniques);
        mission.setInfo(info);

        return mission;
    }
}