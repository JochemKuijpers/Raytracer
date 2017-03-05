package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class Sphere extends Shape {
    private final Vector3 center;
    private final double radius;

    public Sphere(Material material, Vector3 center, double radius) {
        super(material);

        this.center = center;
        this.radius = radius;
    }

    @Override
    public RayCollision test(Ray ray) {
        // t is the solution for collisionPosition = ray.origin + t * ray.direction
        double t;

        Vector3 originToCenter = Vector.subtract(center, ray.origin, new Vector3());
        double tca = Vector.dot(originToCenter, ray.direction);
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

        // optimization: we can recycle originToCenter to store the answer since it is not being used anymore
        Vector3 collisionPosition = Vector.addMultiple(ray.origin, ray.direction, t, originToCenter);
        Vector3 normal = Vector.subtract(collisionPosition, center, new Vector3()).normalize();

        return new RayCollision(this, normal, collisionPosition);
    }
}
