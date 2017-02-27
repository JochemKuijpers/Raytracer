import nl.jochemkuijpers.raytracer.ImageRenderer;
import nl.jochemkuijpers.raytracer.color.AutoExposureGammaStrategy;
import nl.jochemkuijpers.raytracer.color.LightMap;
import nl.jochemkuijpers.raytracer.math.Vector2;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.Camera;
import nl.jochemkuijpers.raytracer.render.RayCaster;
import nl.jochemkuijpers.raytracer.render.Scene;
import nl.jochemkuijpers.raytracer.shape.Sphere;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        Camera camera = new Camera(
                new Vector3(0, 2, 3),
                new Vector3(0, -1, -.2),
                new Vector3(0, 0, 1),
                50
        );

        Scene scene = new Scene();
        scene.add(new Sphere(new Vector3(-4, -10, 1.2), 1.2));
        scene.add(new Sphere(new Vector3(-1, -3, .8), .8));
        scene.add(new Sphere(new Vector3(.5, -6, 1), 1));
        scene.add(new Sphere(new Vector3(2, -5, 2.3), 2.3));

        RayCaster rc = new RayCaster(scene, 1);
        LightMap lightMap = camera.render(new Vector2(1920, 1080), rc);

        long rayTime = System.nanoTime();
        System.out.println("Raytracing done.");

        ImageRenderer ir = new ImageRenderer(new AutoExposureGammaStrategy(2.2));
        RenderedImage image = ir.render(lightMap, 1);

        long rgbTime = System.nanoTime();
        System.out.println("RGB conversion done.");


        try {
            ImageIO.write(image, "png", new File("output.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long fileTime = System.nanoTime();
        System.out.println("File written.");
        System.out.println("Execution time    : " + (fileTime - startTime) / 1000000.0 + "ms");
        System.out.println(" > Raytracing     : " + (rayTime - startTime) / 1000000.0 + "ms");
        System.out.println(" > RGB conversion : " + (rgbTime - rayTime) / 1000000.0 + "ms");
        System.out.println(" > PNG export     : " + (fileTime - rgbTime) / 1000000.0 + "ms");
    }
}
