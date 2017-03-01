import io.nayuki.png.DumbPngOutput;
import nl.jochemkuijpers.raytracer.ImageRenderer;
import nl.jochemkuijpers.raytracer.color.AutoExposureGammaStrategy;
import nl.jochemkuijpers.raytracer.color.LightMap;
import nl.jochemkuijpers.raytracer.math.Vector2;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.Camera;
import nl.jochemkuijpers.raytracer.render.RayCaster;
import nl.jochemkuijpers.raytracer.render.Scene;
import nl.jochemkuijpers.raytracer.shape.Sphere;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Runner {

    public static void main(String[] args) {
        Vector2 resolution = new Vector2(1920*4, 1080*4);
        int ssFactor = 1;

        long startTime = System.nanoTime();

        Camera camera = new Camera(
                new Vector3(0, 0, 0),
                new Vector3(1, 1, 0),
                new Vector3(0, 0, 1),
                60
        );

        Scene scene = new Scene();
        scene.add(new Sphere(new Vector3(10, 4, 2), 3));
        scene.add(new Sphere(new Vector3(6, 12, 1), 5));

        RayCaster rc = new RayCaster(scene, 1);
        LightMap lightMap = camera.render(resolution.multiply(ssFactor), rc);

        long rayTime = System.nanoTime();
        System.out.println();
        System.out.println("Raytracing done.");

        ImageRenderer ir = new ImageRenderer(new AutoExposureGammaStrategy(2.2));
        int[][] image = ir.render(lightMap, ssFactor);

        long rgbTime = System.nanoTime();
        System.out.println("RGB conversion done.");


        try (OutputStream out = new FileOutputStream("output.png")) {
            DumbPngOutput.write(image, out);
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
