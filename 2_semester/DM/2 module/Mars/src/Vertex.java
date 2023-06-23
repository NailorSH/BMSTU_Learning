import java.util.ArrayList;
import java.util.List;

public class Vertex {
    List<Vertex> edges;
    int group;
    int index;
    boolean visited;

    public Vertex(int index) {
        this.index = index;
        edges = new ArrayList<>();
        visited = false;
    }
}