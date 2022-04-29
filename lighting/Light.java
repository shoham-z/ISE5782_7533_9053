package lighting;

import primitives.*;

/**
 * Class light represents light
 */
public abstract class Light {
    protected Color intensity;


    /**
     * constructor for the light
     *
     * @param intensity the light intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter for light intensity;
     *
     * @return the intensity
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
