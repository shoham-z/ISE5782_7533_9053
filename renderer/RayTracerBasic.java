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
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of the pixel using ray and GeoPoint
     *
     * @param geopoint Tuple of the geometry and a point on the geometry
     * @param ray The ray
     * @return The color of the pixel
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color of the pixel using ray and GeoPoint
     *
     * @param gp  Tuple of the geometry and a point on the geometry
     * @param ray The ray
     * @param level Level of additional rays to cast
     * @param k The initial attenuation factor
     * @return The color of the pixel
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray.getDirection(), level, k));
    }

    /**
     * Calculates the local effects (diffusion, specular) at a point and returns the color
     *
     * @param gp  the GeoPoint at the pixel
     * @param ray the ray at the pixel
     * @return the color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(gp,lightSource,l,n,nv);
                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     *
     * @param gp
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = k.product(material.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
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
    /*private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nv)
    {
        if(!gp.geometry.getMaterial().kT.equals(Double3.ZERO)) return false;
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector deltaVector = n.scale(nv < 0 ? this.DELTA : -this.DELTA);
        Point point = gp.point.add(deltaVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));

        return intersections == null;
    }*/

    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n, double nv)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector deltaVector = n.scale(nv < 0 ? this.DELTA : -this.DELTA);
        Point point = gp.point.add(deltaVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));

        if (intersections == null) return Double3.ONE;

        Double3 ktr = Double3.ONE;

        for (GeoPoint intersection: intersections ) {
            if (alignZero(intersection.point.distance(gp.point) - light.getDistance(gp.point)) < 0) {
                ktr = intersection.geometry.getMaterial().kT.product(ktr);
                if (ktr.equals(Double3.ZERO)) return ktr;
            }

        }

        return ktr;
    }


    /**
     * Constructs reflected ray
     * @param point The point on the geometry where the ray intersects
     * @param n The normal to the geometry at the point
     * @param v The direction vector of the ray intersected the geometry
     * @return The ray reflecting from the point
     */
    private Ray constructReflectedRay(Point point, Vector n, Vector v){
        double nv = n.dotProduct(v);
        return new Ray(point.add(n.scale(nv < 0 ? this.DELTA : -this.DELTA)), v.subtract(n.scale(2 * nv)));
    }

    /**
     * Constructs refracted ray
     * @param point The point on the geometry where the ray intersects
     * @param n The normal to the geometry at the point
     * @param v The direction vector of the ray intersected the geometry
     * @return The ray refracting from the point
     */
    private Ray constructRefractedRay(Point point, Vector n, Vector v){
        return new Ray(point.add(n.scale(n.dotProduct(v) < 0 ? this.DELTA : -this.DELTA)), v);
    }

    /**
     * Finds the closest geometry and point that the ray intersects with
     * @param ray The ray to intersect with
     * @return The closest geometry and point
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        return (intersections == null) ? null : ray.findClosestGeoPoint(intersections);
    }


}
