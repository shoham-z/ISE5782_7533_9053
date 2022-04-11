package scene;

import elements.*;
import geometries.*;
import primitives.*;

/**
 * Scene class represents a scene
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries = new Geometries();

    public Scene(String name) {
        this.name = name;
    }

    /**
     * Setter for the name of scene
     *
     * @param name the name
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Setter for the background of scene
     *
     * @param background the background
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter for the ambient light of scene
     *
     * @param ambientLight the ambient light
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter for the geometries in the scene
     *
     * @param geometries the geometries
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
