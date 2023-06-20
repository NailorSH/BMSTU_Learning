import java.util.ArrayList;
import java.util.List;

public class Vertex {
    int index;
    List<Vertex> edges;
    boolean visited = false;
    boolean isReference = false;
    boolean isEquidistant = true;

    public Vertex(int i) {
        index = i;
        edges = new ArrayList<>();
    }

    public String toString() {
        return String.valueOf(index);
    }
}
