package nl.jochemkuijpers.raytracer.math;

import nl.jochemkuijpers.raytracer.scene.Shape;

public class RayCollision {
    public final Shape shape;
    public final Vector3 normal;
    public final Vector3 collidePosition;

    public RayCollision(Shape shape, Vector3 normal, Vector3 collidePosition) {
        this.shape = shape;
        this.normal = normal;
        this.collidePosition = collidePosition;
    }
}
