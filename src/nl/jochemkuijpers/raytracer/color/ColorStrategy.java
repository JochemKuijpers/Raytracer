package nl.jochemkuijpers.raytracer.color;


import nl.jochemkuijpers.raytracer.render.SensorImage;

public interface ColorStrategy {

    /**
     * Convert the sensor image to an 2D RGB array
     *
     * @param sensorImage sensor image
     * @return rgb array
     */
    int[][] execute(SensorImage sensorImage);
    
}
