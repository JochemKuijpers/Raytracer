package nl.jochemkuijpers.raytracer;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Timer {
    private final List<Pair<String, Long>> timings;
    private final long startTime;
    private int width;

    public Timer() {
        this.timings = new LinkedList<>();
        this.startTime = System.nanoTime();
        this.width = "Total execution time".length();
    }

    public void mark(String name) {
        timings.add(new Pair<>(name, System.nanoTime()));

        if (name.length() > width) {
            width = name.length();
        }
    }

    public void print() {
        long lastTime = startTime;
        for (Pair<String, Long> timing: timings) {
            System.out.format("%-" + (width + 1) + "s: %9.2fms", timing.getKey(), (timing.getValue() - lastTime) / 1000000.0);
            System.out.println();
            lastTime = timing.getValue();
        }

        System.out.println(new String(new char[width + 14]).replace("\0", "-"));
        System.out.format("%-" + (width + 1) + "s: %9.2fms", "Total execution time", (lastTime - startTime) / 1000000.0);
        System.out.println();
    }
}
