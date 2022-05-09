package geometries;

import primitives.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.LinkedList;
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
        return this.height;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector dir = this.getDirection().getDirection();
        Point p0 = this.getDirection().getStart();
        if (point.equals(p0)) {
            return dir.scale(-1);
        }
        double t = alignZero(dir.dotProduct(point.subtract(p0)));
        if (isZero(t)) {
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        /*Point tubeStart = this.getDirection().getStart();
        Vector tubeDir = this.getDirection().getDirection();
        List<GeoPoint> cylinderCaseIntersections = super.findGeoIntersectionsHelper(ray,maxDistance);
        if (cylinderCaseIntersections != null) {
            cylinderCaseIntersections.removeIf(p -> {
                double height = tubeDir.dotProduct(p.point.subtract(tubeStart));
                return !(height > 0 && height < this.height);
            });
        }


        GeoPoint base1 = findBaseIntersection(tubeStart, ray);
        GeoPoint base2 = findBaseIntersection(tubeStart.add(tubeDir.scale(this.height)), ray);
        if (cylinderCaseIntersections == null || cylinderCaseIntersections.isEmpty())
            if(base1 == null && base2 == null)
                return null;
        // There is at least one point
        List<GeoPoint> intersections = new LinkedList<>();
        if (cylinderCaseIntersections != null)
            for(GeoPoint geoP : cylinderCaseIntersections)
                intersections.add(new GeoPoint(this, geoP.point));
        if (base1 != null) intersections.add(base1);
        if (base2 != null) intersections.add(base2);
        return intersections;*/
        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray, maxDistance);

        Point p0 = this.getDirection().getStart();
        Vector dir = this.getDirection().getDirection();

        if (intersections != null)
        {
            List<GeoPoint> temp = new ArrayList<>();

            for (GeoPoint g : intersections)
            {
                double pointHeight = alignZero(g.point.subtract(p0).dotProduct(dir));
                if (pointHeight > 0 && pointHeight < height)
                {
                    temp.add(g);
                }
            }

            if (temp.isEmpty())
            {
                intersections = null;
            }
            else
            {
                intersections = temp;
            }
        }

        List<GeoPoint> planeIntersection = new Plane(p0, dir).findGeoIntersections(ray, maxDistance);
        if (planeIntersection != null)
        {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p0) || alignZero(point.point.subtract(p0).lengthSquared() - this.getRadius() * this.getRadius()) < 0)
            {
                if (intersections == null)
                {
                    intersections = new ArrayList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        Point p1 = p0.add(dir.scale(height));

        planeIntersection = new Plane(p1, dir).findGeoIntersections(ray, maxDistance);
        if (planeIntersection != null)
        {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p1) || alignZero(point.point.subtract(p1).lengthSquared() - this.getRadius() * this.getRadius()) < 0)
            {
                if (intersections == null)
                {
                    intersections = new ArrayList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        if (intersections != null)
        {
            for (GeoPoint g : intersections)
            {
                g.geometry = this;
            }
        }

        return intersections;
    }

    private GeoPoint findBaseIntersection (Point center, Ray ray) {

        Plane plane = new Plane(center, this.getDirection().getDirection());
        List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray);
        if (planeIntersections == null) return null;
        GeoPoint intersection = planeIntersections.get(0);
        intersection.geometry = this;
        double dist = intersection.point.distance(center);
        if (dist >= getRadius()) return null;
        return intersection;
    }

}
