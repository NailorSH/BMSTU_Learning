public class Edge implements Comparable<Edge> {
    int from, to;
    double len;

    public Edge(int from, int to, double len) {
        this.from = from;
        this.to = to;
        this.len = len;
    }

    @Override
    public int compareTo(Edge edge) {
        if (this.len != edge.len) return this.len < edge.len ? -1 : 1;
        return 0;
    }
}