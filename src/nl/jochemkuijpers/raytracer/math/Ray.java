package nl.jochemkuijpers.raytracer.math;

public class Ray {
    public final Vector3 origin;
    public final Vector3 direction;

    /**
     * Create a ray
     */
    public Ray() {
        this(new Vector3(), new Vector3());
    }

    /**
     * Create a ray with a starting position and a direction
     *
     * @param origin    origin postion
     * @param direction ray direction
     */
    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    @Override
    public String toString() {
        return direction.toString();
    }
}
