

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Shoham
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}
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

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}
     */
    @Test
    void findIntsersections() {
        Triangle triangle = new Triangle(new Point(1,1,0),
                                         new Point(1,0,0),
                                         new Point(0,1,0));

        // ============ Equivalence Partitions Tests ==============
        // EP01: Ray intersects with Triangle (1 point)
        List <Point> result = triangle.findIntersections(new Ray(new Point(0.75, 0.75, 0.75),
                new Vector(0, 0, -1)));
        assertEquals(1, result.size(), "EP01: Wrong number of points");
        assertEquals(List.of(new Point(0.75,0.75,0)), result, "EP01: Ray crosses triangle");
        result = null;

        // EP02: Ray intersects with the plane against the edge of triangle (0 points)
        result = triangle.findIntersections(new Ray(new Point(1.5, 1.5, 0.75),
                new Vector(0, 0, -1)));
        assertNull(result, "EP02: Wrong number of points");

        // EP03: Ray intersects with the plane against the vertex of triangle (0 points)
        result = triangle.findIntersections(new Ray(new Point(1.25, -1, 0.75),
                new Vector(0, 0, -1)));
        assertNull(result, "EP03: Wrong number of points");

        // =============== Boundary Values Tests ==================
        // BV01: Ray intersects with edge (0 points)
        result = triangle.findIntersections(new Ray(new Point(1, 0.75, 0.75),
                new Vector(0, 0, -1)));
        assertNull(result, "BV01: Wrong number of points");

        // BV02: Ray intersects with vertex (0 points)
        result = triangle.findIntersections(new Ray(new Point(1, 1, 0.75),
                new Vector(0, 0, -1)));
        assertNull(result, "BV02: Wrong number of points");

        // BV03: Ray intersects with edge's continuation (0 points)
        result = triangle.findIntersections(new Ray(new Point(1, 1.5, 0.75),
                new Vector(0, 0, -1)));
        assertNull(result, "BV03: Wrong number of points");
    }
}