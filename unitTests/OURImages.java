import ComplexObjects.Box;
import ComplexObjects.Cube;
import ComplexObjects.House;
import ComplexObjects.StreetLamp;
import geometries.*;
import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.*;

public class OURImages {
    private final Color purple = new Color(BLUE).add(new Color(RED));

    /**
     * Creates an image of few houses
     */
    @Test
    void street() {
        Camera camera = new Camera(new Point(2000, 100, 450), new Vector(-2, -0.1, -0.5), new Vector(-0.1, 2, 0))
                .setVPSize(200, 200)
                .setVPDistance(1000);

        int howManyHouses = 300;
        double houseSize = 30;

        Scene scene = new Scene("GroveStreet").setBackground(new Color(2, 25, 60));

        // ****geometries start

        // *group House
        Vector up = new Vector(0, 1, 0);
        Vector to = new Vector(-1, 0, -0.05);
        Vector right = new Vector(0, 0, -1);

        List<House> houses = new LinkedList<>();
        Point housesCenter = new Point(-30, -50, -140).add(camera.getVRight().scale(115)).add(to.scale(-500));



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

        for (int i = 1; i < howManyHouses * step; i += 4 * step) {
            streetLamps.add(new StreetLamp(housesCenter.add(right.scale(-100)).add(to.scale(houseSize / 2)).add(to.scale(houseSize * i)), 50,
                    new Color(YELLOW).scale(0.5), up, 1.25));
        }

        for (StreetLamp streetLamp : streetLamps) scene.addStreetLamp(streetLamp);

        //scene.lights.add(new DirectionalLight(new Color(GREEN), new Vector(0,-0.5,-1)));
        // ****lights end

        ImageWriter imageWriter = new ImageWriter("GroveStreet2", 500, 500);
        camera.setImageWriter(imageWriter)
                .setAntiAliasing(17)//
                .setThreadsCount(3)
                .setPrintInterval(1)
                .setRayTracer(new RayTracerBasic(scene)) //
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


    @Test
    void carImage(){
        Scene scene = new Scene("car")
                .setBackground(new Color(0,128,0));
        Camera camera = new Camera(new Point(0,0,2000),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(300,300)
                .setVPDistance(2000);
        scene = constructCar(scene, new Point(0,0,0),100,40,20,new Vector(1,0.5,-1).normalize(),new Vector(0,1,0.5).normalize(),new Color(BLACK).add(new Color(10,10,10)),new Material().setKd(1).setKs(0).setKr(0));


        scene.lights.add(new SpotLight(new Color(WHITE).add(new Color(YELLOW)).scale(0.5),camera.getPosition().add(camera.getVTo().scale(2000).add(camera.getVUp().scale(500))),camera.getVTo().add(camera.getVUp().scale(-0.5))));
        //scene.geometries.add(new Plane(new Point(0,0,-50),new Vector(0,0,1))
        //        .setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(30)));

        camera.setImageWriter(new ImageWriter("Car", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                //.setAntiAliasing(4)
                //.setThreadsCount(2)
                .renderImage() //
                .writeToImage(); //
    }
    Scene constructCar(Scene scene,Point location, double length, double width, double height, Vector vTo, Vector vUp, Color color,Material material) {
        if (vTo.dotProduct(vUp) != 0) throw new IllegalArgumentException();
        Geometries car = new Geometries();

        Vector vRight = vTo.crossProduct(vUp).normalize();


        // Body of the car
        Box body = new Box(location,length,width,height,vUp,vRight,color,material);
        car.add(body.getBox());
        scene.geometries.add(car);

        // Headlights
        Point frontCenter = location.add(vTo.scale(length/2+length*0.01));
        Geometry rightHeadLightSphere = new Sphere(frontCenter.add(vRight.scale(width/2-width*0.1)),width/2*1/3)
                .setMaterial(new Material().setKt(0.6)).setEmission(new Color(YELLOW).add(new Color(WHITE)));
        LightSource rightHeadLight= new SpotLight(new Color(YELLOW).add(new Color(WHITE)),frontCenter.add(vRight.scale(width/2-width*0.1)),vTo)
                .setKl(0.000001).setKq(0.0000001);
        car.add(rightHeadLightSphere);
        scene.lights.add(rightHeadLight);

        Geometry leftHeadLightSphere = new Sphere(frontCenter.add(vRight.scale(-width/2+width*0.1)),width/2*1/3)
                .setMaterial(new Material().setKt(0.6)).setEmission(new Color(YELLOW).add(new Color(WHITE)));
        LightSource leftHeadLight= new SpotLight(new Color(YELLOW).add(new Color(WHITE)),frontCenter.add(vRight.scale(-width/2+width*0.1)),vTo)
                .setKl(0.000001).setKq(0.0000001);
        car.add(leftHeadLightSphere);
        scene.lights.add(leftHeadLight);


        // Taillights
        Point backCenter = location.add(vTo.scale(-length/2-length*0.01));
        Geometry rightTailLightSphere = new Sphere(backCenter.add(vRight.scale(width/2-width*0.1)),width/2*1/3)
                .setMaterial(new Material().setKt(0.6)).setEmission(new Color(RED));
        LightSource rightTailLight= new SpotLight(new Color(RED),backCenter.add(vRight.scale(width/2-width*0.1)),vTo.scale(-1))
                .setKl(0.000001).setKq(0.0000001);
        car.add(rightTailLightSphere);
        scene.lights.add(rightTailLight);

        Geometry leftTailLightSphere = new Sphere(backCenter.add(vRight.scale(-width/2+width*0.1)),width/2*1/3)
                .setMaterial(new Material().setKt(0.6)).setEmission(new Color(RED));
        LightSource leftTailLight= new SpotLight(new Color(RED),backCenter.add(vRight.scale(-width/2+width*0.1)),vTo.scale(-1))
                .setKl(0.000001).setKq(0.0000001);
        car.add(leftTailLightSphere);
        scene.lights.add(leftTailLight);


        // Wheels
        Point frontCenterWheel = location.add(vTo.scale(length/2*4/5)).add(vUp.scale(-height/2-height/6));
        Geometry rightFrontWheel = new Sphere(frontCenterWheel.add(vRight.scale(width/4*1.5)), width/5)
                .setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.4).setKs(0.4));
        scene.geometries.add(rightFrontWheel);
        Geometry leftFrontWheel = new Sphere(frontCenterWheel.add(vRight.scale(-width/4*1.5)), width/5)
                .setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.4).setKs(0.4));
        scene.geometries.add(leftFrontWheel);

        Point backCenterWheel = location.add(vTo.scale(-length/2*4/5)).add(vUp.scale(-height/2-height/6));
        Geometry rightBackWheel = new Sphere(backCenterWheel.add(vRight.scale(width/4*1.5)), width/5)
                .setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.4).setKs(0.4));
        scene.geometries.add(rightBackWheel);
        Geometry leftBackWheel = new Sphere(backCenterWheel.add(vRight.scale(-width/4*1.5)), width/5)
                .setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.4).setKs(0.4));
        scene.geometries.add(leftBackWheel);

        // windows
        Point topCenter = location.add(vUp.scale(height/2));

        Point rightFrontBottomWindow = topCenter.add(vRight.scale(width/2)).add(vTo.scale(length/3.5));
        Point leftFrontBottomWindow = topCenter.add(vRight.scale(-width/2)).add(vTo.scale(length/3.5));
        Point rightBackBottomWindow = topCenter.add(vRight.scale(width/2)).add(vTo.scale(-length/3.5));
        Point leftBackBottomWindow = topCenter.add(vRight.scale(-width/2)).add(vTo.scale(-length/3.5));

        Geometry rightFront = new Cylinder(new Ray(rightFrontBottomWindow,vUp), width/100, height)
                .setEmission(new Color(GRAY));
        scene.geometries.add(rightFront);

        Geometry leftFront = new Cylinder(new Ray(leftFrontBottomWindow,vUp), width/100, height)
                .setEmission(new Color(GRAY));
        scene.geometries.add(leftFront);

        Geometry rightBack = new Cylinder(new Ray(rightBackBottomWindow,vUp), width/100, height)
                .setEmission(new Color(GRAY));
        scene.geometries.add(rightBack);

        Geometry leftBack = new Cylinder(new Ray(leftBackBottomWindow,vUp), width/100, height)
                .setEmission(new Color(GRAY));
        scene.geometries.add(leftBack);

        Point rightFrontTopWindow = rightFrontBottomWindow.add(vUp.scale(height));
        Point leftFrontTopWindow = leftFrontBottomWindow.add(vUp.scale(height));
        Point rightBackTopWindow = rightBackBottomWindow.add(vUp.scale(height));
        Point leftBackTopWindow = leftBackBottomWindow.add(vUp.scale(height));

        Geometry rightWindow = new Polygon(rightFrontTopWindow,rightBackTopWindow, rightBackBottomWindow,rightFrontBottomWindow)
                .setMaterial(new Material().setKt(0.8));
        scene.geometries.add(rightWindow);

        Geometry leftWindow = new Polygon(leftFrontTopWindow,leftBackTopWindow, leftBackBottomWindow,leftFrontBottomWindow)
                .setMaterial(new Material().setKt(0.8));
        scene.geometries.add(leftWindow);

        Geometry frontWindow = new Polygon(rightFrontBottomWindow, rightFrontTopWindow, leftFrontTopWindow,leftFrontBottomWindow)
                .setMaterial(new Material().setKt(0.8));
        scene.geometries.add(frontWindow);

        Geometry backWindow = new Polygon(rightBackBottomWindow, rightBackTopWindow, leftBackTopWindow,leftBackBottomWindow)
                .setMaterial(new Material().setKt(0.8));
        scene.geometries.add(backWindow);

        Geometry roof = new Polygon(leftBackTopWindow,rightBackTopWindow,rightFrontTopWindow,leftFrontTopWindow)
                .setEmission(color).setMaterial(material);
        scene.geometries.add(roof);

        ///NEED TO FINISH
        return scene;

    }

}


