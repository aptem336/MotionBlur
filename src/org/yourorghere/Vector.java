package org.yourorghere;

public class Vector {

    public double x, y, z;
    public static final Vector NULL = new Vector(0d, 0d, 0d);

    public Vector() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public void set(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public void add(Vector vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
    }

    public void add(Vector vector, double factor) {
        x += vector.x * factor;
        y += vector.y * factor;
        z += vector.z * factor;
    }
}
