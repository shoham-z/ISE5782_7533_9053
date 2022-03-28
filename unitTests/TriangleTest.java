package unitTests;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Shoham
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // EP01: There is a simple single test here
        Point p = new Point(1,1,1);
        Triangle triangle = new Triangle(p, new Point(1,2,1), new Point(1,1,2));
        assertTrue(triangle.getNormal(p).equals(new Vector(1,0,0)) || triangle.getNormal(p).equals(new Vector(-1,0,0)),
                "ERROR: getnormal method in Triangle does not work properly");
    }
}