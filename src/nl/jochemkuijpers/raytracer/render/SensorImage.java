package nl.jochemkuijpers.raytracer.render;

import nl.jochemkuijpers.raytracer.color.ColorStrategy;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class SensorImage {
    private final int width;
    private final int height;

    private final Vector3[][] lightValues;
    private final Vector3 maxValue;

    public SensorImage(int width, int height, int ssFactor) {
        this.width = width;
        this.height = height;

        this.lightValues = new Vector3[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.lightValues[y][x] = new Vector3();
            }
        }
        this.maxValue = new Vector3();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector3 getMaxLight() {
        return maxValue;
    }

    public Vector3 getLight(int x, int y) {
        return this.lightValues[y][x];
    }

    public void setLight(int x, int y, Vector3 lightValue) {
        this.lightValues[y][x].set(lightValue);
        Vector.max(this.maxValue, lightValue, this.maxValue);
    }

    public int[][] generateRgbImage(ColorStrategy strategy) {
        int[][] rgb = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgb[y][x] = strategy.color(this, x, y);
            }
        }
        return rgb;
    }
}

