import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long x = scanner.nextLong();
        if (x == 1) {
            System.out.println("graph {\n1\n}");
        } else {
            DivisorsGraph example = new DivisorsGraph(x);
            System.out.println(example.getGraphDOT());
        }
    }
}