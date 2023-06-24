import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class State implements Comparable<State> {
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

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.i, other.i);
    }
}

public class Main {
    public static void main(String[] args) {
        int n1, m1, q10;
        int n2, m2, q20;
        int num = 0;

        Scanner scanner = new Scanner(System.in);

        n1 = scanner.nextInt();
        m1 = scanner.nextInt();
        q10 = scanner.nextInt();

        State[] automat1 = new State[n1];
        for (int i = 0; i < n1; i++) {
            automat1[i] = new State(i, m1);
        }

        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < m1; j++) {
                int t = scanner.nextInt();
                automat1[i].trans[j] = automat1[t];
            }
        }

        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < m1; j++) {
                String s = scanner.next();
                automat1[i].outs[j] = s;
            }
        }

        State[] min_automat1 = AufenkampHohn(automat1);

        for (State q : min_automat1) {
            boolean fl = true;
            for (int i = 0; i < m1; i++) {
                if (!q.outs[i].equals(automat1[q10].outs[i])) {
                    fl = false;
                    break;
                }
            }
            if (fl) {
                DFS(q, num);
                break;
            }
        }

        Arrays.sort(min_automat1);

        num = 0;

        n2 = scanner.nextInt();
        m2 = scanner.nextInt();
        q20 = scanner.nextInt();

        State[] automat2 = new State[n2];
        for (int i = 0; i < n2; i++) {
            automat2[i] = new State(i, m2);
        }

        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < m2; j++) {
                int t = scanner.nextInt();
                automat2[i].trans[j] = automat2[t];
            }
        }

        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < m2; j++) {
                String s = scanner.next();
                automat2[i].outs[j] = s;
            }
        }

        State[] min_automat2 = AufenkampHohn(automat2);

        for (State q : min_automat2) {
            boolean fl = true;
            for (int i = 0; i < m2; i++) {
                if (!q.outs[i].equals(automat2[q20].outs[i])) {
                    fl = false;
                    break;
                }
            }
            if (fl) {
                DFS(q, num);
                break;
            }
        }

        Arrays.sort(min_automat2);

        if (min_automat1.length != min_automat2.length || m1 != m2) {
            System.out.println("NOT EQUAL");
            System.exit(0);
        } else {
            for (int i = 0; i < min_automat1.length; i++) {
                for (int j = 0; j < m1; j++) {
                    if (min_automat1[i].trans[j].i != min_automat2[i].trans[j].i ||
                            !min_automat1[i].outs[j].equals(min_automat2[i].outs[j])) {
                        System.out.println("NOT EQUAL");
                        System.exit(0);
                    }
                }
            }
        }

        System.out.println("EQUAL");
    }

    public static void DFS(State s, int num) {
        s.i = num;
        num++;
        s.marked = true;

        for (int i = 0; i < s.outs.length; i++) {
            if (!s.trans[i].marked) {
                DFS(s.trans[i], num);
            }
        }
    }

    public static void Union(State x, State y) {
        State root_x = Find(x);
        State root_y = Find(y);
        if (root_x.depth < root_y.depth) {
            root_x.parent = root_y;
        } else {
            root_y.parent = root_x;
            if (root_x.depth == root_y.depth && root_x != root_y) {
                root_x.depth++;
            }
        }
    }

    public static State Find(State x) {
        if (x.parent == x) {
            return x;
        } else {
            x.parent = Find(x.parent);
            return Find(x.parent);
        }
    }

    public static State[] AufenkampHohn(State[] automat) {
        int n = automat.length;
        int m = automat[0].outs.length;
        int m1;
        List<State> pi = new ArrayList<>(n);

        Split1(automat, m, pi);
        m1 = pi.size();

        while (true) {
            int m2;
            Split(automat, m, pi);
            m2 = pi.size();
            if (m1 == m2) {
                break;
            }
            m1 = m2;
        }

        State[] ans = new State[m1];
        int index = 0;
        for (State q : automat) {
            State qt = pi.get(q.i);
            if (!find(ans, qt)) {
                ans[index] = qt;
                for (int i = 0; i < m; i++) {
                    qt.trans[i] = pi.get(q.trans[i].i);
                    qt.outs[i] = q.outs[i];
                }
                index++;
            }
        }

        return ans;
    }

    public static void Split1(State[] automat, int m, List<State> pi) {
        for (State q : automat) {
            q.parent = q;
            q.depth = 0;
        }
        pi.clear();

        for (State q : automat) {
            boolean found = false;
            for (State p : pi) {
                if (isEqual(q, p, m)) {
                    Union(q, p);
                    found = true;
                    break;
                }
            }
            if (!found) {
                pi.add(Find(q));
            }
        }
    }

    public static void Split(State[] automat, int m, List<State> pi) {
        for (State q : automat) {
            q.parent = q;
            q.depth = 0;
        }

        for (int i = 0; i < automat.length - 1; i++) {
            for (int j = i + 1; j < automat.length; j++) {
                if (pi.get(automat[i].i) == pi.get(automat[j].i) && Find(automat[i]) != Find(automat[j])) {
                    boolean eq = true;
                    for (int c = 0; c < m; c++) {
                        State w1 = automat[i].trans[c];
                        State w2 = automat[j].trans[c];
                        if (pi.get(w1.i) != pi.get(w2.i)) {
                            eq = false;
                            break;
                        }
                    }
                    if (eq) {
                        Union(automat[i], automat[j]);
                    }
                }
            }
        }

        pi.clear();
        for (State q : automat) {
            pi.add(Find(q));
        }
    }

    public static boolean find(State[] automat, State q) {
        for (State s : automat) {
            if (s == q) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEqual(State q1, State q2, int m) {
        for (int i = 0; i < m; i++) {
            if (!q1.outs[i].equals(q2.outs[i])) {
                return false;
            }
        }
        return true;
    }
}
