import java.util.*;

public class Kruskal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Graph graph = new Graph(n);
        graph.readGraph(scanner);
        scanner.close();

        System.out.printf("%.2f\n", graph.getMinimumSum());
    }
}

class Graph {
    private final ArrayList<Vertex> V;
    private final ArrayList<Edge> E;
    private final int verticesNumber;

    public Graph(int n) {
        verticesNumber = n;
        V = new ArrayList<>();
        E = new ArrayList<>();
    }

    public void readGraph(Scanner scanner) {
        for (int i = 0; i < verticesNumber; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Point p = new Point(x, y);

            for (int j = 0; j < i; j++) {
                Point q = V.get(j).coordinate;
                Edge edge = new Edge(i, j, Math.sqrt((q.x - p.x) * (q.x - p.x) + (q.y - p.y) * (q.y - p.y)));
                E.add(edge);
            }

            V.add(new Vertex(p));
        }
    }

    public double getMinimumSum() {
        return mstKruskal(E);
    }

    private double mstKruskal(ArrayList<Edge> edges) {
        DSF dsf = new DSF(verticesNumber); // СНМ
        Collections.sort(edges); // Сортируем ребра
        double ret = 0; // результат
        for (Edge e : edges) // перебираем ребра в порядке возрастания
            if (dsf.union(e.from, e.to)) // если ребра принадлежат разным компонентам
                ret += e.len; // добавляем вес ребра к стоимости MST
        return ret;
    }
}

class DSF {
    int[] set; // номер множества
    int[] rnk; // ранг

    DSF(int size) {
        set = new int[size];
        rnk = new int[size];
        for (int i = 0; i < size; i++)
            set[i] = i;
    }

    // Возвращает множество, которому принадлежит x
    int set(int x) {
        return x == set[x] ? x : (set[x] = set(set[x]));
    }

    // Если u и v лежат в разных множествах, то сливаем их и возвращаем true
    boolean union(int u, int v) {
        if ((u = set(u)) == (v = set(v)))
            return false;
        if (rnk[u] < rnk[v]) {
            set[u] = v;
        } else {
            set[v] = u;
            if (rnk[u] == rnk[v])
                rnk[u]++;
        }
        return true;
    }
}

class Edge implements Comparable<Edge> {
    int from, to;
    double len;

    public Edge(int from, int to, double len) {
        this.from = from;
        this.to = to;
        this.len = len;
    }

    @Override
    public int compareTo(Edge edge) {
        if (this.len != edge.len) return this.len < edge.len ? -1 : 1;
        return 0;
    }
}

class Vertex {
    Point coordinate;

    public Vertex(Point p) {
        coordinate = p;
    }
}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}