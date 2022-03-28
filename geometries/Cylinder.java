package geometries;

import primitives.*;

/**
 * Cylinder class represents a three-dimensional Cylinder in 3D Cartesian coordinate
 * system
 */
public class Cylinder implements Geometry{
    private final Ray dir;
    private final double radius;
    private final double height;

    /**
     * Constructor for Cylinder using an axis ray, radius and height
     * @param direction the axis ray
     * @param radius the radius
     * @param height the height
     */
    public Cylinder(Ray direction, double radius, double height) {
        this.dir = direction;
        this.radius = radius;
        this.height = height;
    }
    /**
     * Getter for the axis ray of the Cylinder
     * @return the axis ray
     */
    public Ray getDirection() {
        return dir;
    }

    /**
     * Getter for the radius of the Cylinder
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter for the height of the Cylinder
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector dir = this.dir.getDirection();
        Point P0 = this.dir.getStart();
        if(point.equals(P0)){
            return this.dir.getDirection().scale(-1);
        }
        double t = dir.dotProduct(point.subtract(P0));
        if(t == 0){
            return this.dir.getDirection().scale(-1);
        }
        if(t == this.height){
            return this.dir.getDirection();
        }
        Point O = P0.add(dir.scale(t));
        Vector normal = point.subtract(O);
        return normal.normalize();
    }
}
