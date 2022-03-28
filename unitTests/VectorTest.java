package unitTests;

import primitives.Double3;
import primitives.Vector;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

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

        // TC01: Makes sure we can't create a zero vector from 3 doubles constructor
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

        // TC01: scalar is greater than 1
        assertEquals(new Vector(3,3,3), v.scale(3),
                "ERROR: scaling with scalar greater than 1 is not working");

        // TC02: scalar is greater than 0 and less than 1
        assertEquals(new Vector(1d/3,1d/3,1d/3), v.scale(1d/3),
                "ERROR: scaling with scalar greater than 0 and less than 1 is not working");

        // TC03: scalar is greater than -1 and less than 0
        assertEquals(new Vector(1d/3,1d/3,1d/3), v.scale( 1d/3 ),
                "ERROR: scaling with scalar greater than -1 and less than 0 is not working");

        // TC04: scalar is less than -1
        assertEquals(new Vector(-3,-3,-3), v.scale(-3),
                "ERROR: scaling with scalar less than -1 is not working");


        // =============== Boundary Values Tests ==================

        // TC1: scalar is 1
        assertEquals(v, v.scale(1),
                "ERROR: scaling with scalar equal 1 not working");

        // TC2: scalar is -1
        assertEquals(new Vector(-1,-1,-1), v.scale(-1),
                "ERROR: scaling with scalar equal -1 not working");

        // TC3: scalar is 0
        assertThrows(IllegalArgumentException.class, () -> v.scale(0),
                "ERROR: scaling vector with scalar equals 0 does NOT throw exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}
     */
    @org.junit.jupiter.api.Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        //TC1: Test to make sure that the dot product of two orthogonal vector works properly
        assertEquals(v1.dotProduct(v3), 0,
                "ERROR: dotProduct() for orthogonal vectors is not zero");

        //TC2: Test to make sure that the dot product value of a vector is proper
        assertTrue(isZero(v1.dotProduct(v2) + 28),
                "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}
     */
    @org.junit.jupiter.api.Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Basic test (orthogonal vectors taken for simplicity)
        // Check length of cross-product is proper
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001,
                "TC01: crossProduct() wrong result length");
        // Check cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),
                "TC01: crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)),
                "TC01: crossProduct() result is not orthogonal to 2nd operand");

        // TC02: reverse direction
        Vector vr2 = v2.crossProduct(v1);

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () ->v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}
     */
    @org.junit.jupiter.api.Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);

        //TC1: Test to make sure that the squared length of a vector is proper
        assertTrue(isZero(v1.lengthSquared() - 14),
                "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}
     */
    @org.junit.jupiter.api.Test
    void length() {
        // ============ Equivalence Partitions Tests ==============

        //TC1: Test to make sure that the length of a vector is proper
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5),
                "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}
     */
    @org.junit.jupiter.api.Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(0, 3, 4);

        // TC1: make sure that vector.normalize works properly
        assertEquals(v.normalize(), new Vector(0,0.6,0.8),
                "ERROR: normalizing vector is not done correctly");
    }
}