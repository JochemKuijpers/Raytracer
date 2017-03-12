package nl.jochemkuijpers.raytracer.render;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.scene.Scene;
import nl.jochemkuijpers.raytracer.scene.Shape;

import java.util.List;

public class RayCaster {
    private final Scene scene;
    private final int maxDepth;
    private int depth;

    public RayCaster(Scene scene, int maxDepth) {
        this.scene = scene;
        this.maxDepth = maxDepth;
        this.depth = 0;
    }

    /**
     * Casts a ray and returns it's light value given by the collision shape material.
     *
     * @param ray ray to cast
     * @return light value of the collision point
     */
    public Vector3 cast(Ray ray) {
        depth += 1;

        // recursive stop condition
        if (depth > maxDepth) {
            depth -= 1;
            return new Vector3();
        }

        RayCollision collision = findNearestCollision(ray);

        // return on no collision
        if (collision == null) {
            depth -= 1;
            return new Vector3();
        }

        // let the material decide what light is returned
        Vector3 lightValue = collision.shape.material.calculateLight(
                this, collision, ray, scene.getCandidateLights(ray, collision)
        );

        depth -= 1;
        return lightValue;
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
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestCollision = collision;
            }
        }

        return nearestCollision;
    }

//    private Vector3 calculateLighting(Ray ray, RayCollision collision) {
//        List<Light> lights = scene.getCandidateLights(ray, collision);
//
//        Vector3 addVector = new Vector3();
//        Vector3 totalLight = new Vector3();
//        Vector3 lightPosition = new Vector3();
//
//        Ray lightRay = new Ray(
//                Vector.addMultiple(collision.collidePosition, collision.normal, 1e-10, new Vector3()),
//                new Vector3()
//        );
//
//        RayCollision lightRayCollision;
//        for (Light light : lights) {
//            light.getPosition(collision, lightPosition);
//            lightRay.direction.set(lightPosition).subtract(lightRay.origin).normalize();
//
//            lightRayCollision = findNearestCollision(lightRay);
//
//            // a collision!
//            if (lightRayCollision != null) {
//                // collision before light position
//                if (Vector.differenceLengthSquared(lightRay.origin, lightRayCollision.collidePosition) <
//                        Vector.differenceLengthSquared(lightRay.origin, lightPosition)) {
//                    continue;
//                }
//            }
//
//            light.calculateLight(collision, addVector);
//            totalLight.add(addVector);
//        }
//
//        return totalLight.multiply(collision.shape.material.color);
//    }

    public Vector3 reflect(Ray ray, RayCollision collision) {
        Vector3 reflectionDirection = new Vector3(ray.direction)
                .addMultiple(collision.normal, Vector.dot(collision.normal, ray.direction) * -2);

        return cast(new Ray(Vector.addMultiple(collision.collidePosition, collision.normal, 1e-10, new Vector3()), reflectionDirection));
    }

    public Vector3 refract(Ray ray, RayCollision collision, double refractiveIndex) {
        double cosi = Vector.dot(ray.direction, collision.normal);
        double etai = 1;
        double etat = refractiveIndex;
        Vector3 normal = new Vector3(collision.normal);

        if (cosi < 0) {
            cosi = -cosi;
        } else {
            etai = etat;
            etat = 1;
            normal.invert();
        }

        double eta = etai / etat;
        double k = 1 - eta * eta * (1 - cosi * cosi);

        if (k < 0) {
            return null;
        }

        Vector3 refractionDirection = new Vector3()
                .addMultiple(ray.direction, eta)
                .addMultiple(normal, eta * cosi - Math.sqrt(k));

        return cast(new Ray(Vector.addMultiple(collision.collidePosition, normal, -1e-10, new Vector3()), refractionDirection));
    }
}
