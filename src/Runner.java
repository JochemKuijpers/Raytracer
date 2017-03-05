import io.nayuki.png.DumbPngOutput;
import nl.jochemkuijpers.raytracer.Timer;
import nl.jochemkuijpers.raytracer.color.AutoExposureDitheringGammaStrategy;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.Camera;
import nl.jochemkuijpers.raytracer.render.RayCaster;
import nl.jochemkuijpers.raytracer.render.SensorImage;
import nl.jochemkuijpers.raytracer.scene.DirectionalLight;
import nl.jochemkuijpers.raytracer.scene.Material;
import nl.jochemkuijpers.raytracer.scene.Scene;
import nl.jochemkuijpers.raytracer.scene.Sphere;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Runner {

    private final Scene scene;

    public static void main(String[] args) throws IOException {
        new Runner().run();
    }

    public Runner() {
        this.scene = new Scene();
    }

    public void run() {
        Timer timer = new Timer();

        SensorImage sensorImage = new SensorImage(1920, 1080, 2);

        buildScene();

        Camera camera = new Camera(
                new Vector3(10, -10, 10),
                new Vector3(.5, 1, -0.1),
                new Vector3(0, 0, 1),
                60
        );

        RayCaster rc = new RayCaster(scene, 10);

        timer.mark("Initialize");

        camera.render(sensorImage, rc);

        timer.mark("Raytracing");

//        int[][] image = sensorImage.generateRgbImage(new AutoExposureGammaStrategy(1.8));
        int[][] image = sensorImage.generateRgbImage(new AutoExposureDitheringGammaStrategy(1.8));

        timer.mark("RGB conversion");

        try (OutputStream out = new FileOutputStream("output.png")) {
            DumbPngOutput.write(image, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer.mark("PNG export");
        timer.print();
    }

    private void buildScene() {
        Material chrome = new Material(new Vector3(1, 1, 1), 0, .95, 1);
        Material white = new Material(new Vector3(1, 1, 1), 0, 0.05, 1);

        Material glass = new Material(new Vector3(1, 1, 1), 0.9, 0.0, 1.05);

        Material red = new Material(new Vector3(1, 0.1, 0.1), 0, 0, 1);
        Material green = new Material(new Vector3(0.1, 1, 0.1), 0, 0, 1);
        Material blue = new Material(new Vector3(0.1, 0.1, 1), 0, 0, 1);

        scene.add(new Sphere(chrome, new Vector3(15, 30, 7), 7));
        scene.add(new Sphere(white, new Vector3(40, 40, 7), 7));
        scene.add(new Sphere(glass, new Vector3(30, 15, 7), 7));

        scene.add(new Sphere(red, new Vector3(0, 0, -10000), 10000));
        scene.add(new Sphere(green, new Vector3(0, 10100, 0), 10000));
        scene.add(new Sphere(blue, new Vector3( 10100, 0, 0), 10000));

        scene.add(new DirectionalLight(new Vector3(0.5, 0.5, -1), new Vector3(1, 1, 1)));
        scene.add(new DirectionalLight(new Vector3(.7, .2, -.2), new Vector3(0.33, 1, 0.66)));
        scene.add(new DirectionalLight(new Vector3(.3, 0.6, -.1), new Vector3(0.66, 0.33, 1)));
    }
}
