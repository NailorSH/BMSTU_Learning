import java.util.ArrayList;

public class BridgesNumber {
    private static int timer = 0;
    private static int bridgesNum = 0;
    private static int[] tin;
    private static int[] fup;


    public static int findBridges(ArrayList<Vertex> V, int verticesNumber) {
        tin = new int[verticesNumber];
        fup = new int[verticesNumber];

        for (Vertex v : V) {
            if (!v.visited) {
                dfs(v, null);
            }
        }

        return bridgesNum;
    }

    public static void dfs(Vertex v, Vertex p) {
        v.visited = true;
        tin[v.index] = fup[v.index] = timer++;

        for (Vertex to : v.edges) {
            if (p != null && to.index == p.index) {
                continue;
            }
            if (to.visited) {
                fup[v.index] = Math.min(fup[v.index], tin[to.index]);
            } else {
                dfs(to, v);
                fup[v.index] = Math.min(fup[v.index], fup[to.index]);
                if (fup[to.index] > tin[v.index]) {
                    bridgesNum++;
                }
            }
        }
    }
}
