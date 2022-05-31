package ComplexObjects;

import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public class Box {
    private Geometries box;

    /**
     * Constructor for Cube
     * @param center Center point of the Cube
     * @param length Length of the Cube
     * @param width Width of the Cube
     * @param height Height of the Cube
     * @param vUp Up direction of the Cube
     * @param vRight Right direction of the Cube
     * @param color Color of the Cube
     * @param material Material of the Cube
     */
    public Box(Point center, double length,double width, double height, Vector vUp, Vector vRight, Color color, Material material) {
        if (vUp.dotProduct(vRight) != 0) throw new IllegalArgumentException();
        Vector vTo = vRight.crossProduct(vUp);
        Geometries box = new Geometries();

        Point frontCenter = center.add(vTo.scale(length / 2));
        box.add(constructRectangle(frontCenter, vRight, vUp, height, width)
                .setEmission(color).setMaterial(material));

        Point backCenter = center.add(vTo.scale(-length / 2));
        box.add(constructRectangle(backCenter, vRight, vUp, height, width)
                .setEmission(color).setMaterial(material));

        Point rightCenter = center.add(vRight.scale(width/2));
        box.add(constructRectangle(rightCenter, vTo, vUp, height, length)
                .setEmission(color).setMaterial(material));

        Point leftCenter = center.add(vRight.scale(-width/2));
        box.add(constructRectangle(leftCenter, vTo, vUp, height, length)
                .setEmission(color).setMaterial(material));

        Point topCenter = center.add(vUp.scale(height/2));
        box.add(constructRectangle(topCenter, vTo, vRight, width, length)
                .setEmission(color).setMaterial(material));

        Point bottomCenter = center.add(vUp.scale(-height/2));
        box.add(constructRectangle(bottomCenter, vTo, vRight, width, length)
                .setEmission(color).setMaterial(material));

        this.box = box;
    }

    /**
     * Getter for the Box
     * @return The Box
     */
    public Geometries getBox(){
        return this.box;
    }

    /**
     * Constructs a two-dimensional rectangle
     *
     * @param center Center point of the rectangle
     * @param vRight Right direction of the rectangle
     * @param vUp    Up direction of the rectangle
     * @param height Height of the rectangle
     * @param width  Width of the rectangle
     * @return The new rectangle
     */
    Polygon constructRectangle(Point center, Vector vRight, Vector vUp, double height, double width) {
        double stepUp = height / 2;
        double stepSide = width / 2;
        Point frontBottomCenter = center.add(vUp.scale(-stepUp));
        Point frontTopCenter = center.add(vUp.scale(stepUp));
        Point frontTopRight = frontTopCenter.add(vRight.scale(stepSide));
        Point frontTopLeft = frontTopCenter.add(vRight.scale(-stepSide));
        Point frontBottomRight = frontBottomCenter.add(vRight.scale(stepSide));
        Point frontBottomLeft = frontBottomCenter.add(vRight.scale(-stepSide));
        return new Polygon(frontTopRight, frontTopLeft, frontBottomLeft, frontBottomRight);
    }
}
