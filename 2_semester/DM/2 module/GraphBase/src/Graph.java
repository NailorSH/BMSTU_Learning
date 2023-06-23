import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Graph {
    private final ArrayList<Vertex> V;
    private final List<Edge> E;
    private final int verticesNumber;
    private final int edgesNumber;
    private int time = 1;
    private int count = 1;

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
        }
    }

    public void getGraphBase() {
        Tarjan();
        Condensation[] base = new Condensation[count];

        for (int i = 0; i < count; i++) {
            base[i] = new Condensation();
        }
        Condensation.isUsed = new boolean[count];
        for (int i = 0; i < count; i++) {
            Condensation.isUsed[i] = true;
        }
        for (int i = 0; i < verticesNumber; i++) {
            if (base[V.get(i).comp].condensationV.size() > 0)
                base[V.get(i).comp].condensationV.add(V.get(i));
            else {
                base[V.get(i).comp].min = i;
                base[V.get(i).comp].condensationV.add(V.get(i));
            }
        }
        for (int j = 0; j < verticesNumber; j++) {
            for (int i = 0; i < V.get(j).edges.size(); i++) {
                if (V.get(j).comp != V.get(V.get(j).edges.get(i).index).comp)
                    Condensation.isUsed[V.get(V.get(j).edges.get(i).index).comp] = false;
            }
        }
        for (int i = 1; i < count; i++)
            if (Condensation.isUsed[i])
                System.out.print(base[i].min + " ");
    }

    private void Tarjan() {
        for (Vertex v : V) {
            v.T1 = 0;
            v.comp = 0;
        }
        Stack<Vertex> s = new Stack<>();
        for (int i = 0; i < V.size(); i++)
            if (V.get(i).T1 == 0)
                VisitVertex_Terjan(i, s);
    }

    private void VisitVertex_Terjan(int i, Stack<Vertex> s) {
        Vertex u;
        V.get(i).T1 = time;
        V.get(i).low = time;
        time++;
        s.push(V.get(i));
        for (int j = 0; j < V.get(i).edges.size(); j++) {
            if (V.get(V.get(i).edges.get(j).index).T1 == 0)
                VisitVertex_Terjan(V.get(i).edges.get(j).index, s);
            if (V.get(V.get(i).edges.get(j).index).comp == 0 && V.get(i).low > V.get(V.get(i).edges.get(j).index).low)
                V.get(i).low = V.get(V.get(i).edges.get(j).index).low;
        }
        if (V.get(i).T1 == V.get(i).low) {
            do {
                u = s.pop();
                u.comp = count;
            }
            while (!(u.equals(V.get(i))));
            count++;
        }
    }

    class Condensation {
        ArrayList<Vertex> condensationV = new ArrayList<>();
        int min;
        static boolean[] isUsed;
    }
}