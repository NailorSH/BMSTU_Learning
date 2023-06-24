public class State implements Comparable<State> {
    int i;
    State parent;
    int depth;
    boolean marked;
    State[] trans;
    String[] outs;

    State(int i, int m) {
        this.i = i;
        this.parent = null;
        this.depth = 0;
        this.marked = false;
        this.trans = new State[m];
        this.outs = new String[m];
    }

    State(int i, State parent, int depth, boolean marked, int m) {
        this.i = i;
        this.parent = parent;
        this.depth = depth;
        this.marked = marked;
        this.trans = new State[m];
        this.outs = new String[m];
    }

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.i, other.i);
    }
}