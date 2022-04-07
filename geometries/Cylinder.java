package geometries;

import primitives.*;

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
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector dir = this.getDirection().getDirection();
        Point p0 = this.getDirection().getStart();
        if (point.equals(p0)) {
            return dir.scale(-1);
        }
        double t = dir.dotProduct(point.subtract(p0));
        if (t == 0) {
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
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
