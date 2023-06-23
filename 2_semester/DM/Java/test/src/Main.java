import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class State {
    int i;
    State parent;
    int depth;
    boolean marked;
    State[] trans;
    String[] outs;

    State(int i, State parent, int depth, boolean marked, int m) {
        this.i = i;
        this.parent = parent;
        this.depth = depth;
        this.marked = marked;
        this.trans = new State[m];
        this.outs = new String[m];
    }
}

class Main {
    static int n, m, q0, num;

    public static void main(String[] args) {
        num = 0;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        q0 = scanner.nextInt();

        State[] automat = new State[n];
        for (int i = 0; i < n; i++) {
            automat[i] = new State(i, null, 0, false, m);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int t = scanner.nextInt();
                automat[i].trans[j] = automat[t];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                String s = scanner.next();
                automat[i].outs[j] = s;
            }
        }

        List<State> min_automat = AufenkampHohn(automat);

        for (State q : min_automat) {
            boolean fl = true;
            for (int i = 0; i < m; i++) {
                if (!q.outs[i].equals(automat[q0].outs[i])) {
                    fl = false;
                    break;
                }
            }
            if (fl) {
                DFS(q);
                break;
            }
        }

        min_automat.sort(Comparator.comparingInt(a -> a.i));

        System.out.println("digraph {");
        System.out.println("    rankdir = LR");
        for (State q : min_automat) {
            for (int i = 0; i < m; i++) {
                System.out.printf("    %d -> %d [label = \"%s(%s)\"]%n", q.i, q.trans[i].i, (char) (i + 97), );
            }
        }
        System.out.println("}");
    }

    static void DFS(State s) {
        s.i = num;
        num++;
        s.marked = true;

        for (int i = 0; i < m; i++) {
            if (!s.trans[i].marked) {
                DFS(s.trans[i]);
            }
        }
    }

    static void Union(State x, State y) {
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

    static State Find(State x) {
        if (x.parent == x) {
            return x;
        } else {
            x.parent = Find(x.parent);
            return Find(x.parent);
        }
    }

    static void Split1(State[] automat, int mt, State[] pi) {
        for (State q : automat) {
            q.parent = q;
            q.depth = 0;
        }
        mt = n;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Find(automat[i]) != Find(automat[j])) {
                    boolean eq = true;
                    for (int c = 0; c < m; c++) {
                        if (!automat[i].outs[c].equals(automat[j].outs[c])) {
                            eq = false;
                            break;
                        }
                    }
                    if (eq) {
                        Union(automat[i], automat[j]);
                        mt--;
                    }
                }
            }
        }
        for (State q : automat) {
            pi[q.i] = Find(q);
        }
    }

    static void Split(State[] automat, int mt, State[] pi) {
        for (State q : automat) {
            q.parent = q;
            q.depth = 0;
        }
        mt = n;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (pi[automat[i].i] == pi[automat[j].i] && Find(automat[i]) != Find(automat[j])) {
                    boolean eq = true;
                    for (int c = 0; c < m; c++) {
                        State w1 = automat[i].trans[c];
                        State w2 = automat[j].trans[c];
                        if (pi[w1.i] != pi[w2.i]) {
                            eq = false;
                            break;
                        }
                    }
                    if (eq) {
                        Union(automat[i], automat[j]);
                        mt--;
                    }
                }
            }
        }
        for (State q : automat) {
            pi[q.i] = Find(q);
        }
    }

    static List<State> AufenkampHohn(State[] automat) {
        int m1 = 0;
        State[] pi = new State[n];
        Split1(automat, m1, pi);

        while (true) {
            int m2 = 0;
            Split(automat, m2, pi);
            if (m1 == m2) {
                break;
            }
            m1 = m2;
        }

        List<State> ans = new ArrayList<>();

        for (State q : automat) {
            State qt = pi[q.i];
            if (!find(ans, qt)) {
                ans.add(qt);
                for (int i = 0; i < m; i++) {
                    qt.trans[i] = pi[q.trans[i].i];
                    qt.outs[i] = q.outs[i];
                }
            }
        }
        return ans;
    }

    static boolean find(List<State> automat, State q) {
        for (State s : automat) {
            if (s == q) {
                return true;
            }
        }
        return false;
    }
}
