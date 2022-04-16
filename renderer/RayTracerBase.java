package renderer;

import primitives.*;
import scene.Scene;

/**
 * Class with methods to trace rays
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * constructor for RayTracerBase class
     * @param scene the scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * traces a ray to find the pixel's color
     * @param ray the ray
     * @return the color
     */
    public abstract Color traceRay(Ray ray);
}
