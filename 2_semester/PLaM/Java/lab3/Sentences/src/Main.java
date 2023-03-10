import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Sentence[] sentences = {
                new Sentence("The quick brown fox"),
                new Sentence("jumps over"),
                new Sentence("the lazy dog"),
                new Sentence("in the forest"),
                new Sentence("near the river")
        };
        Arrays.sort(sentences);
        for (Sentence sentence : sentences) {
            System.out.println(sentence);
        }
    }
}