package org.example.handler;

import org.example.parser.MissionParser;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Parsers {
    private Handler firstHandler;

    public Parsers() {
        TxtHandler txtHandler = new TxtHandler();
        JsonHandler jsonHandler = new JsonHandler();
        XmlHandler xmlHandler = new XmlHandler();
        YamlHandler yamlHandler = new YamlHandler();
        BinaryHandler binaryHandler = new BinaryHandler();
        DefaultHandler defaultHandler = new DefaultHandler();

        txtHandler.setNext(jsonHandler);
        jsonHandler.setNext(xmlHandler);
        xmlHandler.setNext(yamlHandler);
        yamlHandler.setNext(binaryHandler);
        binaryHandler.setNext(defaultHandler);

        firstHandler = txtHandler;
    }

    public MissionParser getParser(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            String content = String.join("\n", lines);
            return firstHandler.handle(content, file.getName());
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        }
    }
}