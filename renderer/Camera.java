package renderer;

import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.*;

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
    private SuperSampling antiAliasing = new SuperSampling();
    int threadsCount = 1;
    long printInterval = 1000l;

    /**
     * Setter for number of threads
     * @param threadsCount Number of desired threads
     * @return This camera object
     */
    public Camera setThreadsCount(int threadsCount) {
        this.threadsCount = threadsCount;
        return this;
    }

    /**
     * Setter for print interval
     * @param printInterval The print interval
     * @return This camera object
     */

    public Camera setPrintInterval(long printInterval) {
        this.printInterval = printInterval;
        return this;
    }

    /**
     * Setter for antiAliasingGrid
     *
     * @param amount the number of rays
     * @return this camera object
     */
    public Camera setAntiAliasing(int amount) {
        this.antiAliasing.setSize(amount);
        return this;
    }

    /**
     * Setter for imageWriter
     *
     * @param imageWriter the imageWriter object
     * @return this camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Setter for rayTracer
     *
     * @param rayTracer the rayTracer object
     * @return this camera object
     */
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
    public Vector getVTo() {
        return this.vTo;
    }

    /**
     * Getter for the vector that point up from camera
     *
     * @return the vector
     */
    public Vector getVUp() {
        return this.vUp;
    }

    /**
     * Getter for the vector that point right from camera
     *
     * @return the vector
     */
    public Vector getVRight() {
        return this.vRight;
    }

    /**
     * Getter for the VP height
     *
     * @return the height
     */
    public int getVPHeight() {
        return this.vpHeight;
    }

    /**
     * Getter for the VP width
     *
     * @return the width
     */
    public int getVPWidth() {
        return this.vpWidth;
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
    public List<Ray> constructRay(int nX, int nY, int j, int i) {
        Point pIJ = this.position.add(this.vTo.scale(this.distanceFromVp));
        double sizeOfX = (double) this.vpWidth / nX;
        double sizeOfY = (double) this.vpHeight / nY;

        double xJ = (j - ((nX - 1) / 2d)) * sizeOfX;
        double yI = (((nY - 1) / 2d) - i) * sizeOfY;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));

        return this.antiAliasing.constructRaysThroughGrid(sizeOfY, sizeOfX, this.position, pIJ, this.vUp, this.vRight);
    }


    /**
     * Renders the image
     *
     * @return the camera
     */
    public Camera renderImage() {
        if (this.vpHeight <= 0 || this.vpWidth <= 0 || this.distanceFromVp <= 0 || this.imageWriter == null || this.rayTracer == null) {
            throw new MissingResourceException("missing resource", "Camera", "");
        }

        int yPixels = this.imageWriter.getNy();
        int xPixels = this.imageWriter.getNx();

        Pixel.initialize(yPixels, xPixels, printInterval);
        IntStream.range(0, yPixels).parallel().forEach(i -> {
            IntStream.range(0, xPixels).parallel().forEach(j -> {
                this.imageWriter.writePixel(j,i,averageColor(xPixels, yPixels, j, i));
                Pixel.pixelDone();
                Pixel.printPixel();
            });
        });


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
        int yPixels = this.imageWriter.getNy();
        int xPixels = this.imageWriter.getNx();
        for (int i = 0; i < xPixels; i++) {
            for (int j = 0; j < yPixels; j++) {
                if (i % interval == 0 || j % interval == 0) this.imageWriter.writePixel(i, j, color);
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

    /**
     * Calculates the average Color of the pixel
     * @param xPixels Number of total pixels on x-axis
     * @param yPixels Number of total pixels on y-axis
     * @param j The x-axis index of the color
     * @param i The y-axis index of the color
     * @return The average color
     */

    public Color averageColor(int xPixels, int yPixels, int j, int i) {
        List<Ray> rayList = this.constructRay(xPixels, yPixels, j, i);
        Color myColor = Color.BLACK;
        for (Ray ray : rayList) {
            myColor = myColor.add(this.rayTracer.traceRay(ray));
        }
        return myColor.reduce(rayList.size());
    }

}
