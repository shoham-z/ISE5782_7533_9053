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


    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersection = this.plane.findIntersections(ray);
        if (intersection == null)
            return null;
        Vector v1 = vertices.get(0).subtract(ray.getStart());
        Vector v2 = vertices.get(1).subtract(ray.getStart());
        Vector v3 = vertices.get(2).subtract(ray.getStart());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        Vector v = ray.getDirection();
        double vn1 = alignZero(v.dotProduct(n1));
        double vn2 = alignZero(v.dotProduct(n2));
        double vn3 = alignZero(v.dotProduct(n3));
        if (isZero(vn1) || isZero(vn2) || isZero(vn3))
            return null;
        if ((vn1 > 0 && vn2 > 0 && vn3 > 0) || ((vn1 < 0 && vn2 < 0 && vn3 < 0)))
            return intersection;
        return null;
    }

    @Override
    public String toString() {
        return "triangle{" + ", plane=" + plane + "vertices=" + vertices + '}';
    }
}
