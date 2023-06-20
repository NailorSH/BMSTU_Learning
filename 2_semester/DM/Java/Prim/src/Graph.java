import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
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