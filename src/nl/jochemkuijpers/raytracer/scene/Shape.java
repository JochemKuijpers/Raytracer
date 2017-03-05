package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;

public abstract class Shape {
    public final Material material;

    public Shape(Material material) {
        this.material = material;
    }

    public abstract RayCollision test(Ray ray);
}
