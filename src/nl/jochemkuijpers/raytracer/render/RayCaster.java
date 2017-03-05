package nl.jochemkuijpers.raytracer.render;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.scene.Light;
import nl.jochemkuijpers.raytracer.scene.Material;
import nl.jochemkuijpers.raytracer.scene.Scene;
import nl.jochemkuijpers.raytracer.scene.Shape;

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
        Vector3 totalLight = new Vector3();

        // recursive stop condition
        if (depth > maxDepth) {
            return new Vector3();
        }

        // find nearest collision
        RayCollision collision = findNearestCollision(ray);
        if (collision == null) {
            return new Vector3();
        }

        Material material = collision.shape.material;

        totalLight.addMultiple(calculateLighting(ray, collision), (1 - material.reflectivity - material.transparency));

        if (material.reflectivity > 0) {
            Vector3 reflectionDirection = new Vector3(ray.direction).addMultiple(collision.normal, Vector.dot(collision.normal, ray.direction) * -2);
            Ray reflectionRay = new Ray(collision.collidePosition, reflectionDirection);

            Vector3 reflection = cast(reflectionRay, depth + 1);

            totalLight.addMultiple(reflection, material.reflectivity);
        }

        // TODO cast ray to all light sources and set light based on cosine thingy

        // TODO cast recursive rays based on material properties
        return totalLight;
    }

    private RayCollision findNearestCollision(Ray ray) {
        List<Shape> shapes = scene.getCandidateShapes(ray);

        RayCollision nearestCollision = null;
        RayCollision collision;
        double nearestDistance = 1e30;
        double distance;

        for (Shape shape : shapes) {
            collision = shape.test(ray);
            if (collision == null) {
                continue;
            }

            distance = Vector.differenceLengthSquared(ray.origin, collision.collidePosition);
            if (distance < nearestDistance && distance > 1e-10) {
                nearestDistance = distance;
                nearestCollision = collision;
            }
        }

        return nearestCollision;
    }

    private Vector3 calculateLighting(Ray ray, RayCollision collision) {
        List<Light> lights = scene.getCandidateLights(ray);

        Vector3 addVector = new Vector3();
        Vector3 totalLight = new Vector3();
        Vector3 lightPosition = new Vector3();
        Ray lightRay = new Ray(collision.collidePosition, new Vector3());
        RayCollision lightRayCollision;
        for (Light light: lights) {
            light.getPosition(collision, lightPosition);
            lightRay.direction.set(lightPosition).subtract(lightRay.origin).normalize();

            lightRayCollision = findNearestCollision(lightRay);

            // a collision!
            if (lightRayCollision != null) {
                // collision before light position
                if (Vector.differenceLengthSquared(lightRay.origin, lightRayCollision.collidePosition) <
                        Vector.differenceLengthSquared(lightRay.origin, lightPosition)) {
                    continue;
                }
            }

            light.calculateLight(collision, addVector);
            totalLight.add(addVector);
        }

        return totalLight.multiply(collision.shape.material.color);
    }

}
