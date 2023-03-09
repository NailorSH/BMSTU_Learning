public class Polyline {
    private String name;
    private Line[] linesArr;

    public Polyline(String argName) {
        System.out.println("An object of the Polyline class has been created");
        this.name = argName;
    }

    public Polyline(String argName, Line[] argLines) {
        System.out.println("An object of the Polyline class has been created");
        this.name = argName;
        this.linesArr = argLines;
    }

    public double getPolylineLength() {
        int len = 0;

        for(int i = 0; i < this.linesArr.length; ++i) {
            len = (int)((double)len + this.linesArr[i].getLineLength());
        }

        return (double)len;
    }
}