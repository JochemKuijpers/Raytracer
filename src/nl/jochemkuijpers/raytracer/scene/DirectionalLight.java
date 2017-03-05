package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class DirectionalLight implements Light {
    private final Vector3 direction;
    private final Vector3 light;

    public DirectionalLight(Vector3 direction, Vector3 light) {
        this.direction = direction.normalize();
        this.light = light;
    }

    @Override
    public void calculateLight(RayCollision collision, Vector3 light) {
        light
                .set(this.light)
                .multiply(Math.max(0, -Vector.dot(collision.normal, direction)));
    }

    @Override
    public void getPosition(RayCollision collision, Vector3 position) {
        position.set(collision.collidePosition).addMultiple(direction, -1e15);
    }
}
