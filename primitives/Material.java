package primitives;

/**
 * Material class represents a material
 */
public class Material {
    /**
     * The diffusive factor of the material
     */
    public Double3 kD = Double3.ZERO;
    /**
     * The specular factor of the material
     */
    public Double3 kS = Double3.ZERO;
    /**
     * The shininess factor of the material
     */
    public int nShininess = 0;

    /**
     * The reflective factor of the material
     */
    public Double3 kR = Double3.ZERO;
    /**
     * The transparency factor of the material
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Setter for reflective factor
     *
     * @param kR the reflective factor
     * @return the Material
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Setter for transparency factor
     *
     * @param kT the transparency factor
     * @return the Material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for attenuation factor
     *
     * @param kD the attenuation factor
     * @return the Material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for attenuation factor
     *
     * @param kD the attenuation factor
     * @return the Material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD, kD, kD);
        return this;
    }

    /**
     * Setter for shininess factor
     *
     * @param kS the shininess factor
     * @return the Material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for attenuation factor
     *
     * @param kS the attenuation factor
     * @return the Material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS, kS, kS);
        return this;
    }

    /**
     * Setter for shininess factor
     *
     * @param nShininess the shininess factor
     * @return the Material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
