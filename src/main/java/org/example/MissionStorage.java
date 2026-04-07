package org.example;

import java.util.ArrayList;
import java.util.List;

public class MissionStorage {
    private static MissionStorage instance;
    private List<Mission> missions;

    private MissionStorage() {
        missions = new ArrayList<>();
    }

    public static MissionStorage getInstance() {
        if (instance == null) {
            instance = new MissionStorage();
        }
        return instance;
    }

    public void addMission(Mission mission) {
        missions.add(mission);
    }

    public List<Mission> getAllMissions() {
        return missions;
    }
}