package primitives;

public class Ray {
    private final Vector direction;
    private final Point start;

    /**
     * Constructor to create a Ray from a Point to direction of dir
     * @param start Point: start of the Ray
     * @param dir Vector: direction of Ray
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
     *
     * @return the direction of the ray
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     *
     * @return the start Point of the ray
     */
    public Point getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "direction=" + direction +
                ", start=" + start +
                '}';
    }
}
