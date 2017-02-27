package nl.jochemkuijpers.raytracer.math;

public class RayCollision {
    public final Ray ray;
    public final Vector3 normal;
    public final Vector3 collidePosition;

    public RayCollision(Ray ray, Vector3 normal, Vector3 collidePosition) {
        this.ray = ray;
        this.normal = normal;
        this.collidePosition = collidePosition;
    }
}
