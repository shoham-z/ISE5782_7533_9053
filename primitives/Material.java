package primitives;

public class Material {
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;

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
