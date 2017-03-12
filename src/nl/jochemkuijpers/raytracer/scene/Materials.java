package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class Materials {

    /**
     * Returns the amount of reflection (0.0 - 1.0) using Fresnel equations
     *
     * @param ray             incoming ray
     * @param collision       collision data
     * @param refractiveIndex refractive index of the material
     * @return amount of reflection (return value) to use vs refraction (1 minus return value)
     */
    public static double fresnel(Ray ray, RayCollision collision, double refractiveIndex) {
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
