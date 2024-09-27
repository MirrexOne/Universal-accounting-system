package dev.mirrex.service;

import dev.mirrex.model.Animal;
import dev.mirrex.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnimalService {

    public List<Animal> loadAnimals(String filePath, Map<String, List<String>> properties) throws IOException {
        List<String> lines = FileUtil.readFile(filePath);
        List<String> propertyNames = new ArrayList<>(properties.keySet());
        
        return lines.stream()
                .map(line -> {
                    String[] props = line.split(",");
                    Map<String, String> animalProps = new LinkedHashMap<>();
                    for (int i = 0; i < props.length && i < propertyNames.size(); i++) {
                        animalProps.put(propertyNames.get(i), props[i].trim());
                    }
                    return new Animal(animalProps);
                })
                .collect(Collectors.toList());
    }
}
