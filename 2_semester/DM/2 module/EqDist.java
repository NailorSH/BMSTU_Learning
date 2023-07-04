import java.util.*;

public class EqDist {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Graph graph = new Graph(n, m);
        graph.readGraph(scanner);

        int k = scanner.nextInt();
        graph.readReferenceVertices(k, scanner);

        graph.getEquidistantVertices();

        ArrayList<Vertex> result = graph.getEquidistantVertices();
        if (result.isEmpty()) System.out.println("-");
        else for (var v : result) System.out.print(v.index + " ");

        System.out.println();
    }
}

class Graph {
    private final ArrayList<Vertex> V;
    private final int verticesNumber;
    private final int edgesNumber;
    private int referenceVerticesNumber;
    private ArrayList<Vertex> referenceVertices;

    public Graph(int n, int m) {
        verticesNumber = n;
        edgesNumber = m;
        V = new ArrayList<>();

        for (int i = 0; i < verticesNumber; i++) V.add(new Vertex(i));
    }

    public void readGraph(Scanner scanner) {
        for (int i = 0; i < edgesNumber; i++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();

            V.get(first).edges.add(V.get(second));
            V.get(second).edges.add(V.get(first));
        }
    }

    public void readReferenceVertices(int k, Scanner scanner) {
        referenceVertices = new ArrayList<>();
        referenceVerticesNumber = k;

        for (int i = 0; i < k; i++) {
            int vertexIndex = scanner.nextInt();
            V.get(vertexIndex).isReference = true;
            referenceVertices.add(V.get(vertexIndex));
        }
    }

    public ArrayList<Vertex> getEquidistantVertices() {
        ArrayList<Integer> dist0 = BFS(referenceVertices.get(0));
        ArrayList<Integer> dist;

        ArrayList<Vertex> equidistantVertices = new ArrayList<>();
        for (int i = 1; i < referenceVerticesNumber; i++) {
            dist = BFS(referenceVertices.get(i));
            for (int j = 0; j < verticesNumber; j++) {
                V.get(j).isEquidistant = V.get(j).isEquidistant &&
                        (!V.get(j).isReference &&
                                dist.get(j) != verticesNumber &&
                                dist.get(j).equals(dist0.get(j)));

                if (i == referenceVerticesNumber - 1 && V.get(j).isEquidistant) {
                    equidistantVertices.add(V.get(j));
                }
            }
        }

        return equidistantVertices;
    }

    private ArrayList<Integer> BFS(Vertex startVertex) {
        for (var v : V) v.visited = false;

        Queue<Vertex> queue = new LinkedList<>();

        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(verticesNumber, verticesNumber));
        dist.set(startVertex.index, 0);

        startVertex.visited = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex currVertex = queue.poll();

            for (var u : currVertex.edges) {
                if (!u.visited) {
                    if (dist.get(u.index) > dist.get(currVertex.index) + 1) {
                        dist.set(u.index, dist.get(currVertex.index) + 1);
                        queue.add(u);
                    }
                }
            }
        }

        return dist;
    }
}

class Vertex {
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