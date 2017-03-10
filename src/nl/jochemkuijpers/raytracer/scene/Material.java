package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Vector3;

public class Material {
    public final Vector3 color;
    public final double reflectivity;
    public final double refractiveIndex;
    public boolean isTransparent;

    public Material(Vector3 color, boolean isTransparent, double reflectivity, double refractiveIndex) {
        this.color = color;
        this.isTransparent = isTransparent;
        this.reflectivity = reflectivity;
        this.refractiveIndex = refractiveIndex;
    }
}
