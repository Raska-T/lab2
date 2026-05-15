package org.example.controller;

import org.example.entity.MissionEntity;
import org.example.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Миссии", description = "REST API для работы с миссиями")
public class MissionRestController {

    @Autowired
    private MissionService missionService;

    @GetMapping("/missions")
    @Operation(summary = "Получить все миссии")
    public ResponseEntity<List<MissionEntity>> getAllMissions() {
        return ResponseEntity.ok(missionService.getAllMissions());
    }

    @GetMapping("/missions/{missionId}")
    @Operation(summary = "Получить миссию по ID (например M-2024-017)")
    public ResponseEntity<MissionEntity> getMissionByMissionId(@PathVariable String missionId) {
        try {
            return ResponseEntity.ok(missionService.getMissionById(missionId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/missions/{missionId}")
    @Operation(summary = "Удалить миссию по ID")
    public ResponseEntity<String> deleteMission(@PathVariable String missionId) {
        try {
            missionService.deleteMission(missionId);
            return ResponseEntity.ok("Миссия " + missionId + " удалена");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/missions/{missionId}/report")
    @Operation(summary = "Получить отчет по миссии")
    public ResponseEntity<String> getReport(@PathVariable String missionId,
                                            @RequestParam(defaultValue = "full") String type) {
        try {
            String report = missionService.generateReport(missionId, type);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @PutMapping("/missions/{missionId}")
    @Operation(summary = "Обновить миссию (ID, локацию, проклятье, участников, статус)")
    public ResponseEntity<String> updateMission(
            @PathVariable String missionId,
            @RequestParam String newMissionId,
            @RequestParam String location,
            @RequestParam String curseName,
            @RequestParam String sorcerersNames,
            @RequestParam String outcome) {
        try {
            missionService.updateMissionWithId(missionId, newMissionId, curseName, sorcerersNames, outcome, location);
            return ResponseEntity.ok("Миссия обновлена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
}