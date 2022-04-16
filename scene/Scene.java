package scene;

import lighting.*;
import geometries.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a scene
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight;
    public Geometries geometries = new Geometries();
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

    public Scene(String name) {
        this.name = name;
    }

    /**
     * Setter for the name of scene
     *
     * @param name the name
     * @return the scene
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
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
}
