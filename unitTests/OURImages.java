import geometries.Cylinder;
import geometries.Geometry;
import geometries.Plane;
import geometries.Tube;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
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

    private Geometry tube = new Tube(new Ray(new Point(0,0,-40),new Vector(0,1,0)),30)
            .setEmission(new Color(GREEN).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300).setKt(0.5));

    private final Color purple = new Color(BLUE).add(new Color(RED));

    private Geometry cylinder = new Cylinder(new Ray(new Point(-30,-20,-10),new Vector(1,1,1)),13, 100)
            .setEmission(purple.reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

    private Geometry plane = new Plane(new Point(0,0,-80),new Vector(1,0,-1))
            .setEmission(new Color(GRAY))
            .setMaterial(new Material().setKr(1));

    /**
     * This test method creates and image with a cylinder and a tube
     */
    @Test
    void cylinderTubeImage(){
        scene1.geometries.add(cylinder);
        scene1.lights.add(new DirectionalLight(new Color(GREEN).scale(2), new Vector(-0.9,-0.9,-1)));

        ImageWriter imageWriter = new ImageWriter("CylinderDirectional", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }
}
