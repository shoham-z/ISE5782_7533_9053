import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;

/**
 * Testing Camera Class
 */
class ImageWriterTest {
    /**
     * Basic test method for image writer
     */
    @Test
    void BasicImageTest() {
        ImageWriter imageWriter = new ImageWriter("basicGrid", 800, 500);
        Color red = new Color(255, 0, 0);
        Color yellow = new Color(255, 255, 0);
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(j, i, red);
                } else {
                    imageWriter.writePixel(j, i, yellow);
                }
            }
        }
        imageWriter.writeToImage();
    }
}