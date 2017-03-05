package nl.jochemkuijpers.raytracer.math;

public class Vector {
    /**
     * Returns the dot product of vector a and vector b
     *
     * @param a vector a
     * @param b vector b
     * @return a (dot) b
     */
    public static double dot(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    /**
     * Sets vector c to the result of the addition of vector a and vector b
     *
     * @param a vector a
     * @param b vector b
     * @param c vector c
     * @return vector c
     */
    public static Vector3 add(Vector3 a, Vector3 b, Vector3 c) {
        c.x = a.x + b.x;
        c.y = a.y + b.y;
        c.z = a.z + b.z;
        return c;
    }

    /**
     * Sets vector c to the result of the addition of vector a and vector b multiplied by scalar s
     *
     * @param a vector a
     * @param b vector b
     * @param s scalar s
     * @param c vector c
     * @return vector c
     */
    public static Vector3 addMultiple(Vector3 a, Vector3 b, double s, Vector3 c) {
        c.x = a.x + b.x * s;
        c.y = a.y + b.y * s;
        c.z = a.z + b.z * s;
        return c;
    }

    /**
     * Sets vector c to the result of the subtraction of vector a and vector b
     *
     * @param a vector a
     * @param b vector b
     * @param c vector c
     * @return vecotr c
     */
    public static Vector3 subtract(Vector3 a, Vector3 b, Vector3 c) {
        c.x = a.x - b.x;
        c.y = a.y - b.y;
        c.z = a.z - b.z;
        return c;
    }

    /**
     * Sets vector b to the result of the multiplication of vector a and scalar s
     *
     * @param a vector a
     * @param s scalar s
     * @param b vector b
     * @return vector b
     */
    public static Vector3 multiply(Vector3 a, double s, Vector3 b) {
        b.x = a.x * s;
        b.y = a.y * s;
        b.z = a.z * s;
        return b;
    }

    /**
     * Sets vector b to the normalized vector a
     *
     * @param a vector a
     * @param b vector b
     * @return vector b
     */
    public static Vector3 normalize(Vector3 a, Vector3 b) {
        double length = a.length();
        if (length > 0) {
            b.x = a.x / length;
            b.y = a.y / length;
            b.z = a.z / length;
        } else {
            b.x = 0;
            b.y = 0;
            b.z = 0;
        }
        return b;
    }

    /**
     * Sets vector c to the result of the cross product of vector a and vector b
     *
     * @param a vector a
     * @param b vector b
     * @param c vector c
     * @return vector c
     */
    public static Vector3 cross(Vector3 a, Vector3 b, Vector3 c) {
        c.x = a.y * b.z - a.z * b.y;
        c.y = a.z * b.x - a.x * b.z;
        c.z = a.x * b.y - a.y * b.x;
        return c;
    }

    /**
     * Returns the square of the magnitude of the difference vector between vector a and vector b
     * @param a vector a
     * @param b vector b
     * @return square of the magnitude of the difference vector
     */
    public static double differenceLengthSquared(Vector3 a, Vector3 b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double dz = a.z - b.z;
        return (dx * dx + dy * dy + dz * dz);
    }

    public static Vector3 max(Vector3 a, Vector3 b, Vector3 c) {
        c.x = Math.max(a.x, b.x);
        c.y = Math.max(a.y, b.y);
        c.z = Math.max(a.z, b.z);
        return c;
    }
}
