import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaxComponent {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Graph graph = new Graph(n, m);
        graph.readGraph(scanner);

        graph.MaxComponent();
        System.out.println(graph);
    }
}

class Graph {
    private final ArrayList<Vertex> V;
    private final List<Edge> E;
    private final int verticesNumber;
    private final int edgesNumber;
    private String graphDOT;

    public Graph(int n, int m) {
        verticesNumber = n;
        edgesNumber = m;
        E = new ArrayList<>();
        V = new ArrayList<>();

        for (int i = 0; i < verticesNumber; i++) {
            V.add(new Vertex(i));
        }
    }

    public void readGraph(Scanner scanner) {
        for (int i = 0; i < edgesNumber; i++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();

            E.add(new Edge(V.get(first), V.get(second)));
            V.get(first).edges.add(V.get(second));
            V.get(second).edges.add(V.get(first));
        }
    }

    public String toString() {
        return getGraphDOT();
    }

    public void printVerticesEdges() {
        for (var v : V) {
            System.out.println(v.edges);
        }
    }

    private static void dfs(Vertex vertex, Connection component) {
        vertex.visited = true;
        component.list.add(vertex);

        for (Vertex u : vertex.edges) {
            if (!u.visited) {
                dfs(u, component);
            }
        }
    }

    public void MaxComponent() {
        List<Connection> connections = new ArrayList<>();
        for (var vertex : V) {
            if (!vertex.visited) {
                Connection component = new Connection();
                connections.add(component);
                dfs(vertex, component);
            }
        }

        for (var con : connections) {
            for (var edge : E) {
                if (con.list.contains(edge.firstVertex)) {
                    con.edgesNum++;
                }
            }
        }

        int maxVerticesNum = -1;
        int maxEdgesNum = -1;
        Connection maxComponent = connections.get(0);
        for (var c : connections) {
            if(maxVerticesNum < c.list.size()) {
                maxVerticesNum = c.list.size();
                maxComponent = c;
                maxEdgesNum = c.edgesNum;
            } else if (maxVerticesNum == c.list.size()) {
                if (maxEdgesNum < c.edgesNum) {
                    maxEdgesNum = c.edgesNum;
                    maxComponent = c;
                }
            }
        }
        for (var v : maxComponent.list) {
            v.color = "red";
        }

        for (var edge : E) {
            if(maxComponent.list.contains(edge.firstVertex)) {
                edge.color = "red";
            }
        }
    }

    public String getGraphDOT() {
        buildGraphDOT();
        return graphDOT;
    }

    public void buildGraphDOT() {
        StringBuilder result = new StringBuilder("graph {\n");
        for (var v : V) {
            result.append("\t").append(v).append("\n");
        }
        for (var edge : E) {
            result.append("\t").append(edge).append("\n");
        }
        result.append("}");
        graphDOT = result.toString();
    }
}

class Connection {
    List<Vertex> list;
    int edgesNum = 0;

    Connection() {
        list = new ArrayList<>();
    }
}

class Vertex {
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

class Edge {
    Vertex firstVertex;
    Vertex secondVertex;
    String color = "null";
    public Edge(Vertex v1, Vertex v2) {
        firstVertex = v1;
        secondVertex = v2;
    }

    public String toString() {
        String result = firstVertex.index + " -- " + secondVertex.index;
        if (!color.equals("null")) result += " [color = " + color + "]";
        return result;
    }
}

/* test1
8
7
0 1
0 2
1 3
4 5
4 6
5 7
6 7
*/

/*test2
8
7
0 1
0 2
1 3
2 3
4 5
4 6
5 7
*/

/*test3
8
8
0 1
0 2
1 3
2 3
4 5
4 6
5 7
6 7
*/

/*test4
12
12
0 1
0 2
1 3
2 3
4 5
4 6
5 7
6 7
8 9
8 10
9 11
10 11
*/