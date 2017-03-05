package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class DirectionalLight implements Light {
    private final Vector3 direction;
    private final Vector3 light;

    public DirectionalLight(Vector3 direction, Vector3 light) {
        this.direction = direction.normalize();
        this.light = light;
    }

    @Override
    public void calculateLight(Ray ray, Vector3 light) {
        light
                .set(this.light)
                .multiply(Math.max(0, -Vector.dot(ray.direction, direction)));
    }
}
