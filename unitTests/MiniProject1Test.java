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
import static java.awt.Color.magenta;

/**
 * Created several images, with and without anti-aliasing
 */
public class MiniProject1Test {

    private final Color purple = new Color(BLUE).add(new Color(RED));


    /**
     * Produce a picture of a sphere lighted by a directional light *with* anti-aliasing
     */
    @Test
    public void sphereDirectionalAA() {
        Scene scene1 = new Scene("Test scene");

        Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000);

        Geometry sphere = new Sphere(new Point(0, 0, -50), 50d)
                .setEmission(new Color(BLUE).reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

        Color spCL = new Color(800, 500, 0); // Sphere test Color of Light


        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectionalAA", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .setAntiAliasing(33)
                .renderImage() //
                .writeToImage(); //
    }


    /**
     * Produce a picture of a sphere lighted by a spot light using anti aliasing
     */
    @Test
    public void twoSpheresAA() {
        Scene scene = new Scene("Test scene");

        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheresAA", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .setAntiAliasing(33)
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow and anti aliasing
     */
    @Test
    public void trianglesTransparentSphereAA() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadowAA", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .setAntiAliasing(33)
                .renderImage() //
                .writeToImage();
    }

    /**
     * Creates an image of sea *with* anti-aliasing
     */
    @Test
    void seaBottomImageAA() {
        Camera camera = new Camera(new Point(0, -100, -1000), new Vector(0, 160, 1000), new Vector(0, 1000, -160))
                .setVPSize(160, 160) // 16x9 scaled by 20
                .setVPDistance(1000);

        Scene scene = new Scene("Chess..?").setBackground(new Color(135, 206, 235));
        //.setAmbientLight(new AmbientLight(new Color(249,215,28),new Double3(0.5)))

        scene.geometries.add(new Triangle(new Point(30,60,40),new Point(20,75,40),new Point(10,60,40))
                .setEmission(new Color(WHITE))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(20, 50, 40), new Vector(0, 1, 0)), 0.5, 30)
                .setEmission(new Color(192,192,192))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(0, 50, 40), new Vector(1, 0, 0)), 2, 40)
                .setEmission(new Color(202,164,114))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Sphere(new Point(0, 0, 40), 8)
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKt(0.5))
                .setEmission(new Color(GREEN).reduce(2)));

        scene.geometries.add(new Polygon(new Point(-100, 30, 45), new Point(100, 30, 45),
                new Point(100, -40, 45), new Point(-100, -40, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40))
                .setEmission(new Color(242,217,181)
                ));
        scene.geometries.add(new Polygon(new Point(-100, 100, 45), new Point(100, 100, 45),
                new Point(100, 30, 45), new Point(-100, 30, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.3))
                .setEmission(new Color(0,105,148)
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

        scene.lights.add(new DirectionalLight(new Color(YELLOW).scale(0.15), new Vector(1,-1,100)));

        scene.lights.add(new SpotLight(new Color(magenta), new Point(100, 100, 0), new Vector(-1, -1, 1))
                .setNarrowBeam(2).setKl(0.00001).setKq(0.00001));

        scene.lights.add(new SpotLight(purple, new Point(-100, -100, 0), new Vector(1, 1, 1))
                .setNarrowBeam(2).setKl(0.00001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("seaBottomAA", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .setAntiAliasing(33)
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Creates an image of sea
     */
    @Test
    void seaImage() {
        Camera camera = new Camera(new Point(0, -100, -1000), new Vector(0, 160, 1000), new Vector(0, 1000, -160))
                .setVPSize(160, 160) // 16x9 scaled by 20
                .setVPDistance(1000);

        Scene scene = new Scene("Chess..?").setBackground(new Color(135, 206, 235));
        //.setAmbientLight(new AmbientLight(new Color(249,215,28),new Double3(0.5)))

        scene.geometries.add(new Triangle(new Point(30,60,40),new Point(20,75,40),new Point(10,60,40))
                .setEmission(new Color(WHITE))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(20, 50, 40), new Vector(0, 1, 0)), 0.5, 30)
                .setEmission(new Color(192,192,192))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Cylinder(new Ray(new Point(0, 50, 40), new Vector(1, 0, 0)), 2, 40)
                .setEmission(new Color(202,164,114))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));

        scene.geometries.add(new Sphere(new Point(0, 0, 40), 8)
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKt(0.5))
                .setEmission(new Color(GREEN).reduce(2)));

        scene.geometries.add(new Polygon(new Point(-100, 30, 45), new Point(100, 30, 45),
                new Point(100, -40, 45), new Point(-100, -40, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40))
                .setEmission(new Color(242,217,181)
                ));
        scene.geometries.add(new Polygon(new Point(-100, 100, 45), new Point(100, 100, 45),
                new Point(100, 30, 45), new Point(-100, 30, 45))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40).setKr(0.3))
                .setEmission(new Color(0,105,148)
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

        scene.lights.add(new DirectionalLight(new Color(YELLOW).scale(0.15), new Vector(1,-1,100)));

        scene.lights.add(new SpotLight(new Color(magenta), new Point(100, 100, 0), new Vector(-1, -1, 1))
                .setNarrowBeam(2).setKl(0.00001).setKq(0.00001));

        scene.lights.add(new SpotLight(purple, new Point(-100, -100, 0), new Vector(1, 1, 1))
                .setNarrowBeam(2).setKl(0.00001).setKq(0.00001));

        ImageWriter imageWriter = new ImageWriter("seaBottom", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}
