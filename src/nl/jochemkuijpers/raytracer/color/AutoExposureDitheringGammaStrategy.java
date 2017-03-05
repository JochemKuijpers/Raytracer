package nl.jochemkuijpers.raytracer.color;

import nl.jochemkuijpers.raytracer.math.Vector3;
import nl.jochemkuijpers.raytracer.render.SensorImage;

public class AutoExposureDitheringGammaStrategy implements ColorStrategy {
    private final double gamma;
    private final double ditherAmount;

    public AutoExposureDitheringGammaStrategy(double gamma, double ditherAmount) {
        this.gamma = gamma;
        this.ditherAmount = ditherAmount;
    }

    @Override
    public int color(SensorImage sensorImage, int x, int y) {
        Vector3 light = sensorImage.getLight(x, y);
        Vector3 maxLight = sensorImage.getMaxLight();

        double exposure = Math.max(maxLight.x, Math.max(maxLight.y, maxLight.z));
        double dither = ((x + y) % 2 == 0) ? (ditherAmount / 256) : (-ditherAmount / 256);

        int r = Color.toInt(Color.gammaEncode(light.x / exposure, gamma) + dither, 0, 255);
        int g = Color.toInt(Color.gammaEncode(light.y / exposure, gamma) + dither, 0, 255);
        int b = Color.toInt(Color.gammaEncode(light.z / exposure, gamma) + dither, 0, 255);

        return r << 16 | g << 8 | b;
    }
}
