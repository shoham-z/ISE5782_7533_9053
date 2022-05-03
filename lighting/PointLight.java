package lighting;

import primitives.*;

import static primitives.Util.alignZero;

public class PointLight extends Light implements LightSource {
    protected final Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * constructor for the light
     *
     * @param intensity the light intensity
     * @param position  position of light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Setter for constant attenuation
     *
     * @param kC the constant attenuation
     * @return the point light
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for linear attenuation
     *
     * @param kL the linear attenuation
     * @return the point light
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for quadratic attenuation
     *
     * @param kQ the quadratic attenuation
     * @return the point light
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double distanceSquared = this.position.distanceSquared(p);
        return this.intensity.scale(1d / (this.kC + this.kL * Math.sqrt(distanceSquared) + this.kQ * distanceSquared));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }
}
