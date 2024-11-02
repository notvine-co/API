package dev.ianrich.kynos.task;

import java.util.TimerTask;

public class CleanupTask extends TimerTask {
    @Override
    public void run() {
        System.gc();
        Runtime.getRuntime().gc();
    }
}
