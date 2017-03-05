package nl.jochemkuijpers.raytracer.math;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(Vector3 copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 set(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        return this;
    }

    public Vector3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 add(Vector3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vector3 addMultiple(Vector3 other, double s) {
        this.x += other.x * s;
        this.y += other.y * s;
        this.z += other.z * s;
        return this;
    }

    public Vector3 subtract(Vector3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vector3 multiply(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
        return this;
    }

    public Vector3 multiply(Vector3 other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector3 normalize() {
        double length = length();
        if (length > 0) {
            multiply(1 / length);
        }
        return this;
    }

    public Vector3 invert() {
        multiply(-1);
        return this;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + "," + this.z + ")";
    }
}
