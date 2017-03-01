package nl.jochemkuijpers.raytracer.color;

import nl.jochemkuijpers.raytracer.math.Vector2;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class LightMap {
    private Vector3[][] lightMap;

    public LightMap(Vector2 resolution) {
        this.lightMap = new Vector3[(int) resolution.y][(int) resolution.x];
        for (int y = 0; y < resolution.y; y += 1) {
            for (int x = 0; x < resolution.x; x += 1) {
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
        this.lightMap[y][x].set(color);
    }

    public Vector3 getLight(int x, int y) {
        return this.lightMap[y][x];
    }

    public Vector3[][] getLightMap() {
        return this.lightMap;
    }
}
