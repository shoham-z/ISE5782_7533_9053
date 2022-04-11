package primitives;

import java.util.List;

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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray))
            return false;
        Ray other = (Ray) obj;
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
     * Calculates the starting point plus t steps in the direction of the ray
     *
     * @param t distance from starting point
     * @return start + direction * t
     */
    public Point getPoint(double t) {
        if (t < 0) {
            throw new IllegalArgumentException();
        }
        if (t > 0) {
            return this.start.add(this.direction.scale(t));
        }
        return this.start;
    }

    public Point findClosestPoint(List<Point> points) {
        try {
            int index = -1;
            double distance = 999999999999d;
            for (Point point :
                    points) {
                if(point.subtract(this.start).dotProduct(this.direction) > 0){
                    double d = this.start.distance(point);
                    if (d < distance) {
                        distance = d;
                        index = points.indexOf(point);
                    }
                }
            }
            return points.get(index);
        } catch (Exception ignore) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Ray{" +
                "direction=" + direction +
                ", start=" + start +
                '}';
    }
}
