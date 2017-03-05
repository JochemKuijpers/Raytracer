import io.nayuki.png.DumbPngOutput;
import nl.jochemkuijpers.raytracer.Timer;
import nl.jochemkuijpers.raytracer.color.AutoExposureDitheringGammaStrategy;
import nl.jochemkuijpers.raytracer.color.AutoExposureGammaStrategy;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.Camera;
import nl.jochemkuijpers.raytracer.render.RayCaster;
import nl.jochemkuijpers.raytracer.scene.DirectionalLight;
import nl.jochemkuijpers.raytracer.scene.Scene;
import nl.jochemkuijpers.raytracer.render.SensorImage;
import nl.jochemkuijpers.raytracer.scene.Material;
import nl.jochemkuijpers.raytracer.scene.Sphere;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Runner {

    public static void main(String[] args) throws IOException {

        Timer timer = new Timer();

        SensorImage sensorImage = new SensorImage(1920, 1080, 2);

        Camera camera = new Camera(
                new Vector3(-5, -5, 10),
                new Vector3(1, 1, -0.333),
                new Vector3(0, 0, 1),
                60
        );

        Scene scene = new Scene();

        Material red = new Material(new Vector3(1, 0, 0));
        Material white = new Material(new Vector3(1, 1, 1));

        scene.add(new Sphere(red, new Vector3(10, 4, 3), 3));
        scene.add(new Sphere(white, new Vector3(6, 12, 5), 5));
        scene.add(new Sphere(white, new Vector3(0, 0, -10000), 10000));
        scene.add(new Sphere(white, new Vector3(0, 10100, 0), 10000));
        scene.add(new Sphere(white, new Vector3( 10100, 0, 0), 10000));
        scene.add(new DirectionalLight(new Vector3(0, 0, -1), new Vector3(1, 1, 1)));
        scene.add(new DirectionalLight(new Vector3(.7, -.2, .8), new Vector3(0.1, 0.2, 0.2)));
        scene.add(new DirectionalLight(new Vector3(-.3, 0.6, .8), new Vector3(0.2, 0.1, 0.3)));

        RayCaster rc = new RayCaster(scene, 1);

        timer.mark("Initialize");

        camera.render(sensorImage, rc);

        timer.mark("Raytracing");

        int[][] image = sensorImage.generateRgbImage(new AutoExposureDitheringGammaStrategy(1.8, 0));

        timer.mark("RGB conversion");

        try (OutputStream out = new FileOutputStream("output.png")) {
            DumbPngOutput.write(image, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer.mark("PNG export");
        timer.print();
    }
}
