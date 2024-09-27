package dev.mirrex.service;

import dev.mirrex.model.Animal;
import dev.mirrex.model.Rule;
import dev.mirrex.util.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RuleService {

    public List<Rule> loadRules(String filePath) throws IOException {
        List<String> lines = FileUtil.readFile(filePath);
        return lines.stream()
                .map(line -> {
                    String[] parts = line.split(": ", 2);
                    return new Rule(parts[0], parseCondition(parts[1]));
                })
                .collect(Collectors.toList());
    }

    private Predicate<Animal> parseCondition(String condition) {
        condition = condition.trim();
        
        if (condition.startsWith("(") && condition.endsWith(")")) {
            return parseCondition(condition.substring(1, condition.length() - 1));
        }
        
        if (condition.contains(" AND ")) {
            List<Predicate<Animal>> predicates = Arrays.stream(condition.split(" AND "))
                .map(this::parseCondition)
                .toList();
            return animal -> predicates.stream().allMatch(predicate -> predicate.test(animal));
        }
        
        if (condition.contains(" OR ")) {
            List<Predicate<Animal>> predicates = Arrays.stream(condition.split(" OR "))
                .map(this::parseCondition)
                .toList();
            return animal -> predicates.stream().anyMatch(predicate -> predicate.test(animal));
        }
        
        if (condition.startsWith("NOT ")) {
            Predicate<Animal> predicate = parseCondition(condition.substring(4));
            return predicate.negate();
        }
        
        String[] parts = condition.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }
        String property = parts[0].trim();
        String value = parts[1].trim();
        return animal -> value.equals(animal.getProperty(property));
    }
}
