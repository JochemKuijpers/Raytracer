package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.RayCaster;

import java.util.List;

public class LegacyMaterial implements Material {
    public final Vector3 color;
    public final double reflectivity;
    public final double refractiveIndex;
    public boolean isTransparent;

    public LegacyMaterial(Vector3 color, boolean isTransparent, double reflectivity, double refractiveIndex) {
        this.color = color;
        this.isTransparent = isTransparent;
        this.reflectivity = reflectivity;
        this.refractiveIndex = refractiveIndex;
    }

    @Override
    public Vector3 calculateLight(RayCaster caster, RayCollision collision, Ray ray, List<Light> lights) {
        return new Vector3(collision.normal).multiply(0.5).add(new Vector3(0.5, 0.5, 0.5));
    }
}
