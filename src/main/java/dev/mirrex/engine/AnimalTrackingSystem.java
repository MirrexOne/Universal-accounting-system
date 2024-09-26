package dev.mirrex.engine;

import dev.mirrex.model.Animal;
import dev.mirrex.model.Rule;
import dev.mirrex.service.AnimalService;
import dev.mirrex.service.PropertyService;
import dev.mirrex.service.RuleService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AnimalTrackingSystem {

    private final PropertyService propertyService;

    private final AnimalService animalService;

    private final RuleService ruleService;

    public AnimalTrackingSystem() {
        this.propertyService = new PropertyService();
        this.animalService = new AnimalService();
        this.ruleService = new RuleService();
    }

    public void run(String animalsFile, String propertiesFile, String rulesFile) {
        try {
            Map<String, List<String>> properties = propertyService.loadProperties(propertiesFile);
            List<Animal> animals = animalService.loadAnimals(animalsFile, properties);
            List<Rule> rules = ruleService.loadRules(rulesFile);

            System.out.println("Loaded animals:");
            animals.forEach(System.out::println);

            System.out.println("\nLoaded rules:");
            rules.forEach(System.out::println);

            for (Rule rule : rules) {
                long count = animals.stream().filter(rule.getPredicate()).count();
                System.out.println(rule.getName() + ": " + count);

                System.out.println("  Matching animals:");
                animals.stream().filter(rule.getPredicate())
                        .forEach(animal -> System.out.println("    " + animal));
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }
}
