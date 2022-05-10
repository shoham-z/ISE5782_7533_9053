import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

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

    /**
     * This test method creates and image with a cylinder and a tube
     */
    @Test
    void cylinderTubeImage() {
        scene1.geometries.add(cylinder);
        scene1.lights.add(new DirectionalLight(new Color(GREEN).scale(2), new Vector(-0.9, -0.9, -1)));

        ImageWriter imageWriter = new ImageWriter("CylinderDirectional", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

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

        ImageWriter imageWriter = new ImageWriter("chess", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }


    /**
     * Creates an image of two cubes
     */
    @Test
    void justCoupleCubesAndPyramid() {
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0))
                .setVPSize(200, 200)
                .setVPDistance(1000);

        Scene scene = new Scene("Chess..?").setBackground(new Color(135, 206, 235))
        .setAmbientLight(new AmbientLight(new Color(249,215,28).scale(0.5),new Double3(0.5)));

        Material material = new Material().setKd(0.5).setKs(0.5).setShininess(5);
        scene.geometries.add(constructCube(new Point(-50, -50, 10), 50, camera.getvRight(), camera.getvTo(), new Color(YELLOW), material));
        scene.geometries.add(constructCube(new Point(50, 50, 10), 30, camera.getvRight(), camera.getvTo(), new Color(RED), material));
        //scene.geometries.add(constructPyramid(new Point(0, 0, 10), 100,100, camera.getvRight().add(new Vector(0.2,0,0)), camera.getvUp().add(new Vector(0,0,-0.4)), new Color(GREEN), material.setKt(0.7)));
scene.geometries.add(constructTower(Point.ZERO,Math.PI/5,40,camera.getvUp(),new Color(YELLOW),material));
        //scene.lights.add(new SpotLight(new Color(magenta), new Point(50, 50, -50), new Vector(-1, -1, 1))
        //        .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        //scene.lights.add(new SpotLight(purple, new Point(-50, -50, -50), new Vector(1, 1, 1))
        //       .setNarrowBeam(5).setKl(0.00001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("just couple cubes and pyramid", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }


    Geometries constructTower(Point base, double angle, double height,Vector vUp, Color color, Material material){
        Geometry baseTower = new Cylinder(new Ray(base, vUp),angle*height/(Math.PI/2),height).setEmission(color).setMaterial(material);
        Geometry top = new Cone(new Ray(base.add(vUp.normalize().scale(height*5/4)), vUp.normalize().scale(-1)),angle ,height/4).setEmission(color).setMaterial(material);
        return new Geometries(baseTower, top);
    }


    /**
     * Constructs a pyramid
     * @param bottomCenter The bottom center point of the pyramid
     * @param height The height of the pyramid
     * @param width The width of the pyramid
     * @param vTo The direction the pyramid is facing
     * @param vUp The direction of the pyramid's head
     * @param color The color of the pyramid
     * @param material The material of the pyramid
     * @return The pyramid
     */
    Geometries constructPyramid(Point bottomCenter, double height,double width, Vector vTo, Vector vUp, Color color, Material material){
        if (vTo.dotProduct(vUp) != 0) throw new IllegalArgumentException();
        Vector vRight = vTo.crossProduct(vUp).normalize();
        Point top = bottomCenter.add(vUp.normalize().scale(height));
        Point frontCenter = bottomCenter.add(vTo.normalize().scale(width/2));
        Point leftFront = frontCenter.add(vRight.normalize().scale(-width/2));
        Point rightFront = frontCenter.add(vRight.normalize().scale(width/2));
        Point backCenter = bottomCenter.add(vTo.normalize().scale(-width/2));
        Point leftBack = backCenter.add(vRight.normalize().scale(-width/2));
        Point rightBack = backCenter.add(vRight.normalize().scale(width/2));

        Geometry front = new Triangle(leftFront,rightFront,top).setEmission(color).setMaterial(material);
        Geometry back = new Triangle(leftBack,rightBack,top).setEmission(color).setMaterial(material);
        Geometry right = new Triangle(rightBack,rightFront,top).setEmission(color).setMaterial(material);
        Geometry left = new Triangle(leftFront,leftBack,top).setEmission(color).setMaterial(material);
        Geometry bottom = new Polygon(leftBack,leftFront,rightFront,rightBack).setEmission(color).setMaterial(material);

        return new Geometries(front,back,right,left,bottom);
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
     * @param vTo      The direction the cube will be facing
     * @param vRight   The direction to the right of the cube
     * @param color    The color the cube will have
     * @param material The material of the cube
     * @return The new cube
     */
    Intersectable constructCube(Point center, double length, Vector vTo, Vector vRight, Color color, Material material) {
        if (vTo.dotProduct(vRight) != 0) throw new IllegalArgumentException();
        Vector vUp = vRight.crossProduct(vTo);
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


