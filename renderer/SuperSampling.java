package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class represents super-sampling through rectangular grid
 */
public class SuperSampling{
    /**
     * Width\Height of the grid
     */
    public double size = 1;

    /**
     * Setter for size of super-sampling
     * @param size Width\Height of the grid
     * @return This SuperSampling object
     */
    public SuperSampling setSize(double size){
        this.size = size;
        return this;
    }

    /**
     *
     * @param height
     * @param width
     * @param source
     * @param gridCenter
     * @param vUp
     * @param vRight
     * @return
     */
    public List<Ray> constructRaysThroughGrid(double height, double width, Point source,Point gridCenter, Vector vUp, Vector vRight){
        List<Ray> rays = new LinkedList<>();
        double xJ;
        double yI = height/(2*this.size)-(height/2);
        Point destination;
        for (int i = 0; i < this.size; i++) {
            xJ = width/(2*this.size)-(width/2);
            for (int j = 0; j < this.size; j++) {
                destination = gridCenter;
                if (xJ != 0) destination = destination.add(vRight.scale(xJ));
                if (yI != 0) destination = destination.add(vUp.scale(yI));
                rays.add(new Ray(source, destination.subtract(source)));
                xJ=alignZero(xJ + width/this.size);
                if (xJ>(width/2))
                    xJ=-width/(2*this.size);
            }
            yI=alignZero(yI+height/this.size);
            if (yI>(height/2))
                yI=-height/(2*this.size);
        }
        return rays;
    }

}
