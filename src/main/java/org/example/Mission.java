package org.example;
import java.util.ArrayList;
import java.util.List;

public class Mission {
    private String missionID;
    private String date;
    private String location;
    private String outcome;
    private int damageCost;
    private Curse curse;
    private List<Sorcerer> sorcerers;
    private List<Techique> techiques;
    private String info;

    public Mission() {
        this.sorcerers = new ArrayList<>();
        this.techiques = new ArrayList<>();
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

    public String getOutcome() {
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

    public String getInfo() {
        return info;
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

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public void setDamageCost(int damageCost) {
        this.damageCost = damageCost;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    public void setSorcerers(List<Sorcerer> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public void setTechiques(List<Techique> techiques) {
        this.techiques = techiques;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void printMission(){
        System.out.println("Основная информация о миссии: ");
        System.out.println("outcome: " + outcome);

        if (curse != null){
            System.out.println("curse.name: " + curse.getName());
            System.out.println("curse.threatLevel: " + curse.getThreatLevel());
        }

        if (!sorcerers.isEmpty()){
            for (Sorcerer s : sorcerers){
                System.out.println("sorcerer.name: " + s.getName());
                System.out.println("sorcerer.rank: " + s.getRank());
            }
        }

        if (!techiques.isEmpty()){
            for (Techique r: techiques){
                System.out.println("technique.name: " + r.getName());
                System.out.println("technique.type: " + r.getType());
                System.out.println("technique.owner: " + r.getOwner());
                System.out.println("technique.damage: " + r.getDamage());
            }
        }
    }
}
