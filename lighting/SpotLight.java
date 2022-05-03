package lighting;

import primitives.*;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight {
    private final Vector direction;
    private int beam = 1;

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
        double v = this.direction.dotProduct(this.getL(p));
        if (alignZero(v) <= 0) return Color.BLACK;
        return super.getIntensity(p).scale(this.beam == 1 ? v : Math.pow(v, this.beam));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    public PointLight setNarrowBeam(int i) {
        // i is to compute (cos(angle))^i to get narrower beam
        this.beam = i;
        return this;
    }
}
