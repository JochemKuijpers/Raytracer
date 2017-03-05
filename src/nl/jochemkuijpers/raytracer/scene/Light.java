package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.Vector3;

public interface Light {
    /**
     * Calculate the light the ray origin will receive
     *
     * @param ray   ray
     * @param light the light to set
     */
    void calculateLight(Ray ray, Vector3 light);
}
