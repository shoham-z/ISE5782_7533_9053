package ComplexObjects;

import geometries.Geometries;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public class Cube {
    private Geometries cube;

    /**
     * Constructor for Cube
     * @param center Center point of the Cube
     * @param length Length of the Cube
     * @param vUp Up direction of the Cube
     * @param vRight Right direction of the Cube
     * @param color Color of the Cube
     * @param material Material of the Cube
     */
    public Cube(Point center, double length, Vector vUp, Vector vRight, Color color, Material material) {
        if (vUp.dotProduct(vRight) != 0) throw new IllegalArgumentException();
        Vector vTo = vRight.crossProduct(vUp);
        Geometries cube = new Geometries();
        double step = length / 2;

        Point frontCenter = center.add(vTo.scale(step));
        cube.add(constructRectangle(frontCenter, vRight, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point backCenter = center.add(vTo.scale(-step));
        cube.add(constructRectangle(backCenter, vRight, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point rightCenter = center.add(vRight.scale(step));
        cube.add(constructRectangle(rightCenter, vTo, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point leftCenter = center.add(vRight.scale(-step));
        cube.add(constructRectangle(leftCenter, vTo, vUp, length, length)
                .setEmission(color).setMaterial(material));

        Point topCenter = center.add(vUp.scale(step));
        cube.add(constructRectangle(topCenter, vTo, vRight, length, length)
                .setEmission(color).setMaterial(material));

        Point bottomCenter = center.add(vUp.scale(-step));
        cube.add(constructRectangle(bottomCenter, vTo, vRight, length, length)
                .setEmission(color).setMaterial(material));

        this.cube = cube;
    }

    /**
     * Getter for the Cube
     * @return The cube
     */
    public Geometries getCube(){
        return this.cube;
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
