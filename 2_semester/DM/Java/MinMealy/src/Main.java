import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int q0 = scanner.nextInt();

        MealyMachine machine = new MealyMachine(n, m, q0);
        machine.readTransitionMatrix(scanner);
        machine.readOutputsMatrix(scanner);

        System.out.println(machine.getMinMealy());
    }
}