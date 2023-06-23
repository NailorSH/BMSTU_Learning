import java.util.List;
import java.util.Map;


public class MST_Prim {
    private final List<Vertex> graph;

    public MST_Prim(List<Vertex> graph) {
        this.graph = graph;
    }

    public void run() {
        if (graph.size() > 0) {
            graph.get(0).setVisited(true);
        }
        while (isDisconnected()) {
            Edge nextMinimum = new Edge(Integer.MAX_VALUE);
            Vertex nextVertex = graph.get(0);
            for (Vertex vertex : graph) {
                if (vertex.isVisited()) {
                    Pair<Vertex, Edge> candidate = vertex.nextMinimum();
                    if (candidate.getValue().getWeight() < nextMinimum.getWeight()) {
                        nextMinimum = candidate.getValue();
                        nextVertex = candidate.getKey();
                    }
                }
            }
            nextMinimum.setIncluded(true);
            nextVertex.setVisited(true);
        }
    }

    private boolean isDisconnected() {
        for (Vertex vertex : graph) {
            if (!vertex.isVisited()) {
                return true;
            }
        }
        return false;
    }

    public void resetPrintHistory() {
        for (Vertex vertex : graph) {
            for (Map.Entry<Vertex, Edge> pair : vertex.getEdges().entrySet()) {
                pair.getValue().setPrinted(false);
            }
        }
    }

    public int minimumSpanningTreeWeight() {
        resetPrintHistory();
        int result = 0;
        for (Vertex vertex : graph) {
            result += vertex.getWeight();
        }

        return result;
    }
}