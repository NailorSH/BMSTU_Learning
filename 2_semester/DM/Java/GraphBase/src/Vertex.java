import java.util.ArrayList;
import java.util.List;

public class Vertex {
    int index;
    int T1;
    int comp;
    int low;
    List<Vertex> edges;

    public Vertex(int i) {
        index = i;
        edges = new ArrayList<>();
    }

    public String toString() {
        return String.valueOf(index);
    }
}
