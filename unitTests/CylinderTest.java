

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 *
 * @author Shoham
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        Cylinder t = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 3, 3);
        // ============ Equivalence Partitions Tests ==============
        // EP01: Point is on the cylinder
        assertEquals(t.getNormal(new Point(1, 0, 3)), new Vector(0, 0, 1),
                "normal for Cylinder is not working properly at random point");
        // EP02: Point is on the bottom base
        assertEquals(t.getNormal(new Point(0, 1, 0)), new Vector(-1, 0, 0),
                "normal for Cylinder when point is on bottom base is not working properly");
        // EP03: Point is on the top base
        assertEquals(t.getNormal(new Point(3, 1, 0)), new Vector(1, 0, 0),
                "normal for Cylinder when point is on top base is not working properly");

        // ============ Boundary Value Tests ====================
        // BV01: Point is at the center of bottom base
        assertEquals(t.getNormal(new Point(0, 0, 0)), new Vector(-1, 0, 0),
                "normal for Cylinder is not working properly when point is at the center of bottom base");
        // BV02: Point is at the center of top base
        assertEquals(t.getNormal(new Point(3, 0, 0)), new Vector(1, 0, 0),
                "normal for Cylinder is not working properly when point is at the center of top base");
    }


    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}
     */
    @Test
    void findIntersections() {
    }
}