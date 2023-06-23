import java.util.*;

public class Graph {
    private final int N;
    private final int M;
    private final String[][] cond;
    private final int[][] trans;

    public Graph(int n, int m) {
        N = n;
        M = m;

        trans = new int[n][m];
        cond = new String[n][m];
    }

    public void readTrans(Scanner scanner) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                trans[i][j] = scanner.nextInt();
            }
        }
    }

    public void readCond(Scanner scanner) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                cond[i][j] = scanner.next();
            }
        }
    }

    public String getGraphViz() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        StringBuilder result = new StringBuilder("digraph {\n");
        result.append("    rankdir = LR\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result.append("    ")
                        .append(i)
                        .append(" -> ")
                        .append(trans[i][j])
                        .append(" [label = \"")
                        .append(alphabet[j])
                        .append("(")
                        .append(cond[i][j])
                        .append(")\"]\n");
            }
        }
        result.append("}");

        return String.valueOf(result);
    }
}