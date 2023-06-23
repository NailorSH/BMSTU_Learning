public class Pair<T1, T2> {
    T1 K;
    T2 V;

    public Pair(T1 k, T2 v) {
        K = k;
        V = v;
    }

    public T1 getKey() {
        return K;
    }

    public T2 getValue() {
        return V;
    }
}