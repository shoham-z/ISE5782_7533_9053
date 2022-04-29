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
        public Geometry geometry;
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
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            if (this.geometry == null || geoPoint.geometry == null) return this.point.equals(geoPoint.point);
            return this.geometry.equals(geoPoint.geometry) && this.point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + this.geometry +
                    ", point=" + this.point +
                    '}';
        }

    }

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }

}
