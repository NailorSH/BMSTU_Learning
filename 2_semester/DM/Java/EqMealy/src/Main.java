import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n1 = scanner.nextInt();
        int m1 = scanner.nextInt();
        int q01 = scanner.nextInt();

        MealyMachine firstMachine = new MealyMachine(n1, m1, q01);
        firstMachine.readTransitionMatrix(scanner);
        firstMachine.readOutputsMatrix(scanner);

        int n2 = scanner.nextInt();
        int m2 = scanner.nextInt();
        int q02 = scanner.nextInt();

        MealyMachine secondMachine = new MealyMachine(n2, m2, q02);
        secondMachine.readTransitionMatrix(scanner);
        secondMachine.readOutputsMatrix(scanner);
        scanner.close();

        if(firstMachine.isEqual(secondMachine)) {
            System.out.println("EQUAL");
        } else {
            System.out.println("NOT EQUAL");
        }

    }
}