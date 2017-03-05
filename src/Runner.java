import io.nayuki.png.DumbPngOutput;
import nl.jochemkuijpers.raytracer.Timer;
import nl.jochemkuijpers.raytracer.color.AutoExposureGammaStrategy;
import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.Camera;
import nl.jochemkuijpers.raytracer.render.RayCaster;
import nl.jochemkuijpers.raytracer.render.Scene;
import nl.jochemkuijpers.raytracer.render.SensorImage;
import nl.jochemkuijpers.raytracer.shape.Sphere;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Runner {

    public static void main(String[] args) throws IOException {

        Timer timer = new Timer();

        SensorImage sensorImage = new SensorImage(1920, 1080, 2);

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

        timer.mark("Initialize");

        camera.render(sensorImage, rc);

        timer.mark("Raytracing");

        int[][] image = sensorImage.generateRgbImage(new AutoExposureGammaStrategy(2.2));
        long rgbTime = System.nanoTime();

        timer.mark("RGB conversion");

        try (OutputStream out = new FileOutputStream("output.png")) {
            DumbPngOutput.write(image, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer.mark("PNG export");

        timer.print();

        Runtime.getRuntime().exec("eog output.png");
    }
}
