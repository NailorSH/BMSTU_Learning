public class Vector {
    private String name;
    private double x;
    private double y;
    private double z;

    public Vector(String argName) {
        System.out.println("Запущен конструктор объекта Vector");
        this.name = argName;
    }

    public Vector(String argName, Particle argA, Particle argB) {
        System.out.println("Запущен конструктор объекта Vector");
        this.name = argName;
        this.x = argB.getX() - argA.getX();
        this.y = argB.getY() - argA.getY();
        this.z = argB.getZ() - argA.getZ();
    }

    public String getName() {
        return this.name;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setName(String argName) {
        this.name = argName;
    }

    public void setCoords(double argX, double argY, double argZ) {
        this.x = argX;
        this.y = argY;
        this.z = argZ;
    }

    public void setX(double argX) {
        this.x = argX;
    }

    public void setY(double argY) {
        this.y = argY;
    }

    public void setZ(double argZ) {
        this.z = argZ;
    }

    public double getVectorLength() {
        return Math.pow(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0) + Math.pow(this.z, 2.0), 0.5);
    }
}
