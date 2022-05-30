package ComplexObjects;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Polygon;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public class Pyramid {
    private Geometries pyramid;

    /**
     * Constructor for Pyramid
     * @param bottomCenter Bottom center of the pyramid
     * @param height Height of the pyramid
     * @param width Width of the pyramid
     * @param vTo To direction of the pyramid
     * @param vUp Up direction of the pyramid
     * @param color Color of the pyramid
     * @param material Material of the pyramid
     */
    public Pyramid(Point bottomCenter, double height, double width, Vector vTo, Vector vUp, Color color, Material material) {
        if (vTo.dotProduct(vUp) != 0) throw new IllegalArgumentException();
        Vector vRight = vTo.crossProduct(vUp).normalize();
        Point top = bottomCenter.add(vUp.normalize().scale(height));
        Point frontCenter = bottomCenter.add(vTo.normalize().scale(width / 2));
        Point leftFront = frontCenter.add(vRight.normalize().scale(-width / 2));
        Point rightFront = frontCenter.add(vRight.normalize().scale(width / 2));
        Point backCenter = bottomCenter.add(vTo.normalize().scale(-width / 2));
        Point leftBack = backCenter.add(vRight.normalize().scale(-width / 2));
        Point rightBack = backCenter.add(vRight.normalize().scale(width / 2));

        Geometry front = new Triangle(leftFront, rightFront, top).setEmission(color).setMaterial(material);
        Geometry back = new Triangle(leftBack, rightBack, top).setEmission(color).setMaterial(material);
        Geometry right = new Triangle(rightBack, rightFront, top).setEmission(color).setMaterial(material);
        Geometry left = new Triangle(leftFront, leftBack, top).setEmission(color).setMaterial(material);
        Geometry bottom = new Polygon(leftBack, leftFront, rightFront, rightBack).setEmission(color).setMaterial(material);

        this.pyramid = new Geometries(front, back, right, left, bottom);
    }

    /**
     * Getter for Pyramid
     * @return The pyramid
     */
    public Geometries getPyramid(){
        return this.pyramid;
    }
}
