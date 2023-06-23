import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Graph graph = new Graph(n);
        graph.readGraph(scanner);
        scanner.close();

        System.out.printf("%.2f\n", graph.getMinimumSum());
    }
}