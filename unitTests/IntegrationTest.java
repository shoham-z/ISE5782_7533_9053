import geometries.*;
import primitives.*;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;


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

    private void countIntersections(Geometry geometry, Camera camera, int expected, String message) {
        if (camera == null) camera = this.camera;
        int countIntersections = 0;
        List<Point> temp;
        for (int i = 0; i < VPheight; i++) {
            for (int j = 0; j < VPwidth; j++) {
                temp = geometry.findIntersections(camera.constructRay(this.nx, this.ny, j, i));
                if (temp != null) countIntersections += temp.size();
            }
        }
        assertEquals(expected, countIntersections, message);
    }


    /**
     * Test method for Camera constructed ray intersections with sphere
     */
    @Test
    void SphereIntersections() {
        //TC01: Small Sphere in front of the VP
        Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        countIntersections(sphere1, camera, 2, "TC01");


        //TC02: Big Sphere and the VP is almost completely inside
        Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(VPheight, VPwidth).setVPDistance(1);
        Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        countIntersections(sphere2, camera2, 18, "TC02");

        //TC03: Small Sphere intersects with the VP
        Sphere sphere3 = new Sphere(new Point(0, 0, -2), 2);
        countIntersections(sphere3, camera2, 10, "TC03");

        //TC04: VP inside the big sphere
        Sphere sphere4 = new Sphere(new Point(1, 1, -1), 4);
        countIntersections(sphere4, camera, 9, "TC04");

        //TC05: Small Sphere in behind camera
        Sphere sphere5 = new Sphere(new Point(0, 0, 1), 0.5);
        countIntersections(sphere5, camera, 0, "TC05");
    }

    /**
     * Test method for Camera constructed ray intersections with plane
     */
    @Test
    void PlaneIntersections() {
        //TC01: Plane parallel to the VP
        Plane plane1 = new Plane(new Point(0, 5, -3), new Vector(0, 0, 1));
        countIntersections(plane1, camera, 9, "TC01");


        //TC02: plane not parallel and there are 9 intersections
        Plane plane2 = new Plane(new Point(0, 2, -1), new Vector(0, 0.5, -1));
        countIntersections(plane2, camera,9,  "TC02");

        //TC03: plane not parallel and there are 6 intersections
        Plane plane3 = new Plane(new Point(0, 2, -1), new Vector(0, 5, -1));
        countIntersections(plane3, camera,6 , "TC03");
    }

    /**
     * Test method for Camera constructed ray intersections with triangle
     */
    @Test
    void TriangleIntersections() {
        //TC01: Triangle is small - only 1 intersection
        Triangle triangle1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        countIntersections(triangle1, camera, 1, "TC01");


        //TC01: Triangle is small but long - 2 intersections
        Triangle triangle2 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        countIntersections(triangle2, camera, 2, "TC02");
    }
}
