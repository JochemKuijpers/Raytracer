package nl.jochemkuijpers.raytracer.color;


public interface ColorStrategy {
    /**
     * Reset internal state.
     */
    void reset();

    /**
     * Return an RGB integer for the given x and y and sample size. A sample size of 2 means (x,y), (x+1,y),
     * (x,y+1), (x+1,y+1) must be converted into a single color. This range can be expected to be within the lightMap
     * dimensions, so no boundary checks need to be performed.
     *
     * @param lightMap   light map
     * @param x          x position
     * @param y          y position
     * @param sampleSize size of sample (square)
     * @return RGB integer corresponding to the current position on the light map
     */
    int color(LightMap lightMap, int x, int y, int sampleSize);
}
