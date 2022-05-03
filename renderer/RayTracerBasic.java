package renderer;

import geometries.Triangle;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class to trace rays and find the pixel's color
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constant to move the point by a small distance
     */
    private static final double DELTA = 0.1;

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
        var intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return this.scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * calculates the color of the pixel using ray and GeoPoint
     *
     * @param gp  tuple of the geometry and a point on the geometry
     * @param ray the ray
     * @return the color of the pixel
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return this.scene.ambientLight.getIntensity()
                .add(calcLocalEffects(gp, ray));
    }

    /**
     * Calculates the local effects (diffusion, specular) at a point and returns the color
     *
     * @param gp  the GeoPoint at the pixel
     * @param ray the ray at the pixel
     * @return the color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : this.scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                if (unshaded(gp, lightSource, l, n, nv)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive effects at a pixel
     *
     * @param material the material of the Geometry
     * @param nl       dot product of the normal of the geometry and the light direction
     * @return the diffusive effects
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular effects at a pixel
     *
     * @param material the material of the Geometry
     * @param n        the normal vector to the geometry
     * @param l        the light direction vector
     * @param nl       dot product of the normal of the geometry and the light direction
     * @param v        the vector from the camera to the point
     * @return the specular effects
     */
    public Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector minusR = n.scale(2 * nl).subtract(l);
        double minusVR = minusR.dotProduct(v);
        if (minusVR <= 0) return Double3.ZERO;
        return material.kS.scale(Math.pow(minusVR, material.nShininess));
    }

    /**
     * Checks if a point on the geometry is unshaded
     *
     * @param gp the geometry and the point
     * @param l  the light direction
     * @param n  the geometry normal at the point
     * @param nv dot product of n and ray's vector
     * @return is point unshaded
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nv)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector deltaVector = n.scale(nv < 0 ? this.DELTA : -this.DELTA);
        Point point = gp.point.add(deltaVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));

        return intersections == null;
    }
}
