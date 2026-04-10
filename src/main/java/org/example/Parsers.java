package org.example;

import java.io.File;

public class Parsers {
    public MissionParser getParser(File file) {
        String fileName = file.getName();

        String l = "";
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            l = fileName.substring(lastDotIndex + 1).toLowerCase();
        }

        if (l.equals("txt")) {
            return new TxtParser();
        }

        if (l.equals("json")) {
            return new JsonParser();
        }

        if (l.equals("xml")) {
            return new XmlParser();
        }

        if (l.equals("yaml")) {
            return new YamlParser();
        }

        if (l.isEmpty()) {
            return new BinaryParser();
        }

        System.out.println("Невозможно прочитать файл" + l);
        return null;
    }
}