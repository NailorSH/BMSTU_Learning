import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MapRoute {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Graph graph = new Graph(n);
        graph.readGraph(scanner);
        scanner.close();

        System.out.println(graph.getMinPath());
    }
}

class Graph {
    Vertex[][] map;
    private final int N;

    public Graph(int n) {
        N = n;
        map = new Vertex[N][N];
    }

    public void readGraph(Scanner scanner) {
        for (int i = 0; i < N; i++) {
            map[i] = new Vertex[N];
            for (int j = 0; j < N; j++) {
                int cost = scanner.nextInt();
                map[i][j] = new Vertex(cost);
            }
        }
    }

    public int getMinPath() {
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(p -> map[p.x][p.y].dist));
        map[0][0].dist = map[0][0].cost;
        queue.offer(new Point(0, 0));

        Point[] shifts = new Point[4];
        shifts[0] = new Point(0, 1);
        shifts[1] = new Point(0, -1);
        shifts[2] = new Point(1, 0);
        shifts[3] = new Point(-1, 0);

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            for (Point shift : shifts) {
                int x = point.x + shift.x;
                int y = point.y + shift.y;
                if (x < 0 || y < 0 || x >= N || y >= N) {
                    continue;
                }
                if (map[x][y].dist > map[point.x][point.y].dist + map[x][y].cost) {
                    map[x][y].dist = map[point.x][point.y].dist + map[x][y].cost;
                    queue.offer(new Point(x, y));
                }
            }
        }

        return map[N - 1][N - 1].dist;
    }
}

class Vertex {
    int cost;
    int dist;

    Vertex(int cost) {
        this.cost = cost;
        this.dist = 1000000;
    }
}

class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}