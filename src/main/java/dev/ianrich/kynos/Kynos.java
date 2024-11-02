package dev.ianrich.kynos;

import com.google.gson.Gson;
import dev.ianrich.kynos.controller.DefaultController;
import dev.ianrich.kynos.task.CleanupTask;
import dev.ianrich.kynos.util.ResourceUtils;
import dev.ianrich.kynos.util.construct.ConfigFile;
import dev.ianrich.kynos.web.Server;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;

@Getter
public class Kynos {

    public static Gson gson = new Gson();
    public static Server server = Server.create();
    public static Path path;

    public static ConfigFile mainConfig;

    public static void main(String[] args) throws IOException {

        // To track how long we take
        long start = System.currentTimeMillis();

        // Starting
        System.out.println("Starting Kynos...");

        // Where are we?
        path = Paths.get(new File(".").getAbsolutePath());
        System.out.println("Found file path to Kynos: \"" + path + "\"");

        // Save defaults
        saveDefaults();

        // Load config
        mainConfig = new ConfigFile("./config.yml");

        // Routes
        createRoutes();

        // Start socket
        server.start(mainConfig.getInteger("server-port"));

        // Initiate garbage cleanup task
        startGarbageCleanup();

        // Finished loading!
        System.out.println("Started Kynos! (" + (System.currentTimeMillis() - start) + "ms)");

    }

    private static void createRoutes() {

        // Default Controller (no data)
        server.get("/", DefaultController.getPage(), false);

    }

    private static void startGarbageCleanup() {

        Timer timer = new Timer("API - Garbage Cleaner Timer");
        timer.schedule(new CleanupTask(), 600000L);
        System.out.println("Started cleanup task.");

    }

    private static void saveDefaults() throws IOException {
        ResourceUtils.saveResourceFromJar("/views/default.html", "views/default.html");
        ResourceUtils.saveResourceFromJar("/configuration/config.yml", "./config.yml");
    }

}
