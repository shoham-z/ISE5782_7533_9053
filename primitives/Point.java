package primitives;

public class Point {
    final protected Double3 xyz;

    /**
     * Constructor to initialize Point based object with its three number values
     *
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to initialize Point based object with its Double3 object
     *
     * @param xyz the Double3 object
     */
    protected Point(Double3 xyz) {
        this.xyz = new Double3(xyz.d1, xyz.d2, xyz.d3);//maybe assign this.xyz = xyz
    }

    public double getX() {
        return xyz.d1;
    }

    /**
     * Subtract two Point object into a new Point where each couple of
     * numbers is subtracted
     *
     * @param other right handle side operand for addition
     * @return result of subtract
     */
    public Vector subtract(Point other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * Subtract two Point object into a new Point where each couple of
     * numbers is added
     *
     * @param other right handle side operand for addition
     * @return result of add
     */
    public Point add(Vector other) {
        return new Point(this.xyz.add(other.xyz));
    }

    /**
     * find the distance squared of two Point objects
     *
     * @param other the other Point to find distance squared from
     * @return distance between Points squared
     */
    public double distanceSquared(Point other) {
        double dX = xyz.d1 - other.xyz.d1;
        double dY = xyz.d2 - other.xyz.d2;
        double dZ = xyz.d3 - other.xyz.d3;
        return dX * dX + dY * dY + dZ * dZ;
    }

    /**
     * find the distance of two Point objects
     *
     * @param other the other Point to find distance from
     * @return distance between Points
     */
    public double distance(Point other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "Point " + xyz;
    }
}
