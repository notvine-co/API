package dev.ianrich.kynos.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UrlUtils {
    public static HashMap<String, String> getParams(String url) {
        HashMap<String, String> filtered = new HashMap<>();

        if(!url.contains(Character.toString('?'))) return filtered;

        String query = url.split("\\?")[1];
        List<String> stufftofilter = Arrays.asList(query.split("\\&"));
        stufftofilter.forEach(d -> {
            filtered.put(d.split("=")[0], d.split("=")[1]);
        });

        String skyrocket = "";

        for(String d: filtered.keySet()){
            skyrocket = skyrocket + d + " ";
        }

        String skyrocket2 = "";

        for(String d: filtered.values()){
            skyrocket2 = skyrocket2 + d + " ";
        }

        System.out.println("URL: " + url + ", PARAMS: " + skyrocket + ", " + skyrocket2);

        return filtered;
    }
}
