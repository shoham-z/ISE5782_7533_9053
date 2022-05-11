package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

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

    public List<Ray> constructRaysThroughGrid(double height, double width, Point source,Point gridCenter, Vector vUp, Vector vRight){
        List<Ray> rays = new LinkedList<>();
        double xJ = -width/(2*this.size)-(width/2);
        double yI = -height/(2*this.size)-(height/2);
        for (int i = 0; i < this.size; i++) {
            yI+=height/this.size;
            if (yI>(height/2))
                yI=-height/(2*this.size);
            for (int j = 0; j < this.size; j++) {
                xJ+=width/this.size;
                if (xJ>(width/2))
                    xJ=-width/(2*this.size);
                if (xJ != 0) gridCenter = gridCenter.add(vRight.scale(xJ));
                if (yI != 0) gridCenter = gridCenter.add(vUp.scale(yI));
                rays.add(new Ray(source, gridCenter.subtract(source)));
            }
        }
        return rays;
    }

}
