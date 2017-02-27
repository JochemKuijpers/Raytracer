package nl.jochemkuijpers.raytracer.color;

import nl.jochemkuijpers.raytracer.math.Vector3;

public class AutoExposureGammaStrategy implements ColorStrategy {
    private double maxLight = -1;
    private final double gamma;

    public AutoExposureGammaStrategy() {
        this(2.2);
    }

    public AutoExposureGammaStrategy(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void reset() {
        maxLight = -1;
    }

    @Override
    public int color(LightMap lightMap, int x, int y, int sampleSize) {
        if (maxLight < 0) {
            findMaxLight(lightMap, sampleSize);
        }

        Vector3 light = getAverageLight(lightMap, x, y, sampleSize);

        int r = clamp(0, (int) (Math.pow(light.x / maxLight, 1 / gamma) * 255.0), 255);
        int g = clamp(0, (int) (Math.pow(light.y / maxLight, 1 / gamma) * 255.0), 255);
        int b = clamp(0, (int) (Math.pow(light.z / maxLight, 1 / gamma) * 255.0), 255);

        return 255 << 24 | r << 16 | g << 8 | b;
    }

    /**
     * Return x if min >= x >= max, otherwise min/max.
     * @param min minimum value
     * @param x x
     * @param max maximum value
     * @return Math.min(max, Math.max(min, x));
     */
    private int clamp(int min, int x, int max) {
        return (min <= x) ? ((max >= x) ? x : max) : min;
    }

    /**
     * Returns the average light value of the sampled area
     *
     * @param lightMap   lightmap
     * @param x          left top x position
     * @param y          left top y position
     * @param sampleSize sample size (square: height and width)
     * @return
     */
    private Vector3 getAverageLight(LightMap lightMap, int x, int y, int sampleSize) {
        Vector3 avgLight = Vector3.ZERO;

        for (int sy = 0; sy < sampleSize; sy++) {
            for (int sx = 0; sx < sampleSize; sx++) {
                avgLight = avgLight.add(lightMap.getLight(x + sx, y + sy));
            }
        }

        return avgLight.multiply(1 / (sampleSize * sampleSize));
    }

    /**
     * The goal of this function is to find the largest light value in the image and set maxLight to this value for the
     * exposure calculation
     *
     * @param lightMap   lightmap
     * @param sampleSize sample size
     */
    private void findMaxLight(LightMap lightMap, int sampleSize) {
        for (int y = 0; y < lightMap.getHeight(); y += sampleSize) {
            for (int x = 0; x < lightMap.getWidth(); x += sampleSize) {
                Vector3 light = getAverageLight(lightMap, x, y, sampleSize);
                maxLight = Math.max(light.x, Math.max(light.y, Math.max(light.z, maxLight)));
            }
        }
    }
}
