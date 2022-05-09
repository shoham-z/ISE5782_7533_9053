package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Tube class represents a three-dimensional tube in 3D Cartesian coordinate
 * system
 */
public class Tube extends Geometry {
    private final Ray axis;
    private final double radius;

    /**
     * Getter for the axis ray of the Tube
     *
     * @return the axis ray
     */
    public Ray getDirection() {
        return this.axis;
    }

    /**
     * Getter for the radius of the Tube
     *
     * @return the radius
     */
    public double getRadius() {
        return this.radius;
    }


    /**
     * Constructor for a Tube for a ray axis and a radius
     *
     * @param ray    the ray axis
     * @param radius the radius
     */
    public Tube(Ray ray, double radius) {
        this.axis = ray;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector dir = this.axis.getDirection();
        Point p0 = this.axis.getStart();
        double t = dir.dotProduct(point.subtract(p0));
        Point o = isZero(t) ? p0 : p0.add(dir.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point rayStart = ray.getStart();
        Vector rayDir = ray.getDirection();
        Point tubeStart = this.axis.getStart();
        Vector tubeDir = this.axis.getDirection();
        Vector K;
        try {
            K = rayDir.crossProduct(tubeDir);
        } catch (IllegalArgumentException e) {
            return null;
        }
        Vector E;
        try {
            E = rayStart.subtract(tubeStart).crossProduct(tubeDir);
        } catch (IllegalArgumentException e) {
            List<GeoPoint> intersections = new LinkedList<>();
            intersections.add(new GeoPoint(this, tubeStart.add(rayDir.scale(getRadius() / K.length()))));
            return intersections;
        }

        double a = K.lengthSquared();
        double b = 2 * K.dotProduct(E);
        double c = E.lengthSquared() - this.radius * this.radius;
        double delta = alignZero(b * b - 4 * a * c);
        if (delta < 0)
            return null;
        if (delta == 0){
            double t = alignZero((-b)/(2*a));
            List<GeoPoint> intersections = new LinkedList<>();
            intersections.add(new GeoPoint(this, t == 0 ? rayStart : rayStart.add(rayDir.scale(t))));
            return intersections;

        }
        double sDelta = Math.sqrt(delta);
        double t1 = alignZero((-b + sDelta) / (2 * a));
        double t2 = alignZero((-b - sDelta) / (2 * a));
        if (t1 <= 0) return null;
        List<GeoPoint> intersections = new LinkedList<>();
        if (alignZero(t1 - maxDistance) <= 0)
            intersections.add(new GeoPoint(this, t1 == 0 ? rayStart : rayStart.add(rayDir.scale(t1))));
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) intersections.add(new GeoPoint(this, rayStart.add(rayDir.scale(t2))));
        return intersections;

    }
}
