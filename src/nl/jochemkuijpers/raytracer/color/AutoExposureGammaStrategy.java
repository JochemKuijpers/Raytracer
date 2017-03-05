package nl.jochemkuijpers.raytracer.color;

import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.SensorImage;

public class AutoExposureGammaStrategy implements ColorStrategy {
    private final double gamma;

    public AutoExposureGammaStrategy() {
        this(2.2);
    }

    public AutoExposureGammaStrategy(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public int color(SensorImage sensorImage, int x, int y) {
        Vector3 light = sensorImage.getLight(x, y);
        Vector3 maxLight = sensorImage.getMaxLight();

        double exposure = Math.max(maxLight.x, Math.max(maxLight.y, maxLight.z));

        int r = Color.toInt(Color.gammaEncode(light.x / exposure, gamma), 0, 255);
        int g = Color.toInt(Color.gammaEncode(light.y / exposure, gamma), 0, 255);
        int b = Color.toInt(Color.gammaEncode(light.z / exposure, gamma), 0, 255);

        return r << 16 | g << 8 | b;
    }
}
