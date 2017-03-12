package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.RayCaster;

import java.util.List;

public interface Material {

    /**
     * Calculate the outgoing light for the point of collision.
     *
     * @param caster    raycaster used to cast successive rays
     * @param collision collision data
     * @param ray       origin ray
     * @param lights    lights illuminating this point
     * @return color of the point of collision
     */
    public Vector3 calculateLight(RayCaster caster, RayCollision collision, Ray ray, List<Light> lights);
}