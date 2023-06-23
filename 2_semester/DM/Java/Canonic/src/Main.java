import java.util.*;

public class Main {
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