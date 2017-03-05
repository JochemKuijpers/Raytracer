package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Vector3;

public class Material {
    public final Vector3 color;

    public Material(Vector3 color) {
        this.color = color;
    }
}
