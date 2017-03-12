package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.RayCollision;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.RayCaster;

import java.util.List;

public class TransparentMaterial implements Material {
    private final double refractiveIndex;

    public TransparentMaterial(double refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    @Override
    public Vector3 calculateLight(RayCaster caster, RayCollision collision, Ray ray, List<Light> lights) {
        Vector3 light = new Vector3();
        double reflectToRefractRatio = Materials.fresnel(ray, collision, refractiveIndex);

        // refraction
        if (reflectToRefractRatio < 1) {
            Vector3 refractionLight = caster.refract(ray, collision, refractiveIndex);
            if (refractionLight != null) {
                light.addMultiple(refractionLight, 1 - reflectToRefractRatio);
            }
        }

        // reflection
        if (reflectToRefractRatio > 0) {
            Vector3 reflectionLight = caster.reflect(ray, collision);
            light.addMultiple(reflectionLight, reflectToRefractRatio);
        }

        // TODO specular reflections of lights

        return light;
    }
}
