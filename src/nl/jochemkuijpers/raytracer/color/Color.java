package nl.jochemkuijpers.raytracer.color;

public class Color {
    /**
     * Perform gamma correction on a normalized light value (between 0 and 1)
     * @param lightValue    light value between 0 and 1
     * @param gamma         gamma value (e.g. 2.2)
     * @return              gamma-corrected light value for storage in an image file
     */
    public static double gammaEncode(double lightValue, double gamma) {
        return Math.pow(lightValue, 1 / gamma);
    }

    /**
     * Perform the inverse of gamma correction on a normalized gamma-corrected light value (between 0 and 1)
     * @param lightValue    gamma corrected light value between 0 and 1
     * @param gamma         gamma value (e.g. 2.2)
     * @return              linear light value for color calculations
     */
    public static double gammaDecode(double lightValue, double gamma) {
        return Math.pow(lightValue, gamma);
    }

    /**
     * Round the light value to the nearest in in the min-max interval (inclusive)
     * @param lightValue    light value between 0 and 1
     * @param min           lower-bound of the integer interval (inclusive)
     * @param max           upper-bound of the integer interval (inclusive)
     * @return              integer value of the light value on the min-max interval (inclusive)
     */
    public static int toInt(double lightValue, int min, int max) {
        int x = (int) Math.round(lightValue * (max - min));
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
}
