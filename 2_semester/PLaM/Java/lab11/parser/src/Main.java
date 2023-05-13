import java.util.Arrays;
import java.util.Scanner;

/*
* <Expr>::= ( cons <List> ) | nil | NUMBER
* <List>::= <Expr> <Tail>
* <Tail>::= <List> | ε
*
* (cons 10 (cons 1) nil)
* */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String source = scanner.nextLine();
        String source = "(cons 10     (cons 1) \n nil)";
        String[] words = source.trim().split("\\s+");

//        String[] words = Arrays.stream(source.trim().split(" "))
//                .filter(word -> !word.isEmpty())
//                .toArray(String[]::new);

//        int news = 0;
//        for (String word: words) {
//            if(word.equals("\n")) news++;
//        }
        System.out.println(Arrays.toString(words));
//        System.out.println(news);
    }
}