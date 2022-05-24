import ComplexObjects.House;
import ComplexObjects.StreetLamp;
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

        int howManyHouses = 20;
        double houseSize = 30;

        Scene scene = new Scene("GroveStreet").setBackground(new Color(2, 25, 60));

        // ****geometries start

        // *group House
        Vector up = new Vector(0, 1, 0);
        Vector to = new Vector(-1, 0, 0);

        List<House> houses = new LinkedList<>();
        Point housesCenter = new Point(-30, -50, -140);


        double step = 2;
        for (int i = 1; i < howManyHouses * step; i += step) {
            houses.add(new House(housesCenter.add(to.scale(houseSize * i)), houseSize, up, to.scale(-1)));
        }

        //Geometries car = new Geometries(new Polygon());


        Geometry ground = new Plane(new Point(0, -50, 0), new Vector(0, 1, 0)).setEmission(new Color(27, 55, 39).scale(0.85)).setMaterial(new Material().setKd(0.5));

        for (House house : houses) scene.geometries.add(house.getHouse());
        scene.geometries.add(ground);
        // ****geometries end

        // ****lights start
        List<StreetLamp> streetLamps = new LinkedList<>();

        for (int i = 1; i < howManyHouses * step/2; i += step) {
            streetLamps.add(new StreetLamp(new Point(100, -50, -70).add(to.scale(houseSize/2)).add(to.scale(4 * houseSize * i)), 50,
                    new Color(YELLOW).scale(0.5), up, 1.25));
        }

        for (StreetLamp streetLamp : streetLamps) scene.addStreetLamp(streetLamp);

        //scene.lights.add(new DirectionalLight(new Color(GREEN), new Vector(0,-0.5,-1)));
        // ****lights end

        ImageWriter imageWriter = new ImageWriter("GroveStreet", 500, 500);
        camera.setImageWriter(imageWriter)
                .setAntiAliasing(1)//
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
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


