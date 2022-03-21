package unitTests;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * @author Shoham
 */
class RayTest {
    /**
     * Test method for {@link primitives.Ray#Ray(primitives.Point, primitives.Vector)}
     */
    @org.junit.jupiter.api.Test
    public void constructorTest() {
        // ============ Equivalence Partitions Tests ==============

        Point p = new Point( 1,1,1);
        // TC1: Makes sure we can't create a ray with zero vector from 3 doubles constructor
        assertThrows(IllegalArgumentException.class, () -> new Ray(p, new Vector(0, 0, 0)),
                "ERROR: zero vector does not throw an exception");

        // TC2: Makes sure we can't create a ray with zero vector from double3 constructor
        assertThrows(IllegalArgumentException.class, () -> new Ray(p, new Vector(new Double3(0, 0, 0))),
                "ERROR: zero vector does not throw an exception");
    }

}