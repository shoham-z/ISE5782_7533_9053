
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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

        // EP01: 2 points of the 3 are unifying
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1, p1.add(new Vector(1,1,1))),
                "ERROR: does not throw an exception when 2 points are the same");

        // EP02: the points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1.add(new Vector(2,2,2)), p1.add(new Vector(1,1,1))),
                "ERROR: does not throw an exception when the points are on the same line");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // EP01: There is a simple single test here
        Point p = new Point(1,1,1);
        Plane plane = new Plane(p, new Point(1,2,1), new Point(1,1,2));
        assertTrue(plane.getNormal(p).equals(new Vector(1,0,0)) || plane.getNormal(p).equals(new Vector(-1,0,0)),
                "ERROR: getnormal method in Plane does not work properly");
    }


    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}
     */
    @Test
    void findIntsersections() {
        Plane plane = new Plane(new Point(1,1,1), new Vector(1,1,1));

        // ============ Equivalence Partitions Tests ==============
        // ****group : ray is neither orthogonal nor parallel to the plane
        // EP01: Ray intersects the plane (1 point)
        List<Point> result = plane.findIntersections(new Ray(new Point(0.5,0.5,0.5), new Vector(1,1,0)));
        assertEquals(1, result.size(), "EP01: Wrong number of points");
        assertEquals(List.of(new Point(1.25,1.25, 0.5)), result, "EP01: Ray crosses plane");
        result.clear();

        // EP02: Ray does not intersect with the plane (0 points)
        result = plane.findIntersections(new Ray(new Point(1.5,1.5,1.5), new Vector(1,1,0)));
        assertNull(result, "EP02: Wrong number of points");


        // ============= Boundary Values Tests =================

        // ****group: Ray is parallel to the plane
        // BV01: Ray included in the plane (0 points) [well, actually infinity, but we can have infinite amount of data]
        result = plane.findIntersections(new Ray(new Point(1,0,2), new Vector(0,-1,1)));
        assertNull(result, "BV01: Wrong number of points");


        // BV02: Ray not included in the plane (0 points)
        result = plane.findIntersections(new Ray(new Point(1,1,1.5), new Vector(0,-1,1)));
        assertNull(result, "BV02: Wrong number of points");


        // ****group: Ray is orthogonal to the plane
        // BV03: Ray starts before the plane (1 point)
        result = plane.findIntersections(new Ray(new Point(0.5,0.5,0.5), new Vector(1,1,1)));
        assertEquals(1, result.size(), "BV03: Wrong number of points");
        assertEquals(List.of(new Point(1,1,1)), result, "BV03: Ray crosses plane");
        result.clear();

        // BV04: Ray starts at the plane (0 points)
        result = plane.findIntersections(new Ray(new Point(1,0,2), new Vector(1,1,1)));
        assertNull(result, "BV04: Wrong number of points");

        // BV05: Ray starts after the plane (0 points)
        result = plane.findIntersections(new Ray(new Point(1.5,1.5,1.5), new Vector(1,1,1)));
        assertNull(result, "BV05: Wrong number of points");

        //****group: special cases
        // BV06: Ray starts at the plane but is neither orthogonal nor parallel to the plane (0 points)
        result = plane.findIntersections(new Ray(new Point(1,0,2), new Vector(1,1.5,1)));
        assertNull(result, "BV06: Wrong number of points");

        // BV07: Ray starts at the point defining the plane but is neither orthogonal nor parallel to the plane
        result = plane.findIntersections(new Ray(new Point(1,1,1), new Vector(1,1.5,1)));
        assertNull(result, "BV07: Wrong number of points");
    }
}