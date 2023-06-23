import java.util.*;

public class Graph {
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