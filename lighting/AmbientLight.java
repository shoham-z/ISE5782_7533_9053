package lighting;

import primitives.*;

/**
 * AmbientLight class represents ambient light in the image
 */
public class AmbientLight extends Light {

    /**
     * Constructor for AmbientLight
     *
     * @param iA light intensity
     * @param kA factor of how much the light reduces as distance grows
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * Default constructor for AmbientLight
     * sets the background color as black
     */
    public AmbientLight() {
         super(Color.BLACK);
    }

    /**
     * Getter for the color intensity
     *
     * @return the color intensity
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
