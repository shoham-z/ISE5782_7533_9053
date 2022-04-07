package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometries implements Intersectable{
    List<Intersectable> geometries =new ArrayList<>();

    /**
     * Constructor for non-empty list of Intersectable objects
     * @param geometries List of geometries
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = List.of(geometries);
    }


    /**
     * Adds the geometries to the geometries list
     * @param geometries geometries to add
     */
    public void add(Intersectable... geometries){
        if(geometries.length>0)
        {
            this.geometries.addAll(Arrays.asList(geometries));
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable geometry:
             this.geometries) {
            List<Point> points = geometry.findIntersections(ray);

            if (points != null) {
                if (intersections == null)
                    intersections = new ArrayList<Point>(points);
                else
                    intersections.addAll(points);
            }
        }
        return intersections;
    }
}
