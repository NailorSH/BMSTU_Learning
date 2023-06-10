import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Expedition expedition = new Expedition(n);
        expedition.readGraph(scanner);
        expedition.printMinimalGroup();
    }
}