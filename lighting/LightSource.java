package lighting;


import primitives.*;


/**
 * This class represents a light source in the 3D cartesian coordinate system
 */
public interface LightSource {

    /**
     * returns the intensity of the light source at the given point
     * @param p the point
     * @return the intensity
     */
    public Color getIntensity(Point p);

    /**
     * returns the direction of the light source to the given point
     * @param p the point
     * @return the direction
     */
    public Vector getL(Point p);


}
