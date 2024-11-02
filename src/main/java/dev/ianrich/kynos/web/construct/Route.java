package dev.ianrich.kynos.web.construct;

import java.io.IOException;

@FunctionalInterface
public interface Route {
    void handle(Request request, Response response) throws IOException;
}
