package org.example.handler;

import org.example.parser.MissionParser;

public interface Handler {
    void setNext(Handler next);
    MissionParser handle(String content, String fileName);
}