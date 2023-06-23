public class IncidenceMatrix {
    private final int[][] matrix;
    private final int verticesNumber;
    private final int edgesNumber;
    public IncidenceMatrix(int n, int m) {
        this.verticesNumber = n;
        this.edgesNumber = m;
        this.matrix = new int[n][m];
    }

    public void addEdge(int firstVertex, int secondVertex, int edge) {
        matrix[firstVertex][edge] = 1;
        matrix[secondVertex][edge] = 1;
    }

    public int getVerticesNumber() {
        return verticesNumber;
    }

    public int getEdgesNumber() {
        return edgesNumber;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < verticesNumber; i++) {
            for (int j = 0; j < edgesNumber; j++) {
                result.append(matrix[i][j]);
            }
        }
        return result.toString();
    }
}