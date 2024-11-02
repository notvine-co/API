package dev.ianrich.kynos.util;

import dev.ianrich.kynos.Kynos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ContentUtils {
    public static String getView(String string) throws IOException {
        return Files.readString(Path.of(Kynos.path.toAbsolutePath() + "/views/" + string + ".html"));
    }
}
