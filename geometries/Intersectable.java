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
     *
     * @param ray The ray to intersect with the geometry
     * @return List of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * The class represents point on the geometry
     */
    public static class GeoPoint {
        /**
         * The geometry that the point is on
         */
        public Geometry geometry;
        /**
         * A point on the geometry
         */
        public Point point;

        /**
         * constructor for GeoPoint
         *
         * @param geometry a geometry
         * @param point    a point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return this.geometry == geoPoint.geometry && this.point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + this.geometry +
                    ", point=" + this.point +
                    '}';
        }

    }

    /**
     * Finds intersection points of ray with the geometry
     * @param ray the ray to intersect with
     * @return list of the geometry with the intersection point(s)
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
    }

    /**
     * Finds intersection points of ray with the geometry
     * @param ray the ray to intersect with
     * @param maxDistance the maximum distance of point(s) from light
     * @return list of the geometry with the intersection point(s)
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * A helper function to {@link geometries.Intersectable#findGeoIntersections(Ray)}
     * @param ray the ray to intersect with
     * @return list of the geometry with the intersection point(s)
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
