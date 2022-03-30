package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for all the Intersectable objects such as Three-Dimensional or Two-Dimensional Geometry
 * or any Composite Geometry
 */
public interface Intersectable {

    /**
     * Finds intersections between the ray (shot from camera through view plane)
     * and the geometry
     * @param ray The ray shot through VP
     * @return List of intersection points
     */
    List<Point> findIntersections(Ray ray);
}
