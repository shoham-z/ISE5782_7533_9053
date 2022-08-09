import geometries.Sphere;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;


public class vidTest {
    @Test
    void sphereVid() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1d, 1d, 1d))) //
                .setBackground(new Color(75, 127, 90));

        Sphere sphere = new Sphere(new Point(0d, 0d, 0d), 25);
        scene.geometries.add(sphere);


        Camera camera = new Camera(new Point(-250, 0, -500), new Vector(0, 0, 1), new Vector(0, 1, 0)) //

                .setVPDistance(500) //
                .setVPSize(300, 300) //
                .setImageWriter(new ImageWriter("base", 500, 500))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(BLUE).add(new Color(RED)));
        camera.writeToImage();
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            camera.moveCam(new Vector(50, 0, 0))
                    .setImageWriter(new ImageWriter("baseMoved" + (i + 1), 500, 500))
                    .renderImage()
                    .printGrid(100, new Color(BLUE).add(new Color(RED)))
                    .writeToImage();
        }
    }
}
