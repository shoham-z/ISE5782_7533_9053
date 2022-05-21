import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.*;

public class OURImages {
    private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(1000);
    private Scene scene1 = new Scene("Test scene");

    private Point spPL = new Point(-50, 0, 25); // Sphere test Position of Light

    private Geometry tube = new Tube(new Ray(new Point(0, 0, -40), new Vector(0, 1, 0)), 30)
            .setEmission(new Color(GREEN).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300).setKt(0.5));

    private final Color purple = new Color(BLUE).add(new Color(RED));

    private Geometry cylinder = new Cylinder(new Ray(new Point(-30, -20, -10), new Vector(1, 1, 1)), 13, 100)
            .setEmission(purple.reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

    private Geometry plane = new Plane(new Point(0, 0, -80), new Vector(1, 0, -1))
            .setEmission(new Color(GRAY))
            .setMaterial(new Material().setKr(1));


    @Test
    void shohamImages() {
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0))
                .setVPSize(160, 160) // 16x9 scaled by 20
                .setVPDistance(1000);

        Scene scene = new Scene("Chess..?");//.setBackground(new Color(135, 206, 235));
        //.setAmbientLight(new AmbientLight(new Color(249,215,28),new Double3(0.5)))

        scene.geometries.add(new Sphere(new Point(0, 0, 50), 20)
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKt(0.5))
                .setEmission(new Color(GREEN).reduce(2)));

        scene.geometries.add(new Polygon(new Point(50, 50, 100), new Point(0, 50, 100),
                new Point(0, 0, 100), new Point(50, 0, 100))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(BLUE)));

        scene.geometries.add(new Polygon(new Point(0, 50, 100), new Point(-50, 50, 100),
                new Point(-50, 0, 100), new Point(0, 0, 100))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(YELLOW)));

        scene.geometries.add(new Polygon(new Point(0, 0, 100), new Point(0, -50, 100),
                new Point(-50, -50, 100), new Point(-50, 0, 100))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(RED)));

        scene.geometries.add(new Polygon(new Point(0, -50, 100), new Point(50, -50, 100),
                new Point(50, 0, 100), new Point(0, 0, 100))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(CYAN)));

        scene.lights.add(new SpotLight(new Color(magenta), new Point(100, 100, 0), new Vector(-1, -1, 1))
                .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        scene.lights.add(new SpotLight(purple, new Point(-100, -100, 0), new Vector(1, 1, 1))
                .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("chess", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void seaImage() {
        Camera camera = new Camera(new Point(0, -100, -1000), new Vector(0, 160, 1000), new Vector(0, 1000, -160))
                .setVPSize(160, 160) // 16x9 scaled by 20
                .setVPDistance(1000);

        Scene scene = new Scene("Chess..?").setBackground(new Color(135, 206, 235));
        //.setAmbientLight(new AmbientLight(new Color(249,215,28),new Double3(0.5)))

        scene.geometries.add(new Triangle(new Point(30, 60, 40), new Point(20, 75, 40), new Point(10, 60, 40))
                .setEmission(new Color(WHITE))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(20, 50, 40), new Vector(0, 1, 0)), 0.5, 30)
                .setEmission(new Color(192, 192, 192))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(0, 50, 40), new Vector(1, 0, 0)), 2, 40)
                .setEmission(new Color(202, 164, 114))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Sphere(new Point(0, 0, 40), 8)
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKt(0.5))
                .setEmission(new Color(GREEN).reduce(2)));

        scene.geometries.add(new Polygon(new Point(-100, 30, 45), new Point(100, 30, 45),
                new Point(100, -40, 45), new Point(-100, -40, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40))
                .setEmission(new Color(242, 217, 181)
                ));
        scene.geometries.add(new Polygon(new Point(-100, 100, 45), new Point(100, 100, 45),
                new Point(100, 30, 45), new Point(-100, 30, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.3))
                .setEmission(new Color(0, 105, 148)
                ));
        scene.geometries.add(new Polygon(new Point(20, 20, 40), new Point(0, 20, 40),
                new Point(0, 0, 40), new Point(20, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(BLUE)));

        scene.geometries.add(new Polygon(new Point(0, 20, 40), new Point(-20, 20, 40),
                new Point(-20, 0, 40), new Point(0, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(YELLOW)));

        scene.geometries.add(new Polygon(new Point(0, 0, 40), new Point(0, -20, 40),
                new Point(-20, -20, 40), new Point(-20, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(RED)));

        scene.geometries.add(new Polygon(new Point(0, -20, 40), new Point(20, -20, 40),
                new Point(20, 0, 40), new Point(0, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(CYAN)));

        scene.lights.add(new DirectionalLight(new Color(YELLOW).scale(0.15), new Vector(1, -1, 100)));

        scene.lights.add(new SpotLight(new Color(magenta), new Point(100, 100, 0), new Vector(-1, -1, 1))
                .setNarrowBeam(2).setKl(0.00001).setKq(0.00001));

        scene.lights.add(new SpotLight(purple, new Point(-100, -100, 0), new Vector(1, 1, 1))
                .setNarrowBeam(2).setKl(0.00001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("sea bottom", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void seaFrontImage() {
        Camera camera = new Camera(new Point(0, -60, -1000), new Vector(0, 120, 1000), new Vector(0, 1000, -120))
                .setVPSize(160, 160) // 16x9 scaled by 20
                .setVPDistance(1000);

        Scene scene = new Scene("Chess..?").setBackground(new Color(135, 206, 235));
        //.setAmbientLight(new AmbientLight(new Color(249,215,28),new Double3(0.5)))

        scene.geometries.add(new Triangle(new Point(30, 60, 40), new Point(20, 75, 40), new Point(10, 60, 40))
                .setEmission(new Color(WHITE))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(20, 50, 40), new Vector(0, 1, 0)), 0.5, 30)
                .setEmission(new Color(192, 192, 192))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(0, 50, 40), new Vector(1, 0, 0)), 2, 40)
                .setEmission(new Color(202, 164, 114))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Sphere(new Point(0, 0, 40), 8)
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKt(0.5))
                .setEmission(new Color(GREEN).reduce(2)));

        scene.geometries.add(new Polygon(new Point(-100, 30, 45), new Point(100, 30, 45),
                new Point(100, -40, 45), new Point(-100, -40, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40))
                .setEmission(new Color(242, 217, 181)
                ));
        scene.geometries.add(new Polygon(new Point(-100, 100, 45), new Point(100, 100, 45),
                new Point(100, 30, 45), new Point(-100, 30, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40))
                .setEmission(new Color(0, 105, 148)
                ));
        scene.geometries.add(new Polygon(new Point(20, 20, 40), new Point(0, 20, 40),
                new Point(0, 0, 40), new Point(20, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(BLUE)));

        scene.geometries.add(new Polygon(new Point(0, 20, 40), new Point(-20, 20, 40),
                new Point(-20, 0, 40), new Point(0, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(YELLOW)));

        scene.geometries.add(new Polygon(new Point(0, 0, 40), new Point(0, -20, 40),
                new Point(-20, -20, 40), new Point(-20, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(RED)));

        scene.geometries.add(new Polygon(new Point(0, -20, 40), new Point(20, -20, 40),
                new Point(20, 0, 40), new Point(0, 0, 40))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.5))
                .setEmission(new Color(CYAN)));

        scene.lights.add(new SpotLight(new Color(magenta), new Point(100, 100, 0), new Vector(-1, -1, 1))
                .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        scene.lights.add(new SpotLight(purple, new Point(-100, -100, 0), new Vector(1, 1, 1))
                .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("seaFront", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }


    /**
     * Creates an image of few houses
     */
    @Test
    void street() {
        Camera camera = new Camera(new Point(2000, 100, 450), new Vector(-2, -0.1, -0.5), new Vector(-0.1, 2, 0))
                .setVPSize(200, 200)
                .setVPDistance(1000);

        Scene scene = new Scene("GroveStreet").setBackground(new Color(135, 206, 235));

        // *group House
        Vector up = new Vector(0, 1, 0);
        Vector right = new Vector(0, 0, -1);

        Geometries houses = new Geometries();

        houses.add(getHouse(new Point(0, -50, 0), 50, up, right.add(new Vector(0, 0, -0.3))));
        houses.add(getHouse(new Point(0, -50, -75), 50, up, right));


        // *group Car
        //Geometries car = new Geometries(new Polygon());


        // Ground
        Geometry ground = new Plane(new Point(0, -50, 0), new Vector(0, 1, 0)).setEmission(new Color(86, 125, 70).scale(1.3)).setMaterial(new Material().setKd(0.5));


        scene.geometries.add(houses, ground);
        //scene.geometries.add(car);
        //scene.lights.add(new SpotLight(new Color(magenta), new Point(50, 50, -50), new Vector(-1, -1, 1))
        //        .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        scene.lights.add(new DirectionalLight(new Color(WHITE).add(new Color(YELLOW)).scale(0.25), new Vector(2, -1, -0.25)));

        //scene.lights.add(new SpotLight(purple, new Point(-50, -50, -50), new Vector(1, 1, 1))
        //       .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("GroveStreet", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }


    /**
     * Builds a house based at a point, with height and
     * @param baseBottom Center of the base of the house
     * @param height Height and width og the house
     * @param up The up direction of the house, used to place the roof
     * @param right The right direction, used to place the door
     * @return A house
     */
    Geometries getHouse(Point baseBottom, double height, Vector up, Vector right) {
        if (up.dotProduct(right) != 0) throw new IllegalArgumentException();
        Material material = new Material().setKd(0.5).setKs(0.5).setShininess(30);
        Vector to = right.crossProduct(up).normalize();
        Geometries house = new Geometries();
        right = right.normalize();
        up = up.normalize();


        Point frontBottomCenter = baseBottom.add(to.scale(height / 2)).add(to.scale(0.000001));

        double doorWidth = height * 3 / 10;
        double doorHeight = height * 3 / 5;

        Point doorBottomRight = frontBottomCenter.add(right.scale(doorWidth / 2));
        Point doorBottomLeft = frontBottomCenter.add(right.scale(-doorWidth / 2));
        Point doorTopRight = doorBottomRight.add(up.scale(doorHeight));
        Point doorTopLeft = doorBottomLeft.add(up.scale(doorHeight));

        Geometry door = new Polygon(doorBottomRight, doorTopRight, doorTopLeft, doorBottomLeft).setMaterial(material).setEmission(new Color(175, 42, 42));


        Geometries roof = constructPyramid(baseBottom.add(up.scale(height)), height, height, to, up, new Color(164, 74, 74), material);

        Geometries body = constructCube(baseBottom.add(up.scale(height / 2)), height, up, right, new Color(249, 253, 183), material);

        house.add(roof, body, door);
        return house;
    }


    /**
     * Constructs a pyramid
     *
     * @param bottomCenter The bottom center point of the pyramid
     * @param height       The height of the pyramid
     * @param width        The width of the pyramid
     * @param vTo          The direction the pyramid is facing
     * @param vUp          The direction of the pyramid's head
     * @param color        The color of the pyramid
     * @param material     The material of the pyramid
     * @return The pyramid
     */
    Geometries constructPyramid(Point bottomCenter, double height, double width, Vector vTo, Vector vUp, Color color, Material material) {
        if (vTo.dotProduct(vUp) != 0) throw new IllegalArgumentException();
        Vector vRight = vTo.crossProduct(vUp).normalize();
        Point top = bottomCenter.add(vUp.normalize().scale(height));
        Point frontCenter = bottomCenter.add(vTo.normalize().scale(width / 2));
        Point leftFront = frontCenter.add(vRight.normalize().scale(-width / 2));
        Point rightFront = frontCenter.add(vRight.normalize().scale(width / 2));
        Point backCenter = bottomCenter.add(vTo.normalize().scale(-width / 2));
        Point leftBack = backCenter.add(vRight.normalize().scale(-width / 2));
        Point rightBack = backCenter.add(vRight.normalize().scale(width / 2));

        Geometry front = new Triangle(leftFront, rightFront, top).setEmission(color).setMaterial(material);
        Geometry back = new Triangle(leftBack, rightBack, top).setEmission(color).setMaterial(material);
        Geometry right = new Triangle(rightBack, rightFront, top).setEmission(color).setMaterial(material);
        Geometry left = new Triangle(leftFront, leftBack, top).setEmission(color).setMaterial(material);
        Geometry bottom = new Polygon(leftBack, leftFront, rightFront, rightBack).setEmission(color).setMaterial(material);

        return new Geometries(front, back, right, left, bottom);
    }


    /**
     * Constructs a two-dimensional rectangle
     *
     * @param center Center point of the rectangle
     * @param vRight Right direction of the rectangle
     * @param vUp    Up direction of the rectangle
     * @param height Height of the rectangle
     * @param width  Width of the rectangle
     * @return The new rectangle
     */
    Polygon constructRectangle(Point center, Vector vRight, Vector vUp, double height, double width) {
        double stepUp = height / 2;
        double stepSide = width / 2;
        Point frontBottomCenter = center.add(vUp.scale(-stepUp));
        Point frontTopCenter = center.add(vUp.scale(stepUp));
        Point frontTopRight = frontTopCenter.add(vRight.scale(stepSide));
        Point frontTopLeft = frontTopCenter.add(vRight.scale(-stepSide));
        Point frontBottomRight = frontBottomCenter.add(vRight.scale(stepSide));
        Point frontBottomLeft = frontBottomCenter.add(vRight.scale(-stepSide));
        return new Polygon(frontTopRight, frontTopLeft, frontBottomLeft, frontBottomRight);
    }

    /**
     * Construct a cube
     *
     * @param center   Center of the Cube
     * @param length   Length of the side of cube
     * @param vUp      The direction the cube will be facing
     * @param vRight   The direction to the right of the cube
     * @param color    The color the cube will have
     * @param material The material of the cube
     * @return The new cube
     */
    Geometries constructCube(Point center, double length, Vector vUp, Vector vRight, Color color, Material material) {
        if (vUp.dotProduct(vRight) != 0) throw new IllegalArgumentException();
        Vector vTo = vRight.crossProduct(vUp);
        Geometries cube = new Geometries();
        double step = length / 2;

        Point frontCenter = center.add(vTo.scale(step));
        cube.add(constructRectangle(frontCenter, vRight, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point backCenter = center.add(vTo.scale(-step));
        cube.add(constructRectangle(backCenter, vRight, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point rightCenter = center.add(vRight.scale(step));
        cube.add(constructRectangle(rightCenter, vTo, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point leftCenter = center.add(vRight.scale(-step));
        cube.add(constructRectangle(leftCenter, vTo, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point topCenter = center.add(vUp.scale(step));
        cube.add(constructRectangle(topCenter, vTo, vRight, length, length)
                .setEmission(color).setMaterial(material));

        Point bottomCenter = center.add(vUp.scale(-step));
        cube.add(constructRectangle(bottomCenter, vTo, vRight, length, length)
                .setEmission(color).setMaterial(material));

        return cube;
    }


    Intersectable constructCar(Point location, double length, double width, double height, Vector vTo, Vector vRight, Color color) {
        if (vTo.dotProduct(vRight) != 0) throw new IllegalArgumentException();
        Geometries car = new Geometries();

        Vector vUp = vRight.crossProduct(vTo);
        Point frontCenter = location.add(vTo.scale(length / 2));
        Point frontTopCenter = frontCenter.add(vUp.scale(height / 2));
        Point frontBottomCenter = frontCenter.add(vUp.scale(-height / 2));
        Point frontTopRight = frontTopCenter.add(vRight.scale(width / 2));
        Point frontTopLeft = frontTopCenter.add(vRight.scale(-width / 2));
        Point frontBottomRight = frontBottomCenter.add(vRight.scale(width / 2));
        Point frontBottomLeft = frontBottomCenter.add(vRight.scale(-width / 2));
        Geometry front = new Polygon(frontTopRight, frontTopLeft, frontBottomLeft, frontBottomRight).setEmission(color);
        car.add(front);

        ///NEED TO FINISH
        return car;
    }
}


