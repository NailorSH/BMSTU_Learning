import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private final Map<Vertex, Edge> edges;
    private boolean isVisited = false;

    public Vertex() {
        edges = new HashMap<>();
    }

    public Map<Vertex, Edge> getEdges() {
        return edges;
    }

    public void addEdge(Vertex vertex, Edge edge) {
        if (this.edges.containsKey(vertex)) {
            if (edge.getWeight() < this.edges.get(vertex).getWeight()) {
                this.edges.replace(vertex, edge);
            }
        } else {
            this.edges.put(vertex, edge);
        }
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public Pair<Vertex, Edge> nextMinimum() {
        Edge nextMinimum = new Edge(Integer.MAX_VALUE);
        Vertex nextVertex = this;
        for (Map.Entry<Vertex, Edge> pair : edges.entrySet()) {
            if (!pair.getKey().isVisited()) {
                if (!pair.getValue().isIncluded()) {
                    if (pair.getValue().getWeight() < nextMinimum.getWeight()) {
                        nextMinimum = pair.getValue();
                        nextVertex = pair.getKey();
                    }
                }
            }
        }
        return new Pair<>(nextVertex, nextMinimum);
    }

    public int getWeight() {
        int res = 0;
        if (isVisited()) {
            for (Map.Entry<Vertex, Edge> pair : edges.entrySet()) {
                if (pair.getValue().isIncluded()) {
                    if (!pair.getValue().isPrinted()) {
                        res += pair.getValue().getWeight();
                        pair.getValue().setPrinted(true);
                    }
                }
            }
        }
        return res;
    }
}