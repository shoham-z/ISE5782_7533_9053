package geometries;

import primitives.*;

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
    public String toString() {
        return "triangle{" + ", plane=" + plane + "vertices=" + vertices + '}';
    }
}
