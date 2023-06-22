import java.util.*;

class Tile {
    int cost;
    int dist;

    Tile(int cost) {
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

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Tile[][] mat = new Tile[n][n];
        for (int i = 0; i < n; i++) {
            mat[i] = new Tile[n];
            for (int j = 0; j < n; j++) {
                int cost = scanner.nextInt();
                mat[i][j] = new Tile(cost);
            }
        }

        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(p -> mat[p.x][p.y].dist));
        mat[0][0].dist = mat[0][0].cost;
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
                if (x < 0 || y < 0 || x >= n || y >= n) {
                    continue;
                }
                if (mat[x][y].dist > mat[point.x][point.y].dist + mat[x][y].cost) {
                    mat[x][y].dist = mat[point.x][point.y].dist + mat[x][y].cost;
                    queue.offer(new Point(x, y));
                }
            }
        }

        System.out.println(mat[n - 1][n - 1].dist);
    }
}
