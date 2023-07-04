import java.util.*;

public class Prim {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Graph graph = new Graph(n, m);
        graph.readGraph(scanner);

        System.out.println(graph.getMinimumSum());
    }
}

class Graph {
    private final List<Vertex> V;
    private final int verticesNumber;
    private final int edgesNumber;


    public Graph(int n, int m) {
        verticesNumber = n;
        edgesNumber = m;

        V = new ArrayList<>();

        for (int i = 0; i < verticesNumber; i++) {
            V.add(new Vertex());
        }
    }

    public void readGraph(Scanner scanner) {
        for (int i = 0; i < edgesNumber; i++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();
            int len = scanner.nextInt();

            Edge edge = new Edge(len);

            V.get(first).addEdge(V.get(second), edge);
            V.get(second).addEdge(V.get(first), edge);
        }
    }

    public int getMinimumSum() {
        MST_Prim prim = new MST_Prim(V);
        prim.run();
        return prim.minimumSpanningTreeWeight();
    }
}

class MST_Prim {
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

class Vertex {
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

class Edge {

    private final int weight;
    private boolean isIncluded = false;
    private boolean isPrinted = false;

    public Edge(int weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isIncluded() {
        return isIncluded;
    }

    public void setIncluded(boolean included) {
        isIncluded = included;
    }

    public boolean isPrinted() {
        return isPrinted;
    }

    public void setPrinted(boolean printed) {
        isPrinted = printed;
    }
}

class Pair<T1, T2> {
    T1 K;
    T2 V;

    public Pair(T1 k, T2 v) {
        K = k;
        V = v;
    }

    public T1 getKey() {
        return K;
    }

    public T2 getValue() {
        return V;
    }
}