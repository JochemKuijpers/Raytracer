package nl.jochemkuijpers.raytracer.scene;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.scene.Light;
import nl.jochemkuijpers.raytracer.scene.Shape;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    List<Shape> shapes;
    List<Light> lights;

    public Scene() {
        this.shapes = new ArrayList<>();
        this.lights = new ArrayList<>();
    }

    /**
     * Returns a list of shapes that might intersect the ray.
     *
     * @param ray ray
     * @return list of shapes that might intersect
     */
    public List<Shape> getCandidateShapes(Ray ray) {
        // just return all shapes for now..
        return shapes;
    }

    /**
     * Returns a list of lights that might be relevant to the ray.
     *
     * @param ray ray
     * @return list of lights that might be relevant to the ray.
     */
    public List<Light> getCandidateLights(Ray ray) {
        // just return all lights for now..
        return lights;
    }

    /**
     * Add a shape to the scene.
     *
     * @param shape scene to add
     */
    public void add(Shape shape) {
        this.shapes.add(shape);
    }

    /**
     * Add a light to the scene.
     *
     * @param light light to add
     */
    public void add(Light light) {
        this.lights.add(light);
    }
}
