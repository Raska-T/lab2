package org.example;

import org.example.gui.MissionDesktopFrame;
import org.example.service.MissionService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .run(args);

        MissionService missionService = context.getBean(MissionService.class);

        SwingUtilities.invokeLater(() -> {
            MissionDesktopFrame frame = new MissionDesktopFrame(missionService);
            frame.setVisible(true);
        });
    }
}