package dev.mirrex;

import dev.mirrex.engine.AnimalTrackingSystem;

public class Application {
    public static void main(String[] args) {
        AnimalTrackingSystem system = new AnimalTrackingSystem();
        system.run("animals.txt", "properties.txt", "rules.txt");
    }
}
