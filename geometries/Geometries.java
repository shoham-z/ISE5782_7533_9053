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
        this.geometries = List.of(geometries);
    }

    /**
     * Constructor for empty list of Intersectable objects
     */
    public Geometries() {
        this.geometries = new LinkedList<>();
    }

    /**
     * Adds the geometries to the geometries list
     *
     * @param geometries geometries to add
     */
    public void add(Intersectable... geometries) {
        if (geometries.length > 0) {
            this.geometries.addAll(Arrays.asList(geometries));
        }
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry :
                this.geometries) {
            List<GeoPoint> geopoints = geometry.findGeoIntersections(ray);

            if (geopoints != null) {
                if (intersections == null)
                    intersections = new LinkedList<GeoPoint>(geopoints);
                else
                    intersections.addAll(geopoints);
            }
        }
        return intersections;
    }
}
