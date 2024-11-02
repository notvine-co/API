package dev.ianrich.kynos.util.construct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigFile {
    private final Map<String, String> configMap = new HashMap<>();

    public ConfigFile(String configPath) throws IOException {
        loadConfig(configPath);

        String[] splitPath = configPath.split("/");
        System.out.println("Loaded configuration file \"" + splitPath[splitPath.length-1] + "\"");
    }

    private void loadConfig(String configPath) throws IOException {
        Path path = Path.of(configPath);
        for (String line : Files.readAllLines(path)) {
            line = line.trim();
            // Skip comments and empty lines
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                configMap.put(key, value);
            }
        }
    }

    public String getString(String key) {
        return configMap.get(key).replace("\"", "");
    }

    public int getInteger(String key) {
        return Integer.parseInt(configMap.getOrDefault(key, "0"));
    }
}