package geometries;

import primitives.*;

import java.util.List;

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

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // if tube's ray and the ray we got have dot product of +-1 => return null
        // find reduction from 3d to 2d as shown in whatsapp
        return null;
    }

}
