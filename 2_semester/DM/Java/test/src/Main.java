import java.util.*;

class EdgeNode {
    List<Boolean> nodes;
    int firstNode;
    int cntNodes;
    int cntEdges;
}

class Edge {
    int from;
    int to;

    Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }
}

class Graph {
    private int numVertices;
    private List<List<Integer>> adjLists;
    List<Edge> edges;
    private List<Boolean> visited;

    Graph(int vertices) {
        numVertices = vertices;
        adjLists = new ArrayList<>(vertices);
        edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjLists.add(new ArrayList<>());
        }
        visited = new ArrayList<>(vertices);
    }

    void addEdge(Edge edge) {
        edges.add(edge);
        adjLists.get(edge.from).add(edge.to);
        adjLists.get(edge.to).add(edge.from);
    }

    EdgeNode BFS(int startVertex) {
        int cntNodes = 1, cntEdges = 0;

        visited = new ArrayList<>(numVertices);

        Queue<Integer> queue = new LinkedList<>();

        visited.set(startVertex, true);
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int currVertex = queue.poll();

            for (int adjVertex : adjLists.get(currVertex)) {
                cntEdges++;
                if (!visited.get(adjVertex)) {
                    visited.set(adjVertex, true);
                    cntNodes++;
                    queue.add(adjVertex);
                }
            }
        }

        EdgeNode res = new EdgeNode();
        res.nodes = visited;
        res.firstNode = startVertex;
        res.cntNodes = cntNodes;
        res.cntEdges = cntEdges / 2;
        return res;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        ArrayList<Boolean> used = new ArrayList<>(n);
        Graph graph = new Graph(n);
        for (int i = 0; i < k; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.addEdge(new Edge(from, to));
        }

        int maxCntNodes = 0, maxCntEdges = 0, maxFirstNode = n;
        List<Boolean> maxCompNodes = new ArrayList<>();
        int i = 0;
        while (i < n) {
            if (!used.get(i)) {
                EdgeNode comp = graph.BFS(i);
                List<Boolean> compNodes = comp.nodes;
                int firstNode = comp.firstNode;
                int cntEdges = comp.cntEdges;
                int cntNodes = comp.cntNodes;
                if (cntNodes > maxCntNodes) {
                    maxCompNodes = compNodes;
                    maxCntNodes = cntNodes;
                    maxCntEdges = cntEdges;
                    maxFirstNode = firstNode;
                } else if (cntNodes == maxCntNodes) {
                    if (cntEdges > maxCntEdges) {
                        maxCompNodes = compNodes;
                        maxCntNodes = cntNodes;
                        maxCntEdges = cntEdges;
                        maxFirstNode = firstNode;
                    } else if (cntEdges == maxCntEdges) {
                        if (firstNode < maxFirstNode) {
                            maxCompNodes = compNodes;
                            maxCntNodes = cntNodes;
                            maxCntEdges = cntEdges;
                            maxFirstNode = firstNode;
                        }
                    }
                }
            }
            i++;
        }

        System.out.println("graph {");
        for (int j = 0; j < n; j++) {
            if (maxCompNodes.get(j)) {
                System.out.printf("    %d [color = red]%n", j);
            } else {
                System.out.printf("    %d%n", j);
            }
        }
        for (Edge edge : graph.edges) {
            if (maxCompNodes.get(edge.from) && maxCompNodes.get(edge.to)) {
                System.out.printf("    %d -- %d [color = red]%n", edge.from, edge.to);
            } else {
                System.out.printf("    %d -- %d%n", edge.from, edge.to);
            }
        }
        System.out.println("}");
    }
}
