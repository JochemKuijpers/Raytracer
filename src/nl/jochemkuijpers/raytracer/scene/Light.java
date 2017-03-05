package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector3;

public interface Light {
    /**
     * Calculate the light the collision position will receive
     *
     * @param collision collision information
     * @param light     the light to set
     */
    void calculateLight(RayCollision collision, Vector3 light);

    /**
     * Sets the position argument as the position of the light. Light sources may use the collision object to determine
     * the position of the light source, or other techniques including stochastic ones (especially when super sampling).
     *
     * @param collision collision information
     * @param position  the light source position to set
     */
    void getPosition(RayCollision collision, Vector3 position);
}
