package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * Triangle class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 */
public class Triangle extends Polygon {
    /**
     * Constructor that generates a triangle from 3 given points
     *
     * @param vertices 3 Point
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }


    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<Point> intersection = this.plane.findIntersections(ray);
        if (intersection == null)
            return null;

        Point p0 = ray.getStart();
        Vector v = ray.getDirection();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double vn1 = alignZero(v.dotProduct(n1));
        if (vn1 == 0) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double vn2 = alignZero(v.dotProduct(n2));
        if (vn1 * vn2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double vn3 = alignZero(v.dotProduct(n3));
        if (vn1 * vn3 <= 0) return null;

        return List.of(new GeoPoint(this, intersection.get(0)));
    }

    @Override
    public String toString() {
        return "triangle{" + ", plane=" + plane + "vertices=" + vertices + '}';
    }
}
