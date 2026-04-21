package org.example.handler;

import org.example.parser.MissionParser;
import org.example.parser.TxtParser;

public class TxtHandler extends BaseHandler {
    @Override
    public MissionParser handle(String content, String fileName) {
        String firstLine = content.trim().split("\n")[0];
        if (firstLine.startsWith("[")) {
            return new TxtParser();
        } else {
            return super.handle(content, fileName);
        }
    }
}