package geometries;

import primitives.*;

import static primitives.Util.*;

import java.util.List;

/**
 * Sphere class represents three-dimensional Sphere in 3D cartesian Coordinate system
 */
public class Sphere implements Geometry {
    private final Point center;
    private final double radius;
    private final double radius2;

    /**
     * Constructor for a sphere
     *
     * @param center center Point of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
        this.radius2 = radius * radius;
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
    public List<Point> findIntersections(Ray ray) {
        Vector u;
        try {
            u = this.center.subtract(ray.getStart());
        } catch (IllegalArgumentException ignore) {
            return List.of(ray.getPoint(this.radius));
        }

        double tm = ray.getDirection().dotProduct(u);
        double d2 = u.lengthSquared() - tm * tm;
        double th2 = alignZero(this.radius2 - d2);
        if (th2 <= 0) return null;

        double th = Math.sqrt(th2);
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        return t1 <= 0 ? List.of(ray.getPoint(t2)) : List.of(ray.getPoint(t1), ray.getPoint(t2));
    }
}
