import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Graph graph = new Graph(n, m);
        graph.readGraph(scanner);

        graph.MaxComponent();
        System.out.println(graph);
    }
}

/* test1
8
7
0 1
0 2
1 3
4 5
4 6
5 7
6 7
*/

/*test2
8
7
0 1
0 2
1 3
2 3
4 5
4 6
5 7
*/

/*test3
8
8
0 1
0 2
1 3
2 3
4 5
4 6
5 7
6 7
*/

/*test4
12
12
0 1
0 2
1 3
2 3
4 5
4 6
5 7
6 7
8 9
8 10
9 11
10 11
*/