package org.example;

public class Curse {
    private String name;
    private CurseThreatLevel threatLevel;

    public Curse() {}

    public String getName() {
        return name;
    }

    public CurseThreatLevel getThreatLevel() {
        return threatLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThreatLevel(CurseThreatLevel threatLevel) {
        this.threatLevel = threatLevel;
    }
}