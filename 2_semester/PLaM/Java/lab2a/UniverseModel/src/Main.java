import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of particles in the universe: ");
        int num = in.nextInt();
        Particle[] particles = new Particle[num];

        String universeName;
        for(int i = 0; i < num; ++i) {
            System.out.printf("Particle #%d\n", i + 1);
            System.out.print("Does the particle have a name? (y/n): ");
            universeName = in.next();
            String currentName;
            if (universeName.equalsIgnoreCase("y")) {
                System.out.print("Enter particle name: ");
                currentName = in.next();
            } else {
                currentName = "Particle #" + (i + 1);
            }

            System.out.print("Enter particle coordinates: ");
            double currentX = in.nextDouble();
            double currentY = in.nextDouble();
            double currentZ = in.nextDouble();
            Particle any = new Particle(currentName, currentX, currentY, currentZ);
            particles[i] = any;
            System.out.println();
        }

        System.out.print("Does the universe have a name? (y/n): ");
        String answer = in.next();
        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Enter universe name: ");
            universeName = in.next();
        } else {
            universeName = "Universe #1";
        }

        Universe C = new Universe(universeName, particles);
        System.out.print("Enter the number of the particle to which you want to calculate the radius vector: ");
        int partNumber = in.nextInt();
        Vector RadiusVector = new Vector("RadiusVector", C.getCenter(), particles[partNumber - 1]);
        System.out.printf("RadiusVector: (%.2f, %.2f, %.2f)", RadiusVector.getX(), RadiusVector.getY(), RadiusVector.getZ());
        in.close();
    }
}