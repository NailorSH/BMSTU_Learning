public class Test {
    public static void main(String[] args) {
        StringBuilder b = new StringBuilder(" qwerty1qffg ");
        String w = "q";
        PrefixList pref = new PrefixList(b, w);
        System.out.println(b);
        for (String s: pref) System.out.println(s);
        b.insert(1, 'x');
        System.out.println("-----");
        System.out.println(b);
        for (String s: pref) System.out.println(s);
        b.insert(5, 'q');
        System.out.println("-----");
        System.out.println(b);
        for (String s: pref) System.out.println(s);
    }
}