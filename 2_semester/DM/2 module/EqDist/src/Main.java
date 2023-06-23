import java.util.*;

public class Main {
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