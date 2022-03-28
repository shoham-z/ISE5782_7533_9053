package geometries;

import primitives.*;

/**
 * Plane class represents A two-dimensional and infinite Plane in 3D Cartesian coordinate
 * system
 *
 */
public class Plane implements Geometry{

    private final Point point;
    private final Vector normal;

    /**
     * Constructor to build a Plane object from a point and an orthogonal vector
     * @param point on the Plane
     * @param vector normal to the plane
     */
    public Plane(Point point, Vector vector) {
        this.point = point;
        this.normal = vector.normalize();
    }

    /**
     * Constructor to build a Plane from 3 Points
     * @param p1 Point on the Plane
     * @param p2 Point on the Plane
     * @param p3 Point on the Plane
     * @throws IllegalArgumentException when the points are on the same line
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.point = p1;
        Vector v1 = p1.subtract(p2).normalize();
        Vector v2 = p1.subtract(p3).normalize();
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     *
     * @return Point on the Plane
     */
    public Point getPoint() {
        return point;
    } // add records

    /**
     *
     * @return the normal Vector to the Plane
     */
    public Vector getNormal() {
        return normal;
    }


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
}
