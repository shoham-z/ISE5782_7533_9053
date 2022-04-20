package lighting;

import primitives.*;

public class SpotLight extends PointLight{
    private Vector direction;

    /**
     * constructor for point light
     *
     * @param intensity the light intensity
     * @param position position of light
     * @param kC constant attenuation with distance
     * @param kL linear attenuation with distance
     * @param kQ quadratic attenuation with distance
     * @param direction the light direction
     */
    public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalize();
    }

    /**
     * constructor for point light
     *
     * @param spCL the light color
     * @param position position of light
     * @param dir the light direction
     */
    public SpotLight(Color spCL, Point position, Vector dir) {
        super(spCL, position);
        this.direction = dir;
    }

    @Override
    public Color getIntensity(Point p) {
        double v = direction.dotProduct(p.subtract(this.position));
        if(v<=0)return new Color(0,0,0);
        return super.getIntensity(p).scale(v);
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }
}
