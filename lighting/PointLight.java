package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource {
    protected Point position;
    private double kC;
    private double kL;
    private double kQ;

    /**
     * constructor for point light
     *
     * @param intensity the light intensity
     * @param position  position of light
     * @param kC        constant attenuation with distance
     * @param kL        linear attenuation with distance
     * @param kQ        quadratic attenuation with distance
     */
    public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    /**
     * constructor for the light
     *
     * @param intensity the light intensity
     * @param position  position of light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    /**
     * Setter for position
     * @param position the position
     * @return the point light
     */
    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }

    /**
     * Setter for constant attenuation
     * @param kC the constant attenuation
     * @return the point light
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for linear attenuation
     * @param kL the linear attenuation
     * @return the point light
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for quadratic attenuation
     * @param kQ the quadratic attenuation
     * @return the point light
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance = this.position.distance(p);
        return this.getIntensity().reduce(this.kC + this.kL * distance + this.kQ * distance * distance);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position);
    }

}
