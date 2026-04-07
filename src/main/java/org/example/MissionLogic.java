package org.example;

import org.example.Mission;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MissionLogic {
    private static final String path = "C:\\Users\\raska\\IdeaProjects\\lab1\\mission_history.json";
    private static MissionLogic instance;
    private ObjectMapper objectMapper;

    private MissionLogic() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static MissionLogic getInstance() {
        if (instance == null) {
            instance = new MissionLogic();
        }
        return instance;
    }

    public void saveMission(Mission mission) {
        List<Mission> history = loadHistory();
        history.add(mission);
        saveToFile(history);
        System.out.println("Миссия " + mission.getMissionID() + " сохранена в историю");
    }

    public List<Mission> loadHistory() {
        File file = new File(path);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Mission.class));
        } catch (IOException e) {
            System.out.println("Ошибка загрузки истории: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveToFile(List<Mission> missions) {
        try {
            objectMapper.writeValue(new File(path), missions);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }
}