package nl.jochemkuijpers.raytracer.math;

public class Vector3 {
    public final double x;
    public final double y;
    public final double z;

    public final static Vector3 ZERO = new Vector3(0, 0, 0);

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 multiply(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector3 normalized() {
        double l = length();
        if (l == 1) {
            return this;
        }
        if (l == 0) {
            return Vector3.ZERO;
        }
        return multiply(1 / l);
    }

    public Vector3 inverted() {
        return new Vector3(-x, -y, -z);
    }

    public double dot(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x
        );
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
