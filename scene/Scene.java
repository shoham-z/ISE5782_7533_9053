package scene;

import ComplexObjects.StreetLamp;
import lighting.*;
import geometries.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a scene
 */
public class Scene {
    /**
     * The general name of the scene
     */
    public final String name;
    /**
     * The background of the scene
     */
    public Color background = Color.BLACK;
    /**
     * The ambient light of the scene
     */
    public AmbientLight ambientLight = new AmbientLight();
    /**
     * The geometries in the scene
     */
    public Geometries geometries = new Geometries();
    /**
     * The light sources in the scene
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * setter for the Light sources
     * @param lights the lights
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Constructor for Scene
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Setter for the background of scene
     *
     * @param background the background
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter for the ambient light of scene
     *
     * @param ambientLight the ambient light
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter for the geometries in the scene
     *
     * @param geometries the geometries
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     *
     */
    public void addStreetLamp(StreetLamp streetLamp){
        this.geometries.add(streetLamp.getBody());
        this.lights.add(streetLamp.getLight());
    }
}
