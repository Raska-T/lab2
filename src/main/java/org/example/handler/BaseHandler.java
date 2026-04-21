package org.example.handler;

import org.example.parser.MissionParser;

public abstract class BaseHandler implements Handler {
    protected Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public MissionParser handle(String content, String fileName) {
        if (next != null) {
            return next.handle(content, fileName);
        }
        return null;
    }
}