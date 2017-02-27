package nl.jochemkuijpers.raytracer.color;

import nl.jochemkuijpers.raytracer.math.Vector3;

public class LightMap {
    private Vector3[][] lightMap;

    public LightMap(int width, int height) {
        this.lightMap = new Vector3[height][width];
        for (int y = 0; y < height; y += 1) {
            for (int x = 0; x < width; x += 1) {
                this.lightMap[y][x] = new Vector3(0, 0, 0);
            }
        }
    }

    public int getWidth() {
        return lightMap[0].length;
    }

    public int getHeight() {
        return lightMap.length;
    }

    public void setLight(int x, int y, Vector3 color) {
        this.lightMap[y][x] = color;
    }

    public Vector3 getLight(int x, int y) {
        return this.lightMap[y][x];
    }

    public Vector3[][] getLightMap() {
        return this.lightMap;
    }
}
