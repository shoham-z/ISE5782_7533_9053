package lighting;

import primitives.*;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight {
    private Vector direction;

    /**
     * constructor for point light
     *
     * @param intensity the light intensity
     * @param position  position of light
     * @param kC        constant attenuation with distance
     * @param kL        linear attenuation with distance
     * @param kQ        quadratic attenuation with distance
     * @param direction the light direction
     */
    public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalize();
    }

    /**
     * constructor for point light
     *
     * @param spCL     the light color
     * @param position position of light
     * @param dir      the light direction
     */
    public SpotLight(Color spCL, Point position, Vector dir) {
        super(spCL, position);
        this.direction = dir.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double v = alignZero(this.direction.dotProduct(this.getL(p)));
        return v > 0 ? super.getIntensity(p).scale(v) : new Color(0, 0, 0);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    public PointLight setNarrowBeam(int i) {
        return this;
    }
}
