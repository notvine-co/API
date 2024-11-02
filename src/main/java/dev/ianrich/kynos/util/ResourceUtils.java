package dev.ianrich.kynos.util;

import dev.ianrich.kynos.Kynos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceUtils {

    public static void saveResourceFromJar(String resourcePath, String destinationPath) throws IOException {
        // Load resource as InputStream from the JAR file
        try (InputStream resourceStream = ResourceUtils.class.getResourceAsStream(resourcePath)) {
            if (resourceStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }

            // Copy the InputStream to the destination path
            Path destination = Path.of(destinationPath);
            Files.createDirectories(destination.getParent()); // Create parent directories if necessary
            if(!Files.exists(destination)) {
                try (OutputStream outputStream = Files.newOutputStream(destination)) {
                    resourceStream.transferTo(outputStream);
                }
            }
        }
    }

    public static String getResource(String string) throws IOException {
        return Files.readString(Path.of(Kynos.path.toAbsolutePath() + string));
    }

}
