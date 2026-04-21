package org.example.handler;

import org.example.parser.BinaryParser;
import org.example.parser.MissionParser;

public class BinaryHandler extends BaseHandler {
    @Override
    public MissionParser handle(String content, String fileName) {
        if (content.contains("|")) {
            return new BinaryParser();
        } else {
            return super.handle(content, fileName);
        }
    }
}