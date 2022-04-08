import geometries.*;
import primitives.*;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


/**
 * Testing Camera ray intersections with geometries
 *
 * @author Shoham
 */
public class IntegrationTest {

    int nx = 3;
    int ny = 3;
    int VPwidth = 3;
    int VPheight = 3;
    static final Point ZERO_POINT = new Point(0, 0, 0);
    Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(VPheight, VPwidth).setVPDistance(1);

    int countIntersections(Geometry geometry, Camera camera) {
        if (camera == null) camera = this.camera;
        int counts_intersection = 0;
        for (int i = 0; i < VPheight; i++) {
            for (int j = 0; j < VPwidth; j++) {
                try {
                    counts_intersection += geometry.findIntersections(camera.constructRay(nx, ny, j, i)).size();
                } catch (NullPointerException ignore) {

                }
            }
        }
        return counts_intersection;
    }


    /**
     * Test method for Camera constructed ray intersections with sphere
     */
    @Test
    void SphereIntersections() {
        //TC01: Small Sphere in front of the VP
        Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, countIntersections(sphere1, camera), "TC01");


        //TC02: Big Sphere and the VP is almost completely inside
        Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(VPheight, VPwidth).setVPDistance(1);
        Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, countIntersections(sphere2, camera2), "TC02");

        //TC03: Small Sphere intersects with the VP
        Sphere sphere3 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, countIntersections(sphere3, camera2), "TC03");

        //TC04: VP inside the big sphere
        Sphere sphere4 = new Sphere(new Point(1, 1, -1), 4);
        assertEquals(9, countIntersections(sphere4, camera), "TC04");

        //TC05: Small Sphere in behind camera
        Sphere sphere5 = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, countIntersections(sphere5, camera), "TC05");
    }

    /**
     * Test method for Camera constructed ray intersections with plane
     */
    @Test
    void PlaneIntersections() {
        //TC01: Plane parallel to the VP
        Plane plane1 = new Plane(new Point(0, 5, -3), new Vector(0, 0, 1));
        assertEquals(9, countIntersections(plane1, camera), "TC01");


        //TC02: plane not parallel and there are 9 intersections
        Plane plane2 = new Plane(new Point(0, 2, -1), new Vector(0, 0.5, -1));
        assertEquals(9, countIntersections(plane2, camera), "TC02");

        //TC03: plane not parallel and there are 6 intersections
        Plane plane3 = new Plane(new Point(0, 2, -1), new Vector(0, 5, -1));
        assertEquals(6, countIntersections(plane3, camera), "TC03");
    }

    /**
     * Test method for Camera constructed ray intersections with triangle
     */
    @Test
    void TriangleIntersections() {
        //TC01: Triangle is small - only 1 intersection
        Triangle triangle1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(1, countIntersections(triangle1, camera), "TC01");


        //TC01: Triangle is small but long - 2 intersections
        Triangle triangle2 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, countIntersections(triangle2, camera), "TC02");
    }
}
