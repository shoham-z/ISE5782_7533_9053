package geometries;

import primitives.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * geometries class represents the geometries form
 */
public class Geometries extends Intersectable {
    List<Intersectable> geometries = new LinkedList<>();

    /**
     * Constructor for non-empty list of Intersectable objects
     *
     * @param geometries List of geometries
     */
    public Geometries(Intersectable... geometries) {
        this.add(geometries);
    }

    /**
     * Adds the geometries to the geometries list
     *
     * @param geometries geometries to add
     */
    public void add(Intersectable... geometries) {
        if (geometries.length > 0) this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : this.geometries) {
            List<GeoPoint> geopoints = geometry.findGeoIntersections(ray, maxDistance);
            if (geopoints != null) {
                if (intersections == null)
                    intersections = new LinkedList<>(geopoints);
                else
                    intersections.addAll(geopoints);
            }
        }
        return intersections;
    }
}
