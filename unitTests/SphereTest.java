
import geometries.Intersectable.GeoPoint;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 *
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
        Sphere sphere = new Sphere(new Point(1, 0, 0), 5); //(x-x1)^2 + (y-y1)^2 + (z-z1)^2 = r^2
        assertEquals(sphere.getNormal(new Point(6, 0, 0)), new Vector(1, 0, 0),
                "ERROR: normal for sphere is not working");
    }


    /**
     * Test method for {@link Sphere#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // EP01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "EP01: Ray's line out of sphere");

        // EP02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "EP02: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "EP02: Ray crosses sphere");
        result = null;

        // EP03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, 0.5, 0),
                new Vector(3, 1, 0)));
        assertEquals(List.of(new Point(1.6851646544245034, 0.7283882181415011, 0)), result, "EP03: Wrong intersections");

        // EP04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(2, 1, 0),
                new Vector(3, 1, 0)));
        assertNull(result, "EP04: Wrong number of points");
        // ...

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // BV01: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0.2, 0.6, 0),
                new Vector(0, -0.5, 0)));
        assertEquals(1, result.size(), "BV01: Wrong number of points");
        assertEquals(List.of(new Point(0.2, -0.6, 0)), result, "BV01: Ray crosses sphere");
        result = null;

        // BV02: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(0.2, 0.6, 0),
                new Vector(0, 1, 0)));
        assertNull(result, "BV02: Wrong number of points");

        // **** Group: Ray's line goes through the center
        // BV03: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1.1, 0),
                new Vector(0, -0.5, 0)));
        assertEquals(2, result.size(), "BV03: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1, 1, 0), new Point(1, -1, 0)), result,
                "BV03: Ray crosses sphere");
        result = null;

        // BV04: Ray starts at sphere and goes inside (1 points)

        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(0, -0.5, 0)));
        assertEquals(1, result.size(), "BV04: Wrong number of points");
        assertEquals(List.of(new Point(1, -1, 0)), result,
                "BV04: Ray crosses sphere");
        result = null;

        // BV05: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0.9, 0),
                new Vector(0, -0.5, 0)));
        assertEquals(1, result.size(), "BV05: Wrong number of points");
        assertEquals(List.of(new Point(1, -1, 0)), result,
                "BV05: Ray crosses sphere");
        result = null;

        // BV06: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(0, -0.5, 0)));
        assertEquals(1, result.size(), "BV06: Wrong number of points");
        assertEquals(List.of(new Point(1, -1, 0)), result,
                "BV06: Ray crosses sphere");
        result = null;

        // BV07: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(0, 0.5, 0)));
        assertNull(result, "BV07: Wrong number of points");

        // BV08: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1.1, 0),
                new Vector(0, 0.5, 0)));
        assertNull(result, "BV08: Wrong number of points");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // BV09: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, 1, 0),
                new Vector(0, -0.5, 0)));
        assertNull(result, "BV09: Wrong number of points");


        // BV10: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),
                new Vector(0, -0.5, 0)));
        assertNull(result, "BV10: Wrong number of points");


        // BV11: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, -1, 0),
                new Vector(0, -0.5, 0)));
        assertNull(result, "BV11: Wrong number of points");


        // **** Group: Special cases
        // BV12: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point(3, 2, 0),
                new Vector(0, -0.5, 0)));
        assertNull(result, "BV12: Wrong number of points");
    }

    /**
     * Test method for {@link geometries.Sphere#findGeoIntersections(Ray, double)}
     */
    @Test
    void testFindGeoPoint() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        Ray ray1 = new Ray(new Point(-1,0,0), new Vector(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        // **** group 1: there is intersection
        // EP01: Point's distance from the start of the ray is less the distance given
        assertEquals(List.of(new GeoPoint(sphere, new Point(0,0,0))) , sphere.findGeoIntersections(ray1, 2),
                "EP01");

        // EP02: Point's distance from the start of the ray is more the distance given
        assertNull(sphere.findGeoIntersections(ray1, 0.5),"EP02");

        // **** group 2: there is no intersection
        Ray ray2 = new Ray(new Point(-1,0,0), new Vector(1,1,0));
        // EP03: Point's distance from the start of the ray is less the distance given
        assertNull(sphere.findGeoIntersections(ray2, 2), "EP03");

        // EP04: Point's distance from the start of the ray is more the distance given
        assertNull(sphere.findGeoIntersections(ray2, 0.5),"EP04");



        // ============ Boundary Value Tests ====================
        // **** group 1: there is intersection
        // BV01: The point is at the distance exactly
        assertEquals(List.of(new GeoPoint(sphere, new Point(0,0,0))) , sphere.findGeoIntersections(ray1, 1),
                "BV01");

        // **** group 2: there is no intersection
        // BV02: The point is at the distance exactly
        assertNull(sphere.findGeoIntersections(ray2, 1), "BV02");

    }
}