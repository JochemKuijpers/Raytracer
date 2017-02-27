package nl.jochemkuijpers.raytracer.render;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    List<Shape> shapes;

    public Scene() {
        this.shapes = new ArrayList<>();
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
     * Add a shape to the scene.
     *
     * @param shape shape to add
     */
    public void add(Shape shape) {
        this.shapes.add(shape);
    }
}
