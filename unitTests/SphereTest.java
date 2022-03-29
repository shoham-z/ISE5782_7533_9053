package unitTests;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Shoham
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // EP01: There is a simple single test here - the point is in the sphere
        Sphere sphere = new Sphere(new Point(0,0,0), 5);
        assertEquals(sphere.getNormal(new Point(5,0,0)), new Vector(1,0,0),
                "ERROR: normal for sphere is not working");
    }


    /**
     * Test method for {@link geometries.Sphere#findIntsersections(primitives.Ray)}
     */
    @Test
    void findIntsersections() {
    }
}