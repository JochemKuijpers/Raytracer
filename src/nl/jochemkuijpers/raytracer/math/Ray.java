package nl.jochemkuijpers.raytracer.math;

public class Ray {
    public final Vector3 origin;
    public final Vector3 direction;

    public Vector3 light = new Vector3(0, 0, 0);

    /**
     * Create a ray with a starting position and a direction
     *
     * @param origin    origin postion
     * @param direction ray direction
     */
    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction.normalized();
    }

    @Override
    public String toString() {
        return direction.toString();
    }
}
