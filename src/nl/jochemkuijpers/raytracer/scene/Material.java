package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Vector3;

public class Material {
    public final Vector3 color;
    public final double transparency;
    public final double reflectivity;

    public Material(Vector3 color, double transparency, double reflectivity) {
        this.color = color;
        this.transparency = transparency;
        this.reflectivity = reflectivity;
    }
}
