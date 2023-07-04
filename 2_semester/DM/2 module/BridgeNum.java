import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BridgeNum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Graph graph = new Graph(n, m);
        graph.readGraph(scanner);

        System.out.println(graph.getBridges());
    }
}

class Graph {
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

class BridgesNumber {
    private static int timer = 0;
    private static int bridgesNum = 0;
    private static int[] tin;
    private static int[] fup;


    public static int findBridges(ArrayList<Vertex> V, int verticesNumber) {
        tin = new int[verticesNumber];
        fup = new int[verticesNumber];

        for (Vertex v : V) {
            if (!v.visited) {
                dfs(v, null);
            }
        }

        return bridgesNum;
    }

    public static void dfs(Vertex v, Vertex p) {
        v.visited = true;
        tin[v.index] = fup[v.index] = timer++;

        for (Vertex to : v.edges) {
            if (p != null && to.index == p.index) {
                continue;
            }
            if (to.visited) {
                fup[v.index] = Math.min(fup[v.index], tin[to.index]);
            } else {
                dfs(to, v);
                fup[v.index] = Math.min(fup[v.index], fup[to.index]);
                if (fup[to.index] > tin[v.index]) {
                    bridgesNum++;
                }
            }
        }
    }
}

class Vertex {
    int index;
    List<Vertex> edges;
    boolean visited = false;

    public Vertex(int i) {
        index = i;
        edges = new ArrayList<>();
    }

    public String toString() {
        return String.valueOf(index);
    }
}