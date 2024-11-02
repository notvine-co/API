package dev.ianrich.kynos.util;

import dev.ianrich.kynos.Kynos;
import dev.ianrich.kynos.images.Frowny;
import dev.ianrich.kynos.images.NotVine;
import dev.ianrich.kynos.web.construct.Request;
import dev.ianrich.kynos.web.construct.Response;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ContentUtils {

    public static String getView(String string) throws IOException {
        return Files.readString(Path.of(Kynos.path.toAbsolutePath() + "/views/" + string + ".html"));
    }

    public static String replaceDefaultVariables(Request request, String string) {
        return string.replace("%authorized%", String.valueOf(request.isAuthorized()))
                     .replace("%request%", request.getJsonRequest().toString())
                     .replace("%url%", request.getUri().toString());
    }

    public static String replaceImageVariables(String string){
        return string.replace("%frowny%", "data:image/png;base64," + Frowny.data)
                .replace("%logo%", "data:image/png;base64," + NotVine.data);
    }
}
