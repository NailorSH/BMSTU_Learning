import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    private final ArrayList<Vertex> V;
    private final int verticesNumber;
    private final int edgesNumber;


    public Graph(int n, int m) {
        verticesNumber = n;
        edgesNumber = m;
        V = new ArrayList<>();

        for (int i = 0; i < verticesNumber; i++) {
            V.add(new Vertex(i));
        }


    }

    public void readGraph(Scanner scanner) {
        for (int i = 0; i < edgesNumber; i++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();

            V.get(first).edges.add(V.get(second));
            V.get(second).edges.add(V.get(first));
        }
    }

    public int getBridges() {
        return BridgesNumber.findBridges(V, verticesNumber);
    }
}