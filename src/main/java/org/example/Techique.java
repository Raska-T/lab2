package org.example;

public class Techique {
    String name;
    String type;
    String owner;
    int damage;

    public Techique() {}

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
