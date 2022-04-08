package unitTests;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Vector;
import renderer.Camera;

/**
 * Testing Camera ray intersections with geometries
 *
 * @author Shoham
 *
 */
public class IntegrationTest {//VPsize = 3x3, Pixels = 3x3
    static final Point ZERO_POINT = new Point(0, 0, 0);
    Camera camera = new Camera(ZERO_POINT, new Vector(0,0,-1), new Vector(0,1,0)).setVPSize(3,3).setVPDistance(1);


    /**
     * Test method for Camera constructed ray intersections with sphere
     */
    @Test
    void SphereIntersections() {
        //TC01: Small Sphere in front of the VP
        Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        double counts_intersection1 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection1 += sphere1.findIntersections(camera.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(2, counts_intersection1, "TC01");


        //TC02: Big Sphere and the VP is almost completely inside
        Camera camera2 = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).setVPSize(3,3).setVPDistance(1);
        Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        double counts_intersection2 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection2 += sphere2.findIntersections(camera2.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(18, counts_intersection2, "TC02");

        //TC03: Small Sphere intersects with the VP
        Sphere sphere3 = new Sphere(new Point(0, 0, -2), 2);
        double counts_intersection3 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection3 += sphere3.findIntersections(camera2.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(10, counts_intersection3, "TC03");

        //TC04: VP inside the big sphere
        Sphere sphere4 = new Sphere(new Point(1, 1, -1), 4);
        double counts_intersection4 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection4 += sphere4.findIntersections(camera.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(9, counts_intersection4, "TC04");

        //TC05: Small Sphere in behind camera
        Sphere sphere5 = new Sphere(new Point(0, 0, 1), 0.5);
        double counts_intersection5 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection5 += sphere5.findIntersections(camera.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(0, counts_intersection5, "TC05");
    }

    /**
     * Test method for Camera constructed ray intersections with plane
     */
    @Test
    void PlaneIntersections() {
        //TC01: Plane parallel to the VP
        Plane plane1 = new Plane(new Point(0,5,-3),new Vector(0,0,1));
        double counts_intersection1 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection1 += plane1.findIntersections(camera.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(9, counts_intersection1, "TC01");


        //TC02: plane not parallel and there are 9 intersections
        Plane plane2 = new Plane(new Point(0,2,-1), new Vector(0,0.5,-1));
        double counts_intersection2 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection2 += plane2.findIntersections(camera.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(9, counts_intersection2, "TC02");

        //TC03: plane not parallel and there are 6 intersections
        Plane plane3 = new Plane(new Point(0,2,-1), new Vector(0,5,-1));
        double counts_intersection3 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection3 += plane3.findIntersections(camera.constructRay(3,3,j,i)).size();
            }
        }
        assertEquals(6, counts_intersection3, "TC03");
    }

    /**
     * Test method for Camera constructed ray intersections with triangle
     */
    @Test
    void TriangleIntersections() {
        //TC01: Triangle is small - only 1 intersection
        Triangle triangle1 = new Triangle(new Point(0,1,-2),new Point(1,-1,-2), new Point(-1,-1,-2));
        double counts_intersection1 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection1 += triangle1.findIntersections(camera.constructRay(3, 3, j, i)).size();
            }
        }
        assertEquals(1, counts_intersection1, "TC01");


        //TC01: Triangle is small but long - 2 intersections
        Triangle triangle2 = new Triangle(new Point(0,20,-2),new Point(1,-1,-2), new Point(-1,-1,-2));
        double counts_intersection2 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                counts_intersection2 += triangle2.findIntersections(camera.constructRay(3, 3, j, i)).size();
            }
        }
        assertEquals(2, counts_intersection2, "TC02");
    }
}
