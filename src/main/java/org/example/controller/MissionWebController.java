package org.example.controller;

import org.example.entity.MissionEntity;
import org.example.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MissionWebController {

    @Autowired
    private MissionService missionService;

    @GetMapping("/")
    public String index(Model model) {
        List<MissionEntity> missions = missionService.getAllMissions();
        model.addAttribute("missions", missions);
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes ra) {
        try {
            String id = missionService.saveMission(file);
            ra.addFlashAttribute("msg", "Миссия " + id + " загружена");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/report/{id}")
    public String report(@PathVariable String id, @RequestParam(defaultValue = "full") String type, Model model) {
        try {
            String report = missionService.generateReport(id, type);
            model.addAttribute("report", report);
            model.addAttribute("missionId", id);
            model.addAttribute("currentType", type);
            return "report";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "report";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) throws Exception {
        MissionEntity mission = missionService.getMissionById(id);
        model.addAttribute("mission", mission);
        return "edit";
    }

    @PostMapping("/updateWithId/{id}")
    public String updateMissionWithId(@PathVariable String id,
                                      @RequestParam String newMissionId,
                                      @RequestParam String location,
                                      @RequestParam String curseName,
                                      @RequestParam String sorcerersNames,
                                      @RequestParam String outcome) throws Exception {
        missionService.updateMissionWithId(id, newMissionId, curseName, sorcerersNames, outcome, location);
        return "redirect:/";
    }
}