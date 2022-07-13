package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represents A two-dimensional and infinite Plane in 3D Cartesian coordinate
 * system
 */
public class Plane extends Geometry {

    private final Point point;
    private final Vector normal;

    /**
     * Constructor to build a Plane object from a point and an orthogonal vector
     *
     * @param point  on the Plane
     * @param vector normal to the plane
     */
    public Plane(Point point, Vector vector) {
        this.point = point;
        this.normal = vector.normalize();
    }

    /**
     * Constructor to build a Plane from 3 Points
     *
     * @param p1 Point on the Plane
     * @param p2 Point on the Plane
     * @param p3 Point on the Plane
     * @throws IllegalArgumentException when the points are on the same line
     */
    public Plane(Point p1, Point p2, Point p3) {//ax+by+cz +d = 0
        this.point = p1;
        this.normal = p1.subtract(p2).crossProduct(p1.subtract(p3)).normalize();
    }

    /**
     * @return Point on the Plane
     */
    public Point getPoint() {
        return point;
    } // add records

    /**
     * @return the normal Vector to the Plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return this.normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "point=" + point +
                ", normal=" + normal +
                '}';
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        double denominator = this.normal.dotProduct(ray.getDirection());
        if (isZero(denominator))
            return null;

        Vector u;
        try {
            u = this.point.subtract(ray.getStart());
        } catch (IllegalArgumentException ignore) {
            return null;
        }

        double t = alignZero(this.normal.dotProduct(u) / denominator);
        return (alignZero(t - maxDistance) <= 0 && t > 0) ? List.of(new GeoPoint(this, ray.getPoint(t))) : null;
    }

}
