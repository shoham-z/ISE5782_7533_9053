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
    private static final int MAX_CALC_COLOR_LEVEL = 20;
    private static final double MIN_CALC_COLOR_K = 0.001;


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
     * @param ray      The ray
     * @return The color of the pixel
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color of the pixel using ray and GeoPoint
     *
     * @param gp    Tuple of the geometry and a point on the geometry
     * @param ray   The ray
     * @param level Level of additional rays to cast
     * @param k     The initial attenuation factor
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
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the addition to the color caused be reflection and transparency
     *
     * @param gp    The geometry and point on it
     * @param v     The direction that
     * @param level The level of additional rays
     * @param k     The initial color
     * @return The additional color
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = k.product(material.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    /**
     * @param ray   The ray to intersect with
     * @param level Level of additional rays to cast
     * @param kx    A constant to scale by
     * @param kkx   The new initial color
     * @return The additional color
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
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
     * Calculates if a point on the geometry is transparent
     *
     * @param gp the geometry and the point
     * @param l  the light direction
     * @param n  the geometry normal at the point
     * @return is point transparent
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        List<GeoPoint> intersections = scene.geometries
                .findGeoIntersections(constructRefractedRay(gp.point, lightDirection, n), light.getDistance(gp.point));
        if(intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;

        for (GeoPoint intersection : intersections) {
            if(alignZero(intersection.point.distance(gp.point) - light.getDistance(gp.point)) <= 0) {
                ktr = intersection.geometry.getMaterial().kT.product(ktr);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }

        return ktr;
    }


    /**
     * Constructs reflected ray
     *
     * @param point The point on the geometry where the ray intersects
     * @param n     The normal to the geometry at the point
     * @param v     The direction vector of the ray intersected the geometry
     * @return The ray reflecting from the point
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        double nv = n.dotProduct(v);
        return new Ray(point, v.subtract(n.scale(2 * nv)), n);
    }

    /**
     * Constructs refracted ray
     *
     * @param point The point on the geometry where the ray intersects
     * @param n     The normal to the geometry at the point
     * @param v     The direction vector of the ray intersected the geometry
     * @return The ray refracting from the point
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * Finds the closest geometry and point that the ray intersects with
     *
     * @param ray The ray to intersect with
     * @return The closest geometry and point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        return (intersections == null) ? null : ray.findClosestGeoPoint(intersections);
    }


}
