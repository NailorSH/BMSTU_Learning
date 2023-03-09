public class Universe {
    private static int universesCounter = 0;
    private String name;
    private int particlesNumber;
    private Particle[] particlesArr;

    public Universe() {
        ++universesCounter;
        System.out.println("Объект класса Universe создан");
        this.name = "Universe " + universesCounter;
    }

    public Universe(String argName) {
        ++universesCounter;
        System.out.println("Объект класса Universe создан");
        this.name = argName;
    }

    public Universe(String argName, Particle[] argArr) {
        ++universesCounter;
        System.out.println("Объект класса Universe создан");
        this.name = argName;
        this.particlesArr = argArr;
        this.particlesNumber = argArr.length;
        double argX = 0.0;
        double argY = 0.0;
        double argZ = 0.0;

        for(int i = 0; i < argArr.length; ++i) {
            argX += argArr[i].getX();
            argY += argArr[i].getY();
            argZ += argArr[i].getZ();
        }

    }

    public Universe(Particle[] argArr) {
        ++universesCounter;
        System.out.println("Объект класса Universe создан");
        this.name = "Universe " + universesCounter;
        this.particlesArr = argArr;
        double argX = 0.0;
        double argY = 0.0;
        double argZ = 0.0;

        for(int i = 0; i < argArr.length; ++i) {
            argX += argArr[i].getX();
            argY += argArr[i].getY();
            argZ += argArr[i].getZ();
        }

    }

    public String getName() {
        return this.name;
    }

    public Particle getCenter() {
        Particle center = new Particle("Center");
        double argX = 0.0;
        double argY = 0.0;
        double argZ = 0.0;

        for(int i = 0; i < this.particlesArr.length; ++i) {
            argX += this.particlesArr[i].getX();
            argY += this.particlesArr[i].getY();
            argZ += this.particlesArr[i].getZ();
        }

        return center;
    }

    public Particle[] getParticles() {
        return this.particlesArr;
    }

    public int getParticlesNumber() {
        return this.particlesNumber;
    }

    public void setName(String argName) {
        this.name = argName;
    }

    public void setParticles(Particle[] argArr) {
        this.particlesArr = argArr;
    }

    public void setParticlesNumber(int argNum) {
        this.particlesNumber = argNum;
    }
}
