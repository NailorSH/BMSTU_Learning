import java.util.ArrayList;
import java.util.List;

public class Vertex {
    int index;
    boolean visited = false;
    List<Vertex> edges;

    Vertex(int index) {
        this.index = index;
        edges = new ArrayList<>();
    }
}