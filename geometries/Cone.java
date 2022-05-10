package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class represents a Three-dimensional Cone
 */
public class Cone extends Geometry {
    Ray axis;
    double angle;
    double height;

    /**
     * Constructor for Cone
     *
     * @param dir   The 'up' direction of the cone
     * @param angle Angle of the cone
     * @param h     Height of the cone
     */
    public Cone(Ray dir, double angle, double h) {
        if (angle >= Math.PI / 2 || angle <= 0) throw new IllegalArgumentException();
        this.axis = dir;
        this.angle = angle;
        this.height = h;
    }

    /**
     * Getter for axis of cone
     *
     * @return Axis of cone
     */
    public Ray getAxis() {
        return this.axis;
    }

    /**
     * Getter for radius of cone
     *
     * @return Radius of cone
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Getter for height of cone
     *
     * @return Height of cone
     */
    public double getHeight() {
        return this.height;
    }

    @Override
    public Vector getNormal(Point point) {
        // get the vector to the point from the start of ray
        // if dot product is 0 return the negative of the axis direction
        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector to = this.axis.getDirection();
        double dv = to.dotProduct(ray.getDirection());
        double cos = Math.cos(this.angle);
        double cos2 = cos * cos;
        Vector co = ray.getStart().subtract(this.axis.getStart());
        double cov = co.dotProduct(ray.getDirection());
        double a = dv*dv-cos2;
        double b = dv*cov- to.dotProduct(co) * cos2;
        double c = cov*cov - co.lengthSquared()*cos2;
        double discriminant2 = b*b-4*a*c;
        if (discriminant2<0) return null;
        if (discriminant2 ==0){
            double t = -b/(2*a);
            List<GeoPoint> intersection=new LinkedList<>();
            intersection.add(new GeoPoint(this,ray.getPoint(t)));
            return intersection;
        }
        double discriminant = Math.sqrt(discriminant2);
        double t1 = alignZero((-b +discriminant)/(2*a));
        double t2 = alignZero((-b -discriminant)/(2*a));

        if (t1 <= 0) return null;
        List<GeoPoint> intersections = new LinkedList<>();
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) intersections.add(new GeoPoint(this, ray.getStart().add(ray.getDirection().scale(t2))));
        if (alignZero(t1 - maxDistance) <= 0)
            intersections.add(new GeoPoint(this, t1 == 0 ? ray.getStart() : ray.getStart().add(ray.getDirection().scale(t1))));
        return intersections;
    }
}
