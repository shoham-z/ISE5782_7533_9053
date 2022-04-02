package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
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
    public List<Point> findIntersections(Ray ray) {
        if (this.center.equals(ray.getStart())){
            List <Point> intersection = new LinkedList<Point>();
            intersection.add(ray.getPoint(this.radius));
            return intersection;
        }
        Vector u = this.center.subtract(ray.getStart());
        double tm = ray.getDirection().dotProduct(u);
        if(tm < 0)
            return null;
        double d = Math.sqrt(alignZero(u.lengthSquared() - tm * tm));
        if (d >= this.radius)
            return null;
        List <Point> intersection = new LinkedList<Point>();
        double th = Math.sqrt(alignZero(this.radius * this.radius - d * d));
        double t1 = alignZero(tm + th), t2 = alignZero(tm - th);
        if (t2 < t1 && t2 > 0){
            t1 = t1 + t2;
            t2 = t1 - t2;
            t1 = t1 - t2;
        }
        if (t1 > 0)
            intersection.add(ray.getPoint(t1));
        if (t2 > 0)
            intersection.add(ray.getPoint(t2));
        return intersection;
    }
}
