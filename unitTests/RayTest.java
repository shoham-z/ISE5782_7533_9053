
import org.junit.jupiter.api.Test;
import primitives.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * @author Shoham
 */
class RayTest {
    /**
     * Test method for {@link primitives.Ray#Ray(primitives.Point, primitives.Vector)}
     */
    @Test
    public void constructorTest() {
        // ============ Equivalence Partitions Tests ==============

        Point p = new Point( 1,1,1);
        // EP01: Makes sure we can't create a ray with zero vector from 3 doubles constructor
        assertThrows(IllegalArgumentException.class, () -> new Ray(p, new Vector(0, 0, 0)),
                "EP01: zero vector does not throw an exception");

        // EP02: Makes sure we can't create a ray with zero vector from double3 constructor
        assertThrows(IllegalArgumentException.class, () -> new Ray(p, new Vector(new Double3(0, 0, 0))),
                "EP02: zero vector does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}
     */
    @Test
    public void getPointTest(){
        Ray ray = new Ray(new Point(1,0,0), new Vector(1,0,0));

        // ============== Equivalence Partitions Tests ==================
        // EP01: t is greater than 0
        assertEquals(new Point(5,0,0), ray.getPoint(4),
                "EP01: not working");

        // EP02: t is smaller than 0
        assertThrows(IllegalArgumentException.class, () -> ray.getPoint(-4),
                "EP02: not working");

        // ================ Boundary Values Tests ===================
        // BV01: t is 0
        assertEquals(new Point(1,0,0), ray.getPoint(0),
                "BV01: not working");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}
     */
    @Test
    public void findClosestPointTest(){
        Ray ray = new Ray(new Point(1,0,0), new Vector(1,0,0));
        Point p = new Point(5,0,0);

        // ============== Equivalence Partitions Tests ==================
        // EP01: closest point is in the middle of the list
        assertEquals(p, ray.findClosestPoint(List.of(p.add(new Vector(1,0,0)), p, p.add(new Vector(-7,0,0)), p.add(new Vector(21,0,0)))),
                "EP01: not working");

        // ================ Boundary Values Tests ===================
        // BV01: list of points is empty
        assertNull(ray.findClosestPoint(null),
                "BV01: not working");

        // BV02: closest point is first
        assertEquals(p, ray.findClosestPoint(List.of(p, p.add(new Vector(1,0,0)), p.add(new Vector(-7,0,0)), p.add(new Vector(21,0,0)))),
                "BV02: not working");

        // BV03: closest point is last
        assertEquals(p, ray.findClosestPoint(List.of(p.add(new Vector(1,0,0)), p.add(new Vector(-7,0,0)), p.add(new Vector(21,0,0)), p)),
                "BV03: not working");
    }
}