import java.util.ArrayList;
import java.util.List;

public class Vertex {
    int index;
    List<Vertex> edges;
    String color = "null";
    ArrayList<Vertex> adjacentVertices;
    boolean visited = false;

    public Vertex(int i) {
        index = i;
        edges = new ArrayList<>();
        adjacentVertices = new ArrayList<>();
    }

    public String toString() {
        String result = String.valueOf(index);
        if (!color.equals("null")) result += " [color = " + color + "]";
        return result;
    }
}
