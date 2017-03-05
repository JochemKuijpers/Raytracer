package nl.jochemkuijpers.raytracer.color;

import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.SensorImage;

public class AutoExposureGammaStrategy implements ColorStrategy {
    private final double gamma;

    public AutoExposureGammaStrategy(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public int[][] execute(SensorImage sensorImage) {
        int[][] rgb = new int[sensorImage.getHeight()][sensorImage.getWidth()];
        Vector3 maxLight = sensorImage.getMaxLight();
        double exposure = Math.max(maxLight.x, Math.max(maxLight.y, maxLight.z));

        int r, g, b;
        Vector3 light;
        for (int y = 0; y < sensorImage.getHeight(); y++) {
            for (int x = 0; x < sensorImage.getWidth(); x++) {
                light = sensorImage.getLight(x, y);

                r = Color.toInt(Color.gammaEncode(light.x / exposure, gamma), 0, 255);
                g = Color.toInt(Color.gammaEncode(light.y / exposure, gamma), 0, 255);
                b = Color.toInt(Color.gammaEncode(light.z / exposure, gamma), 0, 255);

                rgb[y][x] = r << 16 | g << 8 | b;
            }
        }
        return rgb;
    }
}
