package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;

public class Ray {
    private final Vector direction;
    private final Point start;

    /**
     * Constructor to create a Ray from a Point to direction of dir
     *
     * @param start Point: start of the Ray
     * @param dir   Vector: direction of Ray
     */
    public Ray(Point start, Vector dir) {
        this.start = start;
        this.direction = dir.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ray other)) return false;
        return this.direction.equals(other.direction) && this.start.equals(other.start);
    }

    /**
     * Getter for the ray's direction Vector
     *
     * @return the direction vector
     */
    public Vector getDirection() {
        return this.direction;
    }

    /**
     * Getter for the ray's starting Point
     *
     * @return the start Point
     */
    public Point getStart() {
        return this.start;
    }

    /**
     * Calculates a point on the line of the ray at a given distance from the starting point
     *
     * @param t the distance from the starting point
     * @return the point on the line of the ray
     */
    public Point getPoint(double t) {
        return isZero(t) ? this.start : this.start.add(this.direction.scale(t));
    }

    /**
     * finds the closest point to the start of the ray
     *
     * @param points the points
     * @return the closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest point to the start of the ray
     * @param geoPoints list of geometries and points on them, correspondingly
     * @return the closest geometry and a point on the geometry
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        GeoPoint point = null;
        double distance = Double.POSITIVE_INFINITY;
        for (GeoPoint geoPoint : geoPoints) {
            double d = this.start.distance(geoPoint.point);
            if (d < distance) {
                distance = d;
                point = geoPoint;
            }
        }
        return point;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "direction=" + direction +
                ", start=" + start +
                '}';
    }
}
