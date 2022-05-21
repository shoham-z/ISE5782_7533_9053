package primitives;

import geometries.Geometries;
import geometries.Triangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjParser {

    private String filename = null;
    private List<Point> vertices = new ArrayList<>();
    Geometries shape = new Geometries();

    /**
     * Constructor for ObjParser
     *
     * @param name Name of the file
     */
    public ObjParser(String name) throws IOException {
        this.filename = name;
        this.loadVertices();
        this.loadGeometries();
    }

    /**
     * Getter for the shape the obj file represented
     *
     * @return The shape
     */
    public Geometries getShape() {
        return this.shape;
    }

    /**
     * Reads from the file all the vertices and puts them in the vertices array
     */
    private void loadVertices() throws IOException {
        FileReader input;
        try {
            input = new FileReader(this.filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(input);
        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("v ")) {
                String[] content = line.split(" ");
                Point vertex = new Point(Double.parseDouble(content[1]), Double.parseDouble(content[2]), Double.parseDouble(content[3]));
                this.vertices.add(vertex);
            }
            line = reader.readLine();
        }
    }

    /**
     * Reads from the file all the faces (triangles) and puts them in the Geometries
     */
    private void loadGeometries() throws IOException {
        FileReader input;
        try {
            input = new FileReader(this.filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(input);
        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("f ")) {
                String[] content = line.split(" ");
                int vertex1, vertex2, vertex3;
                try{
                    vertex1 = Integer.parseInt(content[1]);
                    vertex2 = Integer.parseInt(content[2]);
                    vertex3 = Integer.parseInt(content[3]);
                } catch (NumberFormatException e) {
                    String[] data1 = content[1].split("/");
                    String[] data2 = content[2].split("/");
                    String[] data3 = content[3].split("/");
                    vertex1 = Integer.parseInt(data1[0]);
                    vertex2 = Integer.parseInt(data2[0]);
                    vertex3 = Integer.parseInt(data3[0]);
                }

                this.shape.add(new Triangle(vertices.get(vertex1 - 1), vertices.get(vertex2 - 1), vertices.get(vertex3 - 1)));
            }
            line = reader.readLine();
        }
    }

}
