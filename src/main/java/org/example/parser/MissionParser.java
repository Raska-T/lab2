package org.example.parser;
import org.example.model.Mission;
import java.io.File;

public interface MissionParser {
    Mission parse(File file);
}
