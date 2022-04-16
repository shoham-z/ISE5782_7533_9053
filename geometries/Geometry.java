package geometries;

import primitives.*;

/**
 * Interface for all the geometries needed
 */
public abstract class Geometry extends Intersectable {
    protected Color emission=Color.BLACK;

    /**
     * Getter for Geometry;
     * @return the emission
     */

    public Color getEmission() {
        return this.emission;
    }
    /**
     * Setter for Geometry;
     * @param emission
     */
    public Geometry setEmission(Color emission) {
        this.emission=emission;
        return this;
    }

    /**
     * finds the normal of the geometry at the given point
     * on the surface of the geometry
     * @param point on the geometry
     * @return the normal of the geometry
     */
    abstract public Vector getNormal(Point point);
}
