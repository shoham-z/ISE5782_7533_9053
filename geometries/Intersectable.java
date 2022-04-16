package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for all the Intersectable objects such as Three-Dimensional or Two-Dimensional Geometry
 * or any Composite Geometry
 */
public abstract class Intersectable {

    /**
     * Finds intersections between the ray
     * and the geometry
     * @param ray The ray to intersect with the geometry
     * @return List of intersection points
     */
    public abstract List<Point> findIntersections(Ray ray);

    /**
     * The class represents point on the geometry
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;
    }

}
