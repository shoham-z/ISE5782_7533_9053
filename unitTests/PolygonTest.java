

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTest {

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // EP01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // EP02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // EP03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // EP04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // BV01: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // BV02: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // BV03: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // EP01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to polygon");
    }


    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}
     */
    @Test
    void findIntsersections() {
        Polygon polygon = new Polygon(new Point(2, 2, 0),
                new Point(-2, 2, 0),
                new Point(-2, -2, 0),
                new Point(2, -2, 0));

        fail("not implemented yet");

        // ================== Equivalence Partition Tests ======================
        // EP01: Ray intersects with polygon (1 point)
        List<Point> result = polygon.findIntersections(new Ray(new Point(0.75, 0.75, 0.75),
                new Vector(0, 0, -1)));
        assertEquals(1, result.size(), "EP01: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0.75, 0.75, 0)), result, "EP01: Ray crosses polygon");
        result.clear();

        // EP02: Ray intersects with the plane against the edge of polygon (0 points)
        result = polygon.findIntersections(new Ray(new Point(3, 1.5, 0.75),
                new Vector(0, 0, -1)));
        assertEquals(0, result.size(), "EP02: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(), result, "EP02: Ray crosses polygon");
        result.clear();

        // EP03: Ray intersects with the plane against the vertex of polygon (0 points)
        result = polygon.findIntersections(new Ray(new Point(3, 3, 0.75),
                new Vector(0, 0, -1)));
        assertEquals(0, result.size(), "EP03: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(), result, "EP03: Ray crosses polygon");
        result.clear();

        // =============== Boundary Values Tests ==================
        // BV01: Ray intersects with edge (0 points)
        result = polygon.findIntersections(new Ray(new Point(2, 1, 0.75),
                new Vector(0, 0, -1)));
        assertEquals(1, result.size(), "BV01: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1, 0.75, 0)), result, "BV01: Ray crosses polygon");
        result.clear();

        // BV02: Ray intersects with vertex (0 points)
        result = polygon.findIntersections(new Ray(new Point(2, 2, 0.75),
                new Vector(0, 0, -1)));
        assertEquals(0, result.size(), "BV02: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(), result, "BV02: Ray crosses polygon");
        result.clear();

        // BV03: Ray intersects with edge's continuation (0 points)
        result = polygon.findIntersections(new Ray(new Point(2, 3, 0.75),
                new Vector(0, 0, -1)));
        assertEquals(0, result.size(), "BV03: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(), result, "BV03: Ray crosses polygon");
        result.clear();
    }
}
