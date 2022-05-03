import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import static java.awt.Color.*;

/**
 * Testing Camera Class
 */
class ImageWriterTest {
    /**
     * Basic test method for image writer
     */
    @Test
    void BasicImageTest() {
        int nX = 800;
        int nY = 500;
        ImageWriter imageWriter = new ImageWriter("basicGrid", nX, nY);
        Color red = new Color(RED);
        Color yellow = new Color(YELLOW);
        for (int i = 0; i < nY; i++)
            for (int j = 0; j < nX; j++)
                    imageWriter.writePixel(j, i, i % 50 == 0 || j % 50 == 0 ? red : yellow);
        imageWriter.writeToImage();
    }
}