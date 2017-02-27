package nl.jochemkuijpers.raytracer.shape;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;

public interface Shape {
    public RayCollision test(Ray ray);
}
