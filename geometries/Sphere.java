package geometries;

import primitives.*;

import java.util.List;

/**
 * Sphere class represents three-dimensional Sphere in 3D cartesian Coordinate system
 */
public class Sphere implements Geometry {
    private final Point center;
    private final double radius;

    /**
     * Constructor for a sphere
     *
     * @param center center Point of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Getter for the center Point of the sphere
     *
     * @return center Point
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Getter for the radius of the sphere
     *
     * @return radius
     */
    public double getRadius() {
        return this.radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(this.center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" + center + ", " + radius + '}';
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
