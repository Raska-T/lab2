package org.example.handler;

import org.example.parser.MissionParser;

public class DefaultHandler extends BaseHandler {
    @Override
    public MissionParser handle(String content, String fileName) {
        System.out.println("Не удалось определить формат файла: " + fileName);
        return null;
    }
}