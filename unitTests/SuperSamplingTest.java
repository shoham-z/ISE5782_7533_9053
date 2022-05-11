import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.SuperSampling;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuperSamplingTest {
    SuperSampling superSampling= new SuperSampling();

    /**
     * Test method for {@link renderer.SuperSampling#constructRaysThroughGrid(double, double, Point, Point, Vector, Vector)}
     */
    @Test
    void testConstructRaysThroughGrid(){
        Point source = new Point(0,0,-1);
        Point gridCenter = new Point(0,0,1);

        Vector vUp = new Vector(0,1,0);
        Vector vRight = new Vector(-1,0,0);
        // EP01: Grid is even length
        double height1 = 2;
        double width1 = 2;
        superSampling.setSize(2);
        List<Ray> expected1 = List.of(new Ray(new Point(0,0,-1), new Vector(0.5,-0.5,2)),
                new Ray(new Point(0,0,-1), new Vector(-0.5,-0.5,2)),
                new Ray(new Point(0,0,-1), new Vector(0.5,0.5,2)),
                new Ray(new Point(0,0,-1), new Vector(-0.5,0.5,2)));

        List<Ray> actual1 = superSampling.constructRaysThroughGrid(height1,width1,source,gridCenter,vUp,vRight);
        assertEquals(expected1,actual1, "EP01: Constructing rays is bad");

        // EP01: Grid is odd length
        double height2 = 3;
        double width2 = 3;
        superSampling.setSize(3);
        List<Ray> expected2 = List.of(new Ray(new Point(0,0,-1), new Vector(1,-1,2)),
                new Ray(new Point(0,0,-1), new Vector(0,-1,2)),
                new Ray(new Point(0,0,-1), new Vector(-1,-1,2)),
                new Ray(new Point(0,0,-1), new Vector(1,0,2)),
                new Ray(new Point(0,0,-1), new Vector(0,0,2)),
                new Ray(new Point(0,0,-1), new Vector(-1,0,2)),
                new Ray(new Point(0,0,-1), new Vector(1,1,2)),
                new Ray(new Point(0,0,-1), new Vector(0,1,2)),
                new Ray(new Point(0,0,-1), new Vector(-1,1,2)));

        List<Ray> actual2 = superSampling.constructRaysThroughGrid(height2,width2,source,gridCenter,vUp,vRight);
        assertEquals(expected2,actual2, "EP02: Constructing rays is bad");

    }
}
