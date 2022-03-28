package unitTests;

import primitives.Vector;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for primitives.Vector class
 * @author Shoham
 */
class VectorTest {

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}
     */
    @Test
    public void constructorTest() {
        // ============ Equivalence Partitions Tests ==============

        // EP01: Makes sure we can't create a zero vector from 3 doubles constructor
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "ERROR: zero vector does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @org.junit.jupiter.api.Test
    void scale() {

        Vector v = new Vector(1,1,1);
        // ============ Equivalence Partitions Tests ==============

        // EP01: scalar is greater than 1
        assertEquals(new Vector(3,3,3), v.scale(3),
                "ERROR: scaling with scalar greater than 1 is not working");

        // EP02: scalar is greater than 0 and less than 1
        assertEquals(new Vector(1d/3,1d/3,1d/3), v.scale(1d/3),
                "ERROR: scaling with scalar greater than 0 and less than 1 is not working");

        // EP03: scalar is greater than -1 and less than 0
        assertEquals(new Vector(1d/3,1d/3,1d/3), v.scale( 1d/3 ),
                "ERROR: scaling with scalar greater than -1 and less than 0 is not working");

        // EP04: scalar is less than -1
        assertEquals(new Vector(-3,-3,-3), v.scale(-3),
                "ERROR: scaling with scalar less than -1 is not working");


        // =============== Boundary Values Tests ==================

        // BV01: scalar is 1
        assertEquals(v, v.scale(1),
                "ERROR: scaling with scalar equal 1 not working");

        // BV02: scalar is -1
        assertEquals(new Vector(-1,-1,-1), v.scale(-1),
                "ERROR: scaling with scalar equal -1 not working");

        // BV03: scalar is 0
        assertThrows(IllegalArgumentException.class, () -> v.scale(0),
                "ERROR: scaling vector with scalar equals 0 does NOT throw exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // EP01: Test to make sure that the dot product of two orthogonal vector works properly
        assertEquals(v1.dotProduct(v3), 0,
                "ERROR: dotProduct() for orthogonal vectors is not zero");

        // EP02: Test to make sure that the dot product value of a vector is proper
        assertEquals(v1.dotProduct(v2) + 28, 0, 0.000001,
                "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}
     */
    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // EP01: Basic test (orthogonal vectors taken for simplicity)
        // Check length of cross-product is proper
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001,
                "EP01: length of cross product");
        // Check cross-product result orthogonality to its operands
        assertEquals(vr.dotProduct(v1), 0, 0.000001,
                "EP01: v1 not orthogonal");
        assertEquals(vr.dotProduct(v2), 0, 0.000001,
                "EP01: v2 not orthogonal");

        // EP02: reverse direction
        Vector vr2 = v2.crossProduct(v1);
        // Check the length of vr and vr2 equal
        assertEquals(vr2.length(), vr.length(), 0.000001,
                "EP02: length of vr2 not equal to length of vr");
        // Check the direction is good
        // Check that cross product of vr, vr2 throws exception
        assertThrows(IllegalArgumentException.class, () -> vr.crossProduct(vr2),
                "EP02: cross product does not throw exception");
        // Check that dot product of vr, vr2 is zero
        assertEquals(vr.dotProduct(vr2), vr.dotProduct(vr.scale(-1)), 0.000001,
                "EP02: dot product is not zero");

        // =============== Boundary Values Tests ==================
        // BV01: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () ->v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);

        // EP01: Test to make sure that the squared length of a vector is proper
        assertEquals(v1.lengthSquared() - 14, 0, 0.000001,
                "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============

        // EP01: Test to make sure that the length of a vector is proper
        assertEquals(new Vector(0, 3, 4).length() - 5, 0, 0.000001,
                "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(0, 3, 4);

        // EP01: make sure that vector.normalize works properly
        assertEquals(v.normalize(), new Vector(0,0.6,0.8),
                "ERROR: normalizing vector is not done correctly");
    }
}