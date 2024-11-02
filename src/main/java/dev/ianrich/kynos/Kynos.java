package dev.ianrich.kynos;

import com.google.gson.Gson;
import dev.ianrich.kynos.controller.DefaultController;
import dev.ianrich.kynos.controller.error.get.GetErrorPage;
import dev.ianrich.kynos.controller.profile.get.GetProfile;
import dev.ianrich.kynos.controller.profile.post.CreateProfile;
import dev.ianrich.kynos.profile.ProfileHandler;
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

        // Initiate
        new ProfileHandler();

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
        server.get("/", DefaultController.getPage());

        // Profiles
        server.get("/profile", GetProfile.getPage());
        server.post("/profile/create", CreateProfile.getPage());

        // Error
        server.get("/error", GetErrorPage.getPage());

    }

    private static void startGarbageCleanup() {

        Timer timer = new Timer("API - Garbage Cleaner Timer");
        timer.schedule(new CleanupTask(), 600000L);
        System.out.println("Started cleanup task.");

    }

    private static void saveDefaults() throws IOException {
        ResourceUtils.saveResourceFromJar("/views/default.html", "views/default.html");

        ResourceUtils.saveResourceFromJar("/views/error/error.html", "views/error/error.html");

        ResourceUtils.saveResourceFromJar("/views/profile/create.html", "views/profile/create.html");
        ResourceUtils.saveResourceFromJar("/views/profile/view.html", "views/profile/view.html");


        ResourceUtils.saveResourceFromJar("/configuration/config.yml", "./config.yml");
    }

}
