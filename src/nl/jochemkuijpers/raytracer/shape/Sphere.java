package nl.jochemkuijpers.raytracer.shape;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class Sphere implements Shape {
    private final Vector3 center;
    private final double radius;

    public Sphere(Vector3 center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public RayCollision test(Ray ray) {
        // t is the solution for collisionPosition = ray.origin + t * ray.direction
        double t;

        Vector3 originToCenter = center.subtract(ray.origin);
        double tca = originToCenter.dot(ray.direction);
        double distanceSqr = originToCenter.lengthSquared() - tca * tca;

        double radiusSqr = radius * radius;
        if (distanceSqr > radiusSqr) {
            // distance from center to ray is larger than radius, i.e. no collision
            return null;
        }

        double thc = Math.sqrt(radiusSqr - distanceSqr);

        t = tca - thc;
        if (t < 0) {
            t = tca + thc;
            if (t < 0) {
                // collision occurs before ray origin, i.e. no collision
                return null;
            }
        }

        Vector3 collisionPosition = ray.origin.add(ray.direction.multiply(t));
        Vector3 normal = collisionPosition.subtract(center);

        return new RayCollision(ray, normal, collisionPosition);
    }
}
