package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

/**
 * Class to trace rays and find the pixel's color
 */
public class RayTracerBasic extends RayTracerBase {


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
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
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
        Vector r = n.scale(2 * nl).subtract(l);
        double vr = r.dotProduct(v);
        if (vr <= 0) return Double3.ZERO;
        return material.kS.scale(Math.pow(vr, material.nShininess));
    }

}
