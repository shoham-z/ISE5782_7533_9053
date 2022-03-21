package geometries;

import primitives.*;

/**
 * Sphere class represents three-dimensional Sphere in 3D cartesian Coordinate system
 */
public class Sphere implements Geometry{
    private final Point center;
    private final double radius;

    /**
     *
     * @param center center Point of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     *
     * @return center Point of sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     *
     * @return radius of sphere
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector normal = point.subtract(this.center);
        return normal.normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" + center + ", " + radius + '}';
    }
}
