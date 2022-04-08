package renderer;


import primitives.*;

/**
 * Camera class represents a 3D camera Which will be the "eye" to render the photo.
 */
public class Camera {
    private Point position;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double vpHeight;
    private double vpWidth;
    private double distanceFromVp;

    /**
     * Getter for the camera position
     * @return the position
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Getter for the vector that point the same direction as camera
     * @return the vector
     */
    public Vector getvTo() {
        return this.vTo;
    }

    /**
     * Getter for the vector that point up from camera
     * @return the vector
     */
    public Vector getvUp() {
        return this.vUp;
    }

    /**
     * Getter for the vector that point right from camera
     * @return the vector
     */
    public Vector getvRight() {
        return this.vRight;
    }

    /**
     * Getter for the VP height
     * @return the height
     */
    public double getVpHeight() {
        return this.vpHeight;
    }

    /**
     * Getter for the VP width
     * @return the width
     */
    public double getVpWidth() {
        return this.vpWidth;
    }

    /**
     * Getter for the distance between camera and VP
     * @return the distance
     */
    public double getDistanceFromVp() {
        return this.distanceFromVp;
    }

    /**
     * Constructor for camera
     * @param to the vector in camera direction
     * @param up the vector points up from camera
     * @param position position of camera
     */
    public Camera(Point position, Vector to, Vector up){
        if (to.dotProduct(up) != 0){
            throw new IllegalArgumentException();
        }
        this.position = position;
        this.vRight = to.crossProduct(up).normalize();
        this.vTo = to.normalize();
        this.vUp = up.normalize();
    }

    /**
     * Setter for VP width and height
     * @param width the width
     * @param height the height
     * @return This object
     */
    public Camera setVPSize(double width, double height){
        this.vpHeight = height;
        this.vpWidth = width;
        return this;
    }

    /**
     * Setter for distance between camera and VP
     * @param distance the distance
     * @return This object
     */
    public Camera setVPDistance(double distance){
        this.distanceFromVp = distance;
        return this;
    }

    /**
     * Constructs ray through pixel on VP
     * @param nX number of columns in VP
     * @param nY number of rows in VP
     * @param j column number of pixel
     * @param i row number of pixel
     * @return the ray
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

}
