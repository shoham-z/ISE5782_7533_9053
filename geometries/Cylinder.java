package geometries;

import primitives.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Cylinder class represents a three-dimensional Cylinder in 3D Cartesian coordinate
 * system
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructor for Cylinder using an axis ray, radius and height
     *
     * @param ray    the axis ray
     * @param radius the radius
     * @param height the height
     */
    public Cylinder(Ray ray, double radius, double height) {
        super(ray, radius);
        this.height = height;
    }

    /**
     * Getter for the height of the Cylinder
     *
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector dir = this.getDirection().getDirection();
        Point p0 = this.getDirection().getStart();
        if (point.equals(p0)) {
            return dir.scale(-1);
        }
        double t = alignZero(dir.dotProduct(point.subtract(p0)));
        if (isZero(t)) {
            return dir.scale(-1);
        }
        if (t == this.height) {
            return dir;
        }
        Point o = p0.add(dir.scale(t));
        Vector normal = point.subtract(o);
        return normal.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        List<Point> points = new ArrayList<Point>();

        Point upPoint = this.getDirection().getStart().add(this.getDirection().getDirection().scale(height));
        List<GeoPoint> pointsT = super.findGeoIntersectionsHelper(ray, maxDistance);

        if (pointsT != null)
            for (GeoPoint geopoint : pointsT) {
                double A = geopoint.point.distanceSquared(this.getDirection().getStart());
                double R = this.getRadius() * this.getRadius();
                Vector v = this.getDirection().getStart().subtract(geopoint.point);
                if (Math.sqrt(A - R) <= height && v.dotProduct(this.getDirection().getDirection()) <= 0)
                    points.add(geopoint.point);
            }

        Plane p1 = new Plane(this.getDirection().getStart(), this.getDirection().getDirection().scale(-1));
        Plane p2 = new Plane(upPoint, this.getDirection().getDirection());

        List<Point> point1 = p1.findIntersections(ray);
        List<Point> point2 = p2.findIntersections(ray);

        if (point1 != null && alignZero(point1.get(0).distance(this.getDirection().getStart()) - this.getRadius()) <= 0)
            points.add(point1.get(0));
        if (point2 != null && alignZero(point2.get(0).distance(upPoint) - this.getRadius()) <= 0)
            points.add(point2.get(0));

        if (points.isEmpty())
            return null;
        List<GeoPoint> intersections = new LinkedList<>();
        for (Point point: points) {
            intersections.add(new GeoPoint(this,point));
        }
        return intersections;
    }

    private GeoPoint findBaseIntersection (Point center, Ray ray) {

        Plane plane = new Plane(center, this.getDirection().getDirection());
        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray);
        if (planeIntersections == null) return null;
        GeoPoint intersection = planeIntersections.get(0);
        intersection.geometry = this;
        double dist = intersection.point.distance(center);
        if (dist >= getRadius()) return null;
        return intersection;
    }

}
