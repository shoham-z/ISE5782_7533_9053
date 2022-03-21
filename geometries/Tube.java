package geometries;

import primitives.*;

/**
 * Tube class represents a three-dimensional tube in 3D Cartesian coordinate
 * system
 */
public class Tube implements Geometry{
    private final Ray dir;
    private final double radius;

    /**
     *
     * @return Ray - dir of the Tube
     */
    public Ray getDirection() {
        return dir;
    }

    /**
     *
     * @return radius - of the Tube
     */
    public double getRadius() {
        return radius;
    }

    public Tube(Ray dir, double radius) {
        this.dir = dir;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector dir = this.dir.getDirection();
        Point P0 = this.dir.getStart();
        double t = dir.dotProduct(point.subtract(P0));
        if(t == 0){// to
            Vector normal = point.subtract(P0);
            return normal.normalize();
        }
        Point O = P0.add(dir.scale(t));
        Vector normal = point.subtract(O);
        return normal.normalize();
    }
}
