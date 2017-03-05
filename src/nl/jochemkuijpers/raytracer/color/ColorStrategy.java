package nl.jochemkuijpers.raytracer.color;


import nl.jochemkuijpers.raytracer.render.SensorImage;

public interface ColorStrategy {
    /**
     * Return an RGB integer for a given x and y.
     *
     * @param sensorImage sensor image
     * @param x           width position
     * @param y           height position
     * @return RGB integer corresponding to the current position on the sensor image
     */
    int color(SensorImage sensorImage, int x, int y);
}
