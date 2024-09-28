package dev.mirrex.service;

import dev.mirrex.util.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class PropertyService {

    public Map<String, List<String>> loadProperties(String filePath) throws IOException {
        List<String> lines = FileUtil.readFile(filePath);
        return lines.stream()
                .map(line -> line.split(": "))
                .collect(Collectors.toMap(
                        parts -> parts[0],
                        parts -> Arrays.asList(parts[1].split(", ")),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }
}
