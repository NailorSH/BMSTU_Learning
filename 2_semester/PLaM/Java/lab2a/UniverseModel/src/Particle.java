public class Particle {
    private static int particlesCounter = 0;
    private String name;
    private double x;
    private double y;
    private double z;

    public Particle() {
        ++particlesCounter;
        this.name = "Particle #" + particlesCounter;
        System.out.println("Объект класса Particle создан");
    }

    public Particle(String argName) {
        ++particlesCounter;
        this.name = argName;
        System.out.println("Объект класса Particle создан");
    }

    public Particle(double argX, double argY, double argZ) {
        ++particlesCounter;
        this.name = "Particle #" + particlesCounter;
        System.out.println("Объект класса Particle создан");
        this.x = argX;
        this.y = argY;
        this.z = argZ;
    }

    public Particle(String argName, double argX, double argY, double argZ) {
        ++particlesCounter;
        System.out.println("Объект класса Particle создан");
        this.name = argName;
        this.x = argX;
        this.y = argY;
        this.z = argZ;
    }

    public Particle(String argName, double argX, double argY) {
        ++particlesCounter;
        System.out.println("Объект класса Particle создан");
        this.name = argName;
        this.x = argX;
        this.y = argY;
        this.z = 0.0;
    }

    public Particle(String argName, double argX) {
        ++particlesCounter;
        System.out.println("Объект класса Particle создан");
        this.name = argName;
        this.x = argX;
        this.y = 0.0;
        this.z = 0.0;
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
}