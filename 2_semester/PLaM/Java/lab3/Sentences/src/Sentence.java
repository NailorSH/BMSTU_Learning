public class Sentence implements Comparable<Sentence> {
    private String sentence;

    public Sentence(String sentence) {
        this.sentence = sentence;
    }

    public String toString() {
        return sentence;
    }

    public int compareTo(Sentence other) {
        int maxLength = getMaxLength(this.sentence);
        int otherMaxLength = getMaxLength(other.sentence);
        return Integer.compare(otherMaxLength, maxLength);
    }

    private int getMaxLength(String sentence) {
        String[] words = sentence.split(" ");
        int maxLength = 0;
        for (String word : words) {
            if (word.length() > maxLength) {
                maxLength = word.length();
            }
        }
        return maxLength;
    }
}