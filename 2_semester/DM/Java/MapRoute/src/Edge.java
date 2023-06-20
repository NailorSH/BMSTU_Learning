public class Edge {
    Vertex firstVertex;
    Vertex secondVertex;
    public Edge(Vertex v1, Vertex v2) {
        firstVertex = v1;
        secondVertex = v2;
    }

    public String toString() {
        return firstVertex.index + " -- " + secondVertex.index;
    }
}
