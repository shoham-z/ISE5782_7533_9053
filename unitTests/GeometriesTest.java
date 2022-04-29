

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Geometries class
 *
 * @author Shoham
 */
class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#add(Intersectable geometries)}
     */
    @Test
    void TestAdd() {
        Plane plane = new Plane(new Point(1, 1, 1), new Vector(1, 1, 1));
        Sphere sphere = new Sphere(new Point(2, 3, 1), 4);
        List<Intersectable> intersectable = new ArrayList<>(List.of(plane));
        intersectable.add(sphere);
        assertEquals(new ArrayList<>(List.of(plane, sphere)), intersectable,
                "Geometries.add method not working");
    }

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    void TestFindIntersections() {
        Geometries geometries = new Geometries(new Plane(new Point(1, 1, 1), new Vector(1, 1, 1)),
                new Triangle(new Point(2, 0, 0), new Point(0, 2, 0), new Point(2, 2, 0)),
                new Sphere(new Point(2, 2, 1), 4));
        Ray ray = new Ray(new Point(1.5, 1, 6), new Vector(0, 0, -1));

        // ============ Equivalence Partitions Tests ==============
        // ray intersect with some geometries
        assertEquals(3, geometries.findIntersections(new Ray(new Point(1.5, -1, 6), new Vector(0, 0, -1))).size(),
                "EP01: not");


        // ============= Boundary Values Tests =================
        // BV01: geometries is empty
        assertNull(new Geometries().findIntersections(ray), "BV01: geometries is empty");


        // BV02: ray intersect with no geometries
        assertNull(geometries.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(-1, 0, 0))),
                "BV02: find intersection not working");

        // BV03: ray intersect with one geometry only
        assertEquals(1, geometries.findIntersections(new Ray(new Point(-2, -2, 0), new Vector(1, 0, 0))).size(),
                "BV02: find intersection not working");


        // BV04: ray intersect with all geometries
        assertEquals(4, geometries.findIntersections(new Ray(new Point(1.5, 1, 6), new Vector(0, 0, -1))).size(),
                "BV04: find intersection not working");
    }
}