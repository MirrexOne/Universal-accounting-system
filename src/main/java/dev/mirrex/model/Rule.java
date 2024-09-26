package dev.mirrex.model;

import java.util.function.Predicate;

public class Rule {

    private final String name;

    private final Predicate<Animal> predicate;

    public Rule(String name, Predicate<Animal> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    public String getName() {
        return name;
    }

    public Predicate<Animal> getPredicate() {
        return predicate;
    }

    @Override
    public String toString() {
        return "Rule: " + name;
    }
}
