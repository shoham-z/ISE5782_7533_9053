

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

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void findIntsersections() {
        Tube tube = new Tube(new Ray(new Point(1,1,1), new Vector(1,0,0)),1);
        // **** Group: ray is parallel to the tube
        // ============ Equivalence Partitions Tests ==============
        //EP01: the ray is outside the tube all the time( 0 intersections)
        assertNull(tube.findIntersections(new Ray(new Point(1,0,0),new Vector(1,0,0))),
        "the ray is outside the tube all the time( 0 intersections)");
        //EP02: the ray is congruent to the edge of the tube
        assertNull(tube.findIntersections(new Ray(new Point(1,1,0),new Vector(1,0,0))),
                "the ray is congruent to the tube");
        //EP03: the ray is inside the tube
        assertNull(tube.findIntersections(new Ray(new Point(1,1,1.5),new Vector(1,0,0))),
                "the ray is inside the tube");
        //EP04: the ray is congruent to the center of the tube
        assertNull(tube.findIntersections(new Ray(new Point(2,1,1),new Vector(1,0,0))),
                "the ray is congruent to the center of the tube");
        // ============ Boundary Value Tests ====================
        //BV01: the ray is cngruent to the center of the tube and starts at the same point
        assertNull(tube.findIntersections(new Ray(new Point(1,1,1),new Vector(1,0,0))),
                "the ray is congruent to the center of the tube and starts at the same point");

        //**** Group:the ray is vertical to the tube
        // ============ Equivalence Partitions Tests ==============
        //EP05: the ray starts inside the tube
        assertNull(tube.findIntersections(new Ray(new Point(2,1.5,1),new Vector(0,1,0))),
                "the ray starts inside the tube");
        //EP06: the  ray starts outside the tube and transverses it twice
        assertNull(tube.findIntersections(new Ray(new Point(2,-1,1.5),new Vector(0,1,0))),
                "the  ray starts outside the tube and transverses it twice");
        //EP07: the ray starts outside the tube and does not transvers it
        assertNull(tube.findIntersections(new Ray(new Point(2,-1,1.5),new Vector(0,1,0))),
                "the ray starts outside the tube and does not transverse it");

    }
}