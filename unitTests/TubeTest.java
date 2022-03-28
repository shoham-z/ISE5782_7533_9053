package unitTests;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Shoham
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        Tube t = new Tube(new Ray(new Point(0,0,0), new Vector(1,0,0)),3);
        // ============ Equivalence Partitions Tests ==============
        // EP01: Point is on the Tube
        assertEquals(t.getNormal(new Point(1,0,3)), new Vector(0,0,1),
                "normal for tube is not working properly at random point");

        // ============ Boundary Value Tests ====================
        // BV01: Point is parallel to the starting point of the ray
        assertEquals(t.getNormal(new Point(0,0,3)), new Vector(0,0,1),
                "normal for tube is not working properly when point is parallel to the starting point");
    }
}