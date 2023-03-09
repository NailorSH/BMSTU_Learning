public class Line {
    private String name;
    private double x;
    private double y;
    private static int LineCounter;

    public Line(String argName) {
        ++LineCounter;
        System.out.println("An object of the Line class has been created");
        this.name = argName;
    }

    public Line(String argName, Point argA, Point argB) {
        ++LineCounter;
        System.out.println("An object of the Line class has been created");
        this.x = Math.abs(argA.getXCoord() - argB.getXCoord());
        this.y = Math.abs(argA.getYCoord() - argB.getYCoord());
    }

    public String getName() {
        return this.name;
    }

    public double getXCoord() {
        return this.x;
    }

    public double getYCoord() {
        return this.y;
    }

    public void setCoord(double argX, double argY) {
        this.x = argX;
        this.y = argY;
    }

    public double getLineLength() {
        return Math.pow(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0), 0.5);
    }
}