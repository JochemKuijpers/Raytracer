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
     *
     * @param ray ray to cast
     * @return light value
     */
    public Vector3 cast(Ray ray) {
        return cast(ray, 1);
    }

    private Vector3 cast(Ray ray, int depth) {
        Vector3 totalLight = new Vector3();
        Thread thread = new Thread();

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

        double reflectToRefractRatio;

        if (material.isTransparent) {
            reflectToRefractRatio = fresnel(ray, collision);

            // refraction
            if (reflectToRefractRatio < 1) {
                Ray refractionRay = refract(ray, collision);
                if (refractionRay != null) {
                    Vector3 refraction = castInternal(refractionRay, depth + 1);
                    refraction.multiply(material.color);
                    totalLight.addMultiple(refraction, 1 - reflectToRefractRatio);
                }
            }
        } else {
            // direct lighting
            totalLight.addMultiple(calculateLighting(ray, collision), 1 - material.reflectivity);

            // set reflection ratio to material reflectivity
            reflectToRefractRatio = material.reflectivity;
        }

        // reflection
        Ray reflectionRay = reflect(ray, collision);
        Vector3 reflection = cast(reflectionRay, depth + 1);
        totalLight.addMultiple(reflection, reflectToRefractRatio);

        return totalLight;
    }

    private Vector3 castInternal(Ray internalRay, int depth) {
        if (depth > maxDepth) {
            return new Vector3();
        }

        RayCollision collision = findNearestCollision(internalRay);
        if (collision == null) {
            return new Vector3();
        }

        Ray refractionRay = refract(internalRay, collision);

        if (refractionRay != null) {
            return cast(refractionRay, depth + 1);
        }

        Ray reflectionRay = reflect(internalRay, collision);
        return castInternal(reflectionRay, depth + 1);
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

    private Vector3 calculateLighting(Ray ray, RayCollision collision) {
        List<Light> lights = scene.getCandidateLights(ray);

        Vector3 addVector = new Vector3();
        Vector3 totalLight = new Vector3();
        Vector3 lightPosition = new Vector3();

        Ray lightRay = new Ray(
                Vector.addMultiple(collision.collidePosition, collision.normal, 1e-10, new Vector3()),
                new Vector3()
        );

        RayCollision lightRayCollision;
        for (Light light : lights) {
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

    private Ray reflect(Ray ray, RayCollision collision) {
        Vector3 reflectionDirection = new Vector3(ray.direction)
                .addMultiple(collision.normal, Vector.dot(collision.normal, ray.direction) * -2);

        return new Ray(
                Vector.addMultiple(collision.collidePosition, collision.normal, 1e-10, new Vector3()),
                reflectionDirection
        );
    }

    private Ray refract(Ray ray, RayCollision collision) {
        double cosi = Vector.dot(ray.direction, collision.normal);
        double etai = 1;
        double etat = collision.shape.material.refractiveIndex;
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

        return new Ray(
                Vector.addMultiple(collision.collidePosition, normal, -1e-10, new Vector3()),
                refractionDirection
        );
    }

    /**
     * Returns the amount of reflection (0.0 - 1.0) using Fresnel equations
     *
     * @param ray       incoming ray
     * @param collision collision data
     * @return amount of reflection (return value) to use vs refraction (1 minus return value)
     */
    private double fresnel(Ray ray, RayCollision collision) {
        double cosi = Vector.dot(ray.direction, collision.normal);
        double etai = 1;
        double etat = collision.shape.material.refractiveIndex;
        Vector3 normal = new Vector3(collision.normal);

        if (cosi < 0) {
            cosi = -cosi;
        } else {
            etai = etat;
            etat = 1;
            normal.invert();
        }

        double sint = (etai / etat) * Math.sqrt(Math.max(0, 1 - cosi * cosi));

        // total internal reflection
        if (sint >= 1) {
            return 1;
        }

        double cost = Math.sqrt(Math.max(0, 1 - sint * sint));
        double Rs = ((etat * cosi) - (etai * cost)) / ((etat * cosi) + (etai * cost));
        double Rp = ((etai * cosi) - (etat * cost)) / ((etai * cosi) + (etat * cost));

        return Math.max(0, Math.min(1, (Rs * Rs + Rp * Rp) / 2));

    }

}
