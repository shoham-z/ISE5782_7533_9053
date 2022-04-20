package lighting;

import primitives.*;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * constructor for the light
     *
     * @param intensity the light intensity
     * @param direction direction of light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }


}
