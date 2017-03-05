package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Vector3;

public class Material {
    public final Vector3 color;
    public final double transparency;
    public final double reflectivity;
    public final double refractiveIndex;

    public Material(Vector3 color, double transparency, double reflectivity, double refractiveIndex) {
        this.color = color;
        this.transparency = transparency;
        this.reflectivity = reflectivity;
        this.refractiveIndex = refractiveIndex;
    }
}
