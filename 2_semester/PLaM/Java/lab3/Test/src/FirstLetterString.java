public class FirstLetterString implements Comparable<FirstLetterString> {
    private String s;

    public FirstLetterString(String s) {
        this.s = s;
    }

    public char charAt(int i) {
        return s.charAt(i);
    }

    public int length() {
        return s.length();
    }

    public String toString() {
        return s;
    }

    public int compareTo(FirstLetterString obj) {
        if (s.length() == 0 && obj.s.length() == 0) return 0;
        else if (s.length() == 0) return -1;
        else if (obj.s.length() == 0) return 1;
        else return s.charAt(0) - obj.s.charAt(0);
    }
}