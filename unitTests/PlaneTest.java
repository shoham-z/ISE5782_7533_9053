package unitTests;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Shoham
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point , primitives.Point)}
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,1,1);

        // TC1: 2 points of the 3 are unifying
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1, p1.add(new Vector(1,1,1))),
                "ERROR: does not throw an exception when 2 points are the same");

        // TC2: the points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1.add(new Vector(2,2,2)), p1.add(new Vector(1,1,1))),
                "ERROR: does not throw an exception when the points are on the same line");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point p = new Point(1,1,1);
        Plane plane = new Plane(p, new Point(1,2,1), new Point(1,1,2));
        assertTrue(plane.getNormal(p).equals(new Vector(1,0,0)) || plane.getNormal(p).equals(new Vector(-1,0,0)),
                "ERROR: getnormal method in Plane does not work properly");
    }
}