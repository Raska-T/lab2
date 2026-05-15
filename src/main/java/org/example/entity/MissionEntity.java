package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "missions")
public class MissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private int damageCost;
    private String info;

    @OneToOne(cascade = CascadeType.ALL)
    private CurseEntity curse;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SorcererEntity> sorcerers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TechniqueEntity> techniques = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TimelineEntity> timelineEvents = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }
    public int getDamageCost() { return damageCost; }
    public void setDamageCost(int damageCost) { this.damageCost = damageCost; }
    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }
    public CurseEntity getCurse() { return curse; }
    public void setCurse(CurseEntity curse) { this.curse = curse; }
    public Set<SorcererEntity> getSorcerers() { return sorcerers; }
    public Set<TechniqueEntity> getTechniques() { return techniques; }
    public Set<TimelineEntity> getTimelineEvents() { return timelineEvents; }}