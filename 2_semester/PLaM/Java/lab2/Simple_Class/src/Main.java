public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        int n = 5;
        Line[] lines = new Line[n];

        for(int i = 0; i < n; ++i) {
            Point PointA = new Point("A", 1.0, 0.0);
            Point PointB = new Point("B", 2.0, 1.0);
            lines[i] = new Line("Line" + i, PointA, PointB);
        }

        Polyline poly = new Polyline("My poplyline", lines);
        System.out.println(poly.getPolylineLength());
    }
}