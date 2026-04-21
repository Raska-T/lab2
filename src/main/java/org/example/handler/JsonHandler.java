package org.example.handler;

import org.example.parser.JsonParser;
import org.example.parser.MissionParser;

public class JsonHandler extends BaseHandler {
    @Override
    public MissionParser handle(String content, String fileName) {
        String firstLine = content.trim().split("\n")[0];
        if (firstLine.startsWith("{")) {
            return new JsonParser();
        } else {
            return super.handle(content, fileName);
        }
    }
}