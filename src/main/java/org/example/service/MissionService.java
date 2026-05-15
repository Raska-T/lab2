package org.example.service;

import org.example.entity.*;
import org.example.handler.Parsers;
import org.example.model.Mission;
import org.example.model.Sorcerer;
import org.example.model.Techique;
import org.example.model.Timeline;
import org.example.parser.MissionParser;
import org.example.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private Parsers parsers;

    public String saveMission(MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("mission_", "_" + file.getOriginalFilename());
        file.transferTo(tempFile);

        MissionParser parser = parsers.getParser(tempFile);
        if (parser == null) {
            throw new Exception("Неподдерживаемый формат файла");
        }

        Mission parsedMission = parser.parse(tempFile);
        MissionEntity entity = convertToEntity(parsedMission);
        missionRepository.save(entity);

        tempFile.delete();
        return entity.getMissionId();
    }

    public List<MissionEntity> getAllMissions() {
        return missionRepository.findAll();
    }

    public MissionEntity getMissionById(String missionId) throws Exception {
        List<MissionEntity> missions = missionRepository.findAllByMissionId(missionId);
        if (missions.isEmpty()) {
            throw new Exception("Миссия не найдена: " + missionId);
        }
        return missions.get(missions.size() - 1);
    }

    public void deleteMission(String missionId) throws Exception {
        List<MissionEntity> missions = missionRepository.findAllByMissionId(missionId);
        if (missions.isEmpty()) {
            throw new Exception("Миссия не найдена: " + missionId);
        }
        missionRepository.delete(missions.get(missions.size() - 1));
    }

    @Transactional
    public void updateMissionWithId(String oldMissionId, String newMissionId, String curseName,
                                    String sorcerersNames, String outcome, String location) throws Exception {
        List<MissionEntity> missions = missionRepository.findAllByMissionId(oldMissionId);
        if (missions.isEmpty()) {
            throw new Exception("Миссия не найдена: " + oldMissionId);
        }
        MissionEntity entity = missions.get(missions.size() - 1);

        if (location != null && !location.isEmpty()) {
            entity.setLocation(location);
        }

        if (!oldMissionId.equals(newMissionId)) {
            List<MissionEntity> existing = missionRepository.findAllByMissionId(newMissionId);
            if (!existing.isEmpty()) {
                throw new Exception("Миссия с ID " + newMissionId + " уже существует!");
            }
            entity.setMissionId(newMissionId);
        }

        if (entity.getCurse() != null) {
            entity.getCurse().setName(curseName);
        } else {
            CurseEntity newCurse = new CurseEntity();
            newCurse.setName(curseName);
            entity.setCurse(newCurse);
        }

        entity.setOutcome(outcome);
        entity.getSorcerers().clear();
        String[] names = sorcerersNames.split("\\s*,\\s*");
        for (String name : names) {
            if (!name.trim().isEmpty()) {
                SorcererEntity sorcerer = new SorcererEntity();
                sorcerer.setName(name.trim());
                entity.getSorcerers().add(sorcerer);
            }
        }

        missionRepository.save(entity);
    }

    public String generateReport(String missionId, String type) throws Exception {
        List<MissionEntity> missions = missionRepository.findAllByMissionId(missionId);
        if (missions.isEmpty()) {
            throw new Exception("Миссия не найдена: " + missionId);
        }
        MissionEntity entity = missions.get(missions.size() - 1);

        Mission mission = convertToModel(entity);

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);

        if ("small".equalsIgnoreCase(type)) {
            new org.example.report.SmallReport().generate(mission);
        } else {
            new org.example.report.Report().generate(mission);
        }

        System.out.flush();
        System.setOut(old);
        return baos.toString();
    }

    private MissionEntity convertToEntity(Mission model) {
        MissionEntity entity = new MissionEntity();
        entity.setMissionId(model.getMissionID());
        entity.setDate(model.getDate());
        entity.setLocation(model.getLocation());
        entity.setOutcome(model.getOutcome() != null ? model.getOutcome().toString() : null);
        entity.setDamageCost(model.getDamageCost());
        entity.setInfo(model.getInfo());

        if (model.getCurse() != null) {
            CurseEntity curse = new CurseEntity();
            curse.setName(model.getCurse().getName());
            curse.setThreatLevel(model.getCurse().getThreatLevel().toString());
            entity.setCurse(curse);
        }

        for (org.example.model.Sorcerer s : model.getSorcerers()) {
            SorcererEntity sorcerer = new SorcererEntity();
            sorcerer.setName(s.getName());
            sorcerer.setRank(s.getRank());
            entity.getSorcerers().add(sorcerer);
        }

        for (org.example.model.Techique t : model.getTechiques()) {
            TechniqueEntity technique = new TechniqueEntity();
            technique.setName(t.getName());
            technique.setType(t.getType());
            technique.setOwner(t.getOwner());
            technique.setDamage(t.getDamage());
            entity.getTechniques().add(technique);
        }

        for (org.example.model.Timeline tl : model.getTimelineEvents()) {
            TimelineEntity timeline = new TimelineEntity();
            timeline.setTimestamp(tl.getTimestamp());
            timeline.setType(tl.getType());
            timeline.setDescription(tl.getDescription());
            entity.getTimelineEvents().add(timeline);
        }

        return entity;
    }

    private Mission convertToModel(MissionEntity entity) {
        Mission mission = new Mission();
        mission.setMissionID(entity.getMissionId());
        mission.setDate(entity.getDate());
        mission.setLocation(entity.getLocation());
        mission.setDamageCost(entity.getDamageCost());
        mission.setInfo(entity.getInfo());

        if (entity.getOutcome() != null) {
            mission.setOutcome(org.example.model.Outcome.valueOf(entity.getOutcome()));
        }

        if (entity.getCurse() != null) {
            org.example.model.Curse curse = new org.example.model.Curse();
            curse.setName(entity.getCurse().getName());
            curse.setThreatLevel(org.example.model.CurseThreatLevel.valueOf(entity.getCurse().getThreatLevel()));
            mission.setCurse(curse);
        }

        for (SorcererEntity se : entity.getSorcerers()) {
            Sorcerer sorcerer = new Sorcerer();
            sorcerer.setName(se.getName());
            sorcerer.setRank(se.getRank());
            mission.getSorcerers().add(sorcerer);
        }

        for (TechniqueEntity te : entity.getTechniques()) {
            Techique technique = new Techique();
            technique.setName(te.getName());
            technique.setType(te.getType());
            technique.setOwner(te.getOwner());
            technique.setDamage(te.getDamage());
            mission.getTechiques().add(technique);
        }

        for (TimelineEntity te : entity.getTimelineEvents()) {
            Timeline timeline = new Timeline();
            timeline.setTimestamp(te.getTimestamp());
            timeline.setType(te.getType());
            timeline.setDescription(te.getDescription());
            mission.addTimelineEvent(timeline);
        }

        return mission;
    }
}