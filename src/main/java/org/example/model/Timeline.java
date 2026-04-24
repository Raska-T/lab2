package org.example.model;

public class Timeline {
    private String timestamp;
    private String type;
    private String description;

    public Timeline() {}

    public Timeline(String timestamp, String type, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.description = description;
    }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String v) { this.timestamp = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
}