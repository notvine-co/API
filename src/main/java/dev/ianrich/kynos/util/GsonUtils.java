package dev.ianrich.kynos.util;

import dev.ianrich.kynos.Kynos;

import java.io.IOException;

public class GsonUtils {
    public static String toJson(Object obj) throws IOException {
        return Kynos.gson.toJson(obj);
    }
}
