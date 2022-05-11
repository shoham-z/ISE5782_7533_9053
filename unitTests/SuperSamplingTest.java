import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.SuperSampling;

import java.util.List;

public class SuperSamplingTest {
    SuperSampling superSampling= new SuperSampling();

    /**
     * Test method for {@link renderer.SuperSampling#constructRaysThroughGrid(double, double, Point, Point, Vector, Vector)}
     */
    @Test
    void testConstructRaysThroughGrid(){
        Point source = new Point(0,0,-1);
        Point gridCenter = new Point(0,0,1);
        double height = 2;
        double width = 2;
        Vector vUp = new Vector(0,1,0);
        Vector vRight = new Vector(-1,0,0);
        superSampling.setSize(2);
        List<Ray> rays = superSampling.constructRaysThroughGrid(height,width,source,gridCenter,vUp,vRight);
        for (Ray ray:rays) {
            System.out.println(ray);
        }

    }
}
