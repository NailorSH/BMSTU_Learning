public class Point {
    private String name;
    private double x;
    private double y;
    private static int PointCounter;

    public Point(String argName) {
        ++PointCounter;
        System.out.println("An object of the Point class has been created");
        this.name = argName;
    }

    public Point(String argName, double argX, double argY) {
        ++PointCounter;
        System.out.println("An object of the Point class has been created");
        this.x = argX;
        this.y = argY;
    }

    public double getXCoord() {
        return this.x;
    }

    public double getYCoord() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public void setCoord(double varX, double varY) {
        this.x = varX;
        this.y = varY;
    }
}