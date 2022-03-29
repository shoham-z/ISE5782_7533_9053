package geometries;

import primitives.*;

import java.util.List;

/**
 * Triangle class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 */
public class Triangle extends Polygon {
    /**
     * Constructor that generates a triangle from 3 given points
     *
     * @param vertices 3 Point
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }


    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }

    @Override
    public String toString() {
        return "triangle{" + ", plane=" + plane + "vertices=" + vertices + '}';
    }
}
