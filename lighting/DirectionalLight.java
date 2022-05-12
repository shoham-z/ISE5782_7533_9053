package lighting;

import primitives.*;

public class DirectionalLight extends Light implements LightSource {
    private final Vector direction;

    /**
     * constructor for the light
     *
     * @param intensity the light intensity
     * @param direction direction of light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }

}
