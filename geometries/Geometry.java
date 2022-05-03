package geometries;

import primitives.*;

/**
 * Interface for all the geometries needed
 */
public abstract class Geometry extends Intersectable {
    private Material material = new Material();
    protected Color emission = Color.BLACK;

    /**
     * Getter for Geometry;
     *
     * @return the emission
     */

    public Color getEmission() {
        return this.emission;
    }

    /**
     * Setter for Geometry emission
     *
     * @param emission the emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Getter for the material of the geometry
     *
     * @return the material
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Setter for material
     *
     * @param material the material
     * @return the Material
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * finds the normal of the geometry at the given point
     * on the surface of the geometry
     *
     * @param point on the geometry
     * @return the normal of the geometry
     */
    abstract public Vector getNormal(Point point);
}
