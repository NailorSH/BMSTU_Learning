public class Edge {
    Vertex firstVertex;
    Vertex secondVertex;
    String color = "null";
    public Edge(Vertex v1, Vertex v2) {
        firstVertex = v1;
        secondVertex = v2;
    }

    public String toString() {
        String result = firstVertex.index + " -- " + secondVertex.index;
        if (!color.equals("null")) result += " [color = " + color + "]";
        return result;
    }
}
