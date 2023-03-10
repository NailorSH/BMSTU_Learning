import java.util.Arrays;
public class Main{
    public static void main (String[] args) {
        FirstLetterString[] a = new FirstLetterString[] {
                new FirstLetterString("gamma"),
                new FirstLetterString("beta"),
                new FirstLetterString("alpha"),
        };
        Arrays.sort(a);
        for (FirstLetterString s : a) System.out.println(s);
    }
}