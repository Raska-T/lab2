package org.example;
import org.example.Mission;
import java.io.File;

public interface MissionParser {
    Mission parse(File file);
}
