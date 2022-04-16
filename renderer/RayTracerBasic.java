package renderer;

import primitives.*;

import scene.Scene;

import java.util.List;

/**
 * Class to trace rays and find the pixel's color
 */
public class RayTracerBasic extends RayTracerBase{


    /**
     * constructor for RayTracerBase class
     *
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = this.scene.geometries.findIntersections(ray);
        if(intersections==null) return this.scene.background;
        Point closest = ray.findClosestPoint(intersections);
        return calcColor(closest);
    }

    private Color calcColor(Point point){
        return this.scene.ambientLight.getIntensity();
    }
}
