package geometries;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Tube class represents a three-dimensional tube in 3D Cartesian coordinate
 * system
 */
public class Tube implements Geometry{
    private final Ray axis;
    private final double radius;

    /**
     * Getter for the axis ray of the Tube
     * @return the axis ray
     */
    public Ray getDirection() {
        return axis;
    }

    /**
     *
     * @return radius - of the Tube
     */
    public double getRadius() {
        return radius;
    }

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
}
