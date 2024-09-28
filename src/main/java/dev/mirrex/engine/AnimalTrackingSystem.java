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

    private List<Animal> animals;

    private List<Rule> rules;

    public AnimalTrackingSystem() {
        this.propertyService = new PropertyService();
        this.animalService = new AnimalService();
        this.ruleService = new RuleService();
    }

    public void run(String animalsFile, String propertiesFile, String rulesFile) throws IOException {
        Map<String, List<String>> properties = propertyService.loadProperties(propertiesFile);
        animals = animalService.loadAnimals(animalsFile, properties);
        rules = ruleService.loadRules(rulesFile);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
