package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mission {
    private String missionID;
    private String date;
    private String location;
    private Outcome outcome;
    private int damageCost;
    private Curse curse;
    private List<Sorcerer> sorcerers;
    private List<Techique> techiques;
    private Map<String, Object> extendedData;
    private String info;

    public Mission() {
        this.sorcerers = new ArrayList<>();
        this.techiques = new ArrayList<>();
        this.extendedData = new HashMap<>();
    }

    public String getMissionID() {
        return missionID;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public int getDamageCost() {
        return damageCost;
    }

    public Curse getCurse() {
        return curse;
    }

    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }

    public List<Techique> getTechiques() {
        return techiques;
    }

    public void setMissionID(String missionID) {
        this.missionID = missionID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public void setDamageCost(int damageCost) {
        this.damageCost = damageCost;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }


    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, Object> getExtendedData() {
        return extendedData;
    }

    public void addExtendedData(String key, Object value) {
        this.extendedData.put(key, value);
    }

    private List<Timeline> timelineEvents = new ArrayList<>();

    public List<Timeline> getTimelineEvents() { return timelineEvents; }
    
    public void addTimelineEvent(Timeline event) { timelineEvents.add(event); }
}
