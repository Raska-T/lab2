package org.example;

import java.io.File;

public class Parsers {
    public MissionParser getParser(File fIle){
        String fileName = fIle.getName();

        if (fileName.endsWith(".txt")){
            return new Txtparser();
        }

        if (fileName.endsWith(".json")){
            return new Jsonparser();
        }

        if (fileName.endsWith(".xml")){
            return new Xmlparser();
        }

        else{
            System.out.println("Не можем прочитать такой файл");
            return null;
        }
    }
}
