package ComplexObjects;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Polygon;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public class House {
    Geometries house;


    public House(Point baseBottom, double height, Vector up, Vector right) {
        if (up.dotProduct(right) != 0) throw new IllegalArgumentException();
        Material material = new Material().setKd(0.5).setKs(0.5).setShininess(30);
        Vector to = right.crossProduct(up).normalize();
        Geometries house = new Geometries();
        right = right.normalize();
        up = up.normalize();


        Point frontBottomCenter = baseBottom.add(to.scale(height / 2)).add(to.scale(0.000001));

        double doorWidth = height * 3 / 10;
        double doorHeight = height * 3 / 5;

        Point doorBottomRight = frontBottomCenter.add(right.scale(doorWidth / 2));
        Point doorBottomLeft = frontBottomCenter.add(right.scale(-doorWidth / 2));
        Point doorTopRight = doorBottomRight.add(up.scale(doorHeight));
        Point doorTopLeft = doorBottomLeft.add(up.scale(doorHeight));

        Geometry door = new Polygon(doorBottomRight, doorTopRight, doorTopLeft, doorBottomLeft).setMaterial(material).setEmission(new Color(175, 42, 42));


        Pyramid roof = new Pyramid(baseBottom.add(up.scale(height)), height, height, to, up, new Color(164, 74, 74), material);

        Cube body = new Cube(baseBottom.add(up.scale(height / 2)), height, up, right, new Color(249, 253, 183), material);

        house.add(roof.getPyramid(), body.getCube(), door);
        this.house = house;
    }

    /**
     * Getter for House
     * @return The house
     */
    public Geometries getHouse() {
        return house;
    }
}
