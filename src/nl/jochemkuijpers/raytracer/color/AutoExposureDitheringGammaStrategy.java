package nl.jochemkuijpers.raytracer.color;

import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.SensorImage;

public class AutoExposureDitheringGammaStrategy implements ColorStrategy {
    private final double gamma;

    private final double[][] thresholdMap = {
            { 0.0/16.0,  8.0/16.0,  2.0/16.0, 10.0/16.0},
            {12.0/16.0,  4.0/16.0, 14.0/16.0,  6.0/16.0},
            { 3.0/16.0, 11.0/16.0,  1.0/16.0,  9.0/16.0},
            {15.0/16.0,  7.0/16.0, 13.0/16.0,  5.0/16.0}
    };

    public AutoExposureDitheringGammaStrategy(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public int[][] execute(SensorImage sensorImage) {
        int[][] rgb = new int[sensorImage.getHeight()][sensorImage.getWidth()];
        Vector3 maxLight = sensorImage.getMaxLight();
        double exposure = Math.max(maxLight.x, Math.max(maxLight.y, maxLight.z));

        int r, g, b;
        Vector3 light;
        double dithering;
        for (int y = 0; y < sensorImage.getHeight(); y++) {
            for (int x = 0; x < sensorImage.getWidth(); x++) {
                light = sensorImage.getLight(x, y);

                dithering = 1.0/256 * (thresholdMap[y % thresholdMap.length][x % thresholdMap[0].length] - .5);

                r = Color.toInt(Color.gammaEncode(light.x / exposure, gamma) + dithering, 0, 255);
                g = Color.toInt(Color.gammaEncode(light.y / exposure, gamma) + dithering, 0, 255);
                b = Color.toInt(Color.gammaEncode(light.z / exposure, gamma) + dithering, 0, 255);

                rgb[y][x] = r << 16 | g << 8 | b;
            }
        }
        return rgb;
    }
}
