package ComplexObjects;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Sphere;
import lighting.LightSource;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;

import static java.awt.Color.GRAY;
import static java.awt.Color.white;

/**
 * Internal class to represent street lamps
 */
public class StreetLamp{
    private Geometry pole;
    private Geometry sphere;
    private LightSource light;

    /**
     * Constructor for street lamp
     * @param point Position of the base
     * @param height Height of the light
     * @param color Color of the light
     * @param up Up direction
     * @param radius Radius of poles
     */
    public StreetLamp(Point point, double height, Color color, Vector up, double radius){

        this.pole = new Cylinder(new Ray(point,up), radius, height).setEmission(new Color(GRAY).scale(1.25)).setMaterial(new Material().setKt(0.3));

        Point lightPosition = point.add(up.normalize().scale(height + radius*3));

        this.sphere = new Sphere(lightPosition, radius*3).setEmission(color).setMaterial(new Material().setKt(1));

        this.light = new PointLight(color,lightPosition).setKl(0.00001).setKq(0.00001);
    }

    /**
     * Getter for the body of the lamp
     * @return Body of the lamp
     */
    public Geometries getBody(){
        return new Geometries(this.pole, this.sphere);
    }

    /**
     * Getter for the light
     * @return The light
     */
    public LightSource getLight(){
        return this.light;
    }
}
