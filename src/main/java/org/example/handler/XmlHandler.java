package org.example.handler;

import org.example.parser.MissionParser;
import org.example.parser.XmlParser;

public class XmlHandler extends BaseHandler {
    @Override
    public MissionParser handle(String content, String fileName) {
        String firstLine = content.trim().split("\n")[0];
        if (firstLine.startsWith("<")) {
            return new XmlParser();
        } else {
            return super.handle(content, fileName);
        }
    }
}