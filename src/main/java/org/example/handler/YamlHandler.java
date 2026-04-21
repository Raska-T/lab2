package org.example.handler;

import org.example.parser.MissionParser;
import org.example.parser.YamlParser;

public class YamlHandler extends BaseHandler {
    @Override
    public MissionParser handle(String content, String fileName) {
        String firstLine = content.trim().split("\n")[0];
        if (firstLine.contains("missionId:") && !firstLine.contains("\"")) {
            return new YamlParser();
        } else {
            return super.handle(content, fileName);
        }
    }
}