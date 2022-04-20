package renderer;

import primitives.*;

import scene.Scene;

import geometries.Intersectable.GeoPoint;
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
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    private Color calcColor(GeoPoint gp) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission());
    }
}
