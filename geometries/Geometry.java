package geometries;

import primitives.*;

/**
 * Interface for all the geometries needed
 */
public interface Geometry extends Intersectable {

    /**
     * finds the normal of the geometry at the given point
     * on the surface of the geometry
     * @param point on the geometry
     * @return the normal of the geometry
     */
    Vector getNormal(Point point);
}
