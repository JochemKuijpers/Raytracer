package nl.jochemkuijpers.raytracer.render;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.shape.Shape;

import java.util.List;

public class RayCaster {
    private final Scene scene;
    private final int maxDepth;

    public RayCaster(Scene scene, int maxDepth) {
        this.scene = scene;
        this.maxDepth = maxDepth;
    }

    /**
     * Casts a ray and returns it's light value. May cast rays recursively until maxDepth is reached.
     * @param ray ray to cast
     * @return light value
     */
    public Vector3 cast(Ray ray) {
        return cast(ray, 1);
    }

    private Vector3 cast(Ray ray, int depth) {
        // recursive stop condition
        if (depth > maxDepth) {
            return new Vector3();
        }

        // find nearest collision
        List<Shape> shapes = scene.getCandidateShapes(ray);
        RayCollision collision = findNearestCollision(ray, shapes);

        if (collision == null) {
            return new Vector3();
        }

        // TODO set ray color based on material properties

        // TODO cast ray to all light sources and set light based on cosine thingy

        // TODO cast recursive rays based on material properties

        return new Vector3(collision.normal);
    }

    private RayCollision findNearestCollision(Ray ray, List<Shape> shapes) {
        double nearestDistance = 1e30;
        double distance;
        RayCollision nearestCollision = null;
        RayCollision collision;

        for (Shape shape : shapes) {
            collision = shape.test(ray);
            if (collision == null) {
                continue;
            }

            distance = Vector.differenceLengthSquared(ray.origin, collision.collidePosition);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestCollision = collision;
            }
        }

        return nearestCollision;
    }


}
