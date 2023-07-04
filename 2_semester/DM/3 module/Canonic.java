import java.util.*;

public class Canonic {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int q0 = scanner.nextInt();

        Graph graph = new Graph(n, m);
        graph.readTrans(scanner);
        graph.readCond(scanner);

        scanner.close();

        graph.getCanonical(q0);
    }
}

class Graph {
    private final int N;
    private final int M;
    private final List<Vertex> V;
    private final List<Edge> E;
    private final List<List<String>> cond;
    private List<Integer> num;
    private List<Integer> parent;
    private final int[][] trans;

    public Graph(int n, int m) {
        N = n;
        M = m;

        V = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            V.add(new Vertex(i));
        }
        E = new ArrayList<>();

        num = new ArrayList<>();
        parent = new ArrayList<>();
        trans = new int[n][m];

        cond = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            cond.add(new ArrayList<>(m));
        }
    }

    public void addEdge(Edge edge) {
        E.add(edge);
        V.get(edge.from.index).edges.add(edge.to);
    }

    public void readTrans(Scanner scanner) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                trans[i][j] = scanner.nextInt();
                if (i == trans[i][j]) {
                    continue;
                }
                Edge edge = new Edge(V.get(i), V.get(trans[i][j]));
                addEdge(edge);
            }
        }
    }

    public void readCond(Scanner scanner) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                cond.get(i).add(scanner.next());
            }
        }
    }

    public void getCanonical(int q) {
        num = new ArrayList<>(Collections.nCopies(N, 0));
        parent = new ArrayList<>(Collections.nCopies(N, 0));
        DFS(q);

        System.out.println(N + "\n" + M + "\n" + 0);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int transNum = trans[num.get(i)][j];
                if (j == M - 1) {
                    System.out.println(parent.get(transNum));
                } else {
                    System.out.print(parent.get(transNum) + " ");
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (j == M - 1) {
                    System.out.println(cond.get(num.get(i)).get(j));
                } else {
                    System.out.print(cond.get(num.get(i)).get(j) + " ");
                }
            }
        }
    }

    private void DFS(int start) {
        int cnt = 0;
        Stack<Vertex> stack = new Stack<>();
        stack.push(V.get(start));

        while (!stack.isEmpty()) {
            Vertex cur = stack.pop();
            List<Vertex> adjList = cur.edges;

            if (!cur.visited) {
                cur.visited = true;
                num.set(cnt, cur.index);
                parent.set(cur.index, cnt);
                cnt++;

                for (int i = adjList.size() - 1; i >= 0; i--) {
                    Vertex neighbor = adjList.get(i);
                    if (!neighbor.visited) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
}

class Edge {
    Vertex from, to;

    Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }
}

class Vertex {
    int index;
    boolean visited = false;
    List<Vertex> edges;

    Vertex(int index) {
        this.index = index;
        edges = new ArrayList<>();
    }
}