import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ;
        int n = scanner.nextInt();
        int[][] weights = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weights[i][j] = scanner.nextInt();
            }
        }
        Graph graph = new Graph(n * n);
        Point[] shifts = { new Point(-1, 0), new Point(0, -1), new Point(1, 0), new Point(0, 1) };
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int adjNode = i * n + j;
                for (Point shift : shifts) {
                    Point neighPoint = new Point(i + shift.x, j + shift.y);
                    if (neighPoint.x >= 0 && neighPoint.y >= 0 && neighPoint.x < n && neighPoint.y < n) {
                        int neighNode = neighPoint.x * n + neighPoint.y;
                        Edge edge = new Edge(adjNode, neighNode, weights[neighPoint.x][neighPoint.y]);
                        graph.addEdge(edge);
                    }
                }
            }
        }

        System.out.println(weights[0][0] + graph.minPath(0));
    }
}

class Edge {
    int from, to, len;

    Edge(int from, int to, int len) {
        this.from = from;
        this.to = to;
        this.len = len;
    }
}

class Node {
    int node, len;

    Node(int node, int len) {
        this.node = node;
        this.len = len;
    }
}

class Pair implements Comparable<Pair> {
    int key;
    int value;

    Pair(int key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        return Integer.compare(this.key, other.key);
    }
}

class Graph {
    int numVertices;
    List<List<Node>> adjLists;
    List<Edge> edges;

    Graph(int vertices) {
        numVertices = vertices;
        adjLists = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjLists.add(new ArrayList<>());
        }
        edges = new ArrayList<>();
    }

    void addEdge(Edge edge) {
        edges.add(edge);
        Node newNode = new Node(edge.to, edge.len);
        adjLists.get(edge.from).add(newNode);
    }

    int minPath(int st) {
        int[] dist = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[st] = 0;
        PriorityQueue<Pair> q = new PriorityQueue<>();
        q.add(new Pair(dist[st], st));

        while (!q.isEmpty()) {
            int v = q.peek().value;
            int cur_d = -q.peek().key;
            q.remove();
            if (cur_d > dist[v]) continue;

            List<Node> adjList = adjLists.get(v);
            for (Node node : adjList) {
                int to = node.node;
                int len = node.len;
                if (dist[v] + len < dist[to]) {
                    dist[to] = dist[v] + len;
                    q.add(new Pair(-dist[to], to));
                }
            }
        }
        return dist[numVertices - 1];
    }
}

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}