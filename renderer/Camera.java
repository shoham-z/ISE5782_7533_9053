package renderer;

import java.util.MissingResourceException;

import primitives.*;

/**
 * Camera class represents a 3D camera Which will be the "eye" to render the photo.
 */
public class Camera {
    private Point position;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private int vpHeight;
    private int vpWidth;
    private double distanceFromVp;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Getter for the camera position
     *
     * @return the position
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Getter for the vector that point the same direction as camera
     *
     * @return the vector
     */
    public Vector getvTo() {
        return this.vTo;
    }

    /**
     * Getter for the vector that point up from camera
     *
     * @return the vector
     */
    public Vector getvUp() {
        return this.vUp;
    }

    /**
     * Getter for the vector that point right from camera
     *
     * @return the vector
     */
    public Vector getvRight() {
        return this.vRight;
    }

    /**
     * Getter for the VP height
     *
     * @return the height
     */
    public int getVpHeight() {
        return this.vpHeight;
    }

    /**
     * Getter for the VP width
     *
     * @return the width
     */
    public int getVpWidth() {
        return this.vpWidth;
    }

    /**
     * Getter for the distance between camera and VP
     *
     * @return the distance
     */
    public double getDistanceFromVp() {
        return this.distanceFromVp;
    }

    /**
     * Constructor for camera
     *
     * @param to       the vector in camera direction
     * @param up       the vector points up from camera
     * @param position position of camera
     */
    public Camera(Point position, Vector to, Vector up) {
        if (to.dotProduct(up) != 0) {
            throw new IllegalArgumentException();
        }
        this.position = position;
        this.vTo = to.normalize();
        this.vUp = up.normalize();
        // Cross product of 2 orthogonal unit vectors is always a unit vector
        this.vRight = this.vTo.crossProduct(this.vUp);
    }

    /**
     * Setter for VP width and height
     *
     * @param width  the width
     * @param height the height
     * @return This object
     */
    public Camera setVPSize(int height, int width) {
        this.vpHeight = height;
        this.vpWidth = width;
        return this;
    }

    /**
     * Setter for distance between camera and VP
     *
     * @param distance the distance
     * @return This object
     */
    public Camera setVPDistance(double distance) {
        this.distanceFromVp = distance;
        return this;
    }

    /**
     * Constructs ray through pixel on VP
     *
     * @param nX number of columns in VP (horizontal)
     * @param nY number of rows in VP (vertical)
     * @param j  column number of pixel (horizontal)
     * @param i  row number of pixel (vertical)
     * @return the ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pIJ = this.position.add(this.vTo.scale(this.distanceFromVp));
        double xJ = (j - ((nX - 1) / 2d)) * ((double) this.vpWidth / nX);
        double yI = (((nY - 1) / 2d) - i) * ((double) this.vpHeight / nY);
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        return new Ray(this.position, pIJ.subtract(this.position));
    }


    /**
     * Renders the image
     * @return the camera
     */
    public Camera renderImage() {
        if (this.vpHeight <= 0 || this.vpWidth <= 0 || !(this.distanceFromVp > 0) || this.imageWriter == null || this.rayTracer == null) {
            throw new MissingResourceException("missing resource", "Camera", "");
        }
        int yPixels = this.imageWriter.getNy();
        int xPixels = this.imageWriter.getNx();
        for (int i = 0; i < xPixels; i++) {
            for (int j = 0; j < yPixels; j++) {
                this.imageWriter.writePixel(i, j, this.rayTracer.traceRay(this.constructRay(xPixels, yPixels, i, j)));
            }
        }
        return this;
    }

    /**
     * prints the grid into the image with the color provided
     *
     * @param interval size of the space between 2 parallel grid lines
     * @param color    the color
     */
    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null) throw new MissingResourceException("missing imageWriter", "Camera", "");
        for (int i = 0; i < this.imageWriter.getNx(); i++) {
            for (int j = 0; j < this.imageWriter.getNx(); j++) {
                if(i%interval == 0 || j%interval == 0) this.imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Writes the image to the storage device
     */
    public void writeToImage() {
        if (this.imageWriter == null) throw new MissingResourceException("missing imageWriter", "Camera", "");
        this.imageWriter.writeToImage();
    }


}
