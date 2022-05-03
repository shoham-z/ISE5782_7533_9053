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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if(ray.equals(this.axis)) return null;
        Vector v = this.axis.getDirection();
        Vector u = ray.getDirection();
        double uv = u.dotProduct(v);
        if(uv == 0) return null;
        Vector temp = v.scale(uv);
        if (u.equals(temp)) return null;
        Vector deltaP = ray.getStart().subtract(this.axis.getStart());

        double a = u.subtract(temp).lengthSquared();
        double b = 2 * u.subtract(temp).dotProduct(deltaP.subtract(v.scale(deltaP.dotProduct(v))));
        double c = deltaP.subtract(v.scale(deltaP.dotProduct(v))).lengthSquared() - this.radius * radius;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) return null;
        if (discriminant==0) return List.of (new GeoPoint(this,ray.getPoint(-b/2*a)));
        return List.of (new GeoPoint(this,ray.getPoint((-b + discriminant)/2*a)),new GeoPoint(this,ray.getPoint((-b-discriminant)/2*a)));
    }
}
