package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (ray.equals(this.axis)) return null;
        Vector tubeAxis = this.axis.getDirection();
        Vector rayAxis = ray.getDirection();
        double uv = alignZero(rayAxis.dotProduct(tubeAxis));
        double a;
        double b;
        Vector deltaP = ray.getStart().subtract(this.axis.getStart());
        double deltaPv = alignZero(deltaP.dotProduct(tubeAxis));
        if (deltaPv == 0) return null;
        Vector w = tubeAxis.scale(deltaPv);
        if (w.equals(deltaP)) return null;
        Vector later = deltaP.subtract(w);
        if (uv == 0) {
            a = alignZero(rayAxis.lengthSquared());
            b = alignZero(2 * rayAxis.subtract(tubeAxis).dotProduct(later));
        } else {
            Vector temp = tubeAxis.scale(uv);
            if (rayAxis.equals(temp)) return null;

            a = alignZero(rayAxis.subtract(temp).lengthSquared());
            b = alignZero(2 * rayAxis.subtract(temp).dotProduct(later));
        }

        double c = alignZero(later.lengthSquared() - this.radius * this.radius);
        double discriminant2 = alignZero(b * b - 4 * a * c);

        if (discriminant2 < 0) return null;
        if (discriminant2 == 0) return List.of(new GeoPoint(this, ray.getPoint(-b / (2 * a))));
        double discriminant = alignZero(Math.sqrt(discriminant2));
        double t1 = alignZero((-b + discriminant) / (2 * a));
        if (t1 < 0) return null;
        double t2 = alignZero((-b - discriminant) / (2 * a));
        if (t2 < 0) return List.of(new GeoPoint(this, ray.getPoint(t1)));
        return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }

    private List<Double> quadratic(double a, double b, double c) {
        double root1, root2;

        // calculate the determinant (b2 - 4ac)
        double determinant = b * b - 4 * a * c;


        // if determinant is less than zero
        if (determinant < 0) {
            return null;
        }

        // check if determinant is equal to 0
        else if (determinant == 0) {

            // two real and equal roots
            // determinant is equal to 0
            // so -b + 0 == -b
            root1 = -b / (2 * a);
            return List.of(root1);
        }

        // check if determinant is greater than 0

        // two real and distinct roots
        root1 = (-b + Math.sqrt(determinant)) / (2 * a);
        root2 = (-b - Math.sqrt(determinant)) / (2 * a);
        return List.of(root1, root2);
    }
}
