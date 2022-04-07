package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for all the Intersectable objects such as Three-Dimensional or Two-Dimensional Geometry
 * or any Composite Geometry
 */
public interface Intersectable {

    /**
     * Finds intersections between the ray
     * and the geometry
     * @param ray The ray to intersect with the geometry
     * @return List of intersection points
     */
    List<Point> findIntersections(Ray ray);
}
