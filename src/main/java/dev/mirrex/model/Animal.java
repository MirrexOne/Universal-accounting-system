package dev.mirrex.model;

import java.util.Map;

public class Animal {

    private final Map<String, String> properties;

    public Animal(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public String toString() {
        return "Animal" + properties;
    }
}
