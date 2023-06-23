import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MealyMachine {
    int statesNumber;
    int inputAlphabetSize;
    int initialStateNumber;
    State[] Q;
    private int num;

    public MealyMachine(int n, int m, int q0) {
        statesNumber = n;
        inputAlphabetSize = m;
        initialStateNumber = q0;

        Q = new State[n];
        for (int i = 0; i < n; i++) {
            Q[i] = new State(i, m);
        }
    }

    public void readTransitionMatrix(Scanner scanner) {
        for (int i = 0; i < statesNumber; i++) {
            for (int j = 0; j < inputAlphabetSize; j++) {
                int t = scanner.nextInt();
                Q[i].trans[j] = Q[t];
            }
        }
    }

    public void readOutputsMatrix(Scanner scanner) {
        for (int i = 0; i < statesNumber; i++) {
            for (int j = 0; j < inputAlphabetSize; j++) {
                String s = scanner.next();
                Q[i].outs[j] = s;
            }
        }
    }

    public String getMinMealy() {
        num = 0;
        List<State> min_machine = AufenkampHohn(Q);
        for (State q : min_machine) {
            boolean fl = true;
            for (int i = 0; i < inputAlphabetSize; i++) {
                if (!q.outs[i].equals(Q[initialStateNumber].outs[i])) {
                    fl = false;
                    break;
                }
            }
            if (fl) {
                DFS(q);
                break;
            }
        }

        min_machine.sort(Comparator.comparingInt(a -> a.i));
        return buildGraphDOT(min_machine);
    }

    private String buildGraphDOT(List<State> min_machine) {
        StringBuilder result = new StringBuilder("digraph {\n");
        result.append("    rankdir = LR\n");
        for (State q : min_machine) {
            for (int i = 0; i < inputAlphabetSize; i++) {
                result.append("    ")
                        .append(q.i)
                        .append(" -> ")
                        .append(q.trans[i].i)
                        .append(" [label = \"")
                        .append((char) (i + 97))
                        .append("(")
                        .append(q.outs[i])
                        .append(")\"]\n");
            }
        }
        result.append("}");
        return String.valueOf(result);
    }

    private void DFS(State s) {
        s.i = num;
        num++;
        s.marked = true;

        for (int i = 0; i < inputAlphabetSize; i++) {
            if (!s.trans[i].marked) {
                DFS(s.trans[i]);
            }
        }
    }

    private List<State> AufenkampHohn(State[] machine) {
        int m1 = 0;
        State[] pi = new State[statesNumber];
        Split1(machine, pi);

        while (true) {
            int m2 = 0;
            Split(machine, pi);
            if (m1 == m2) {
                break;
            }
            m1 = m2;
        }

        List<State> ans = new ArrayList<>();

        for (State q : machine) {
            State qt = pi[q.i];
            if (!isContains(ans, qt)) {
                ans.add(qt);
                for (int i = 0; i < inputAlphabetSize; i++) {
                    qt.trans[i] = pi[q.trans[i].i];
                    qt.outs[i] = q.outs[i];
                }
            }
        }
        return ans;
    }

    private void Split1(State[] machine, State[] pi) {
        for (State q : machine) {
            q.parent = q;
            q.depth = 0;
        }
        for (int i = 0; i < statesNumber - 1; i++) {
            for (int j = i + 1; j < statesNumber; j++) {
                if (Find(machine[i]) != Find(machine[j])) {
                    boolean eq = true;
                    for (int c = 0; c < inputAlphabetSize; c++) {
                        if (!machine[i].outs[c].equals(machine[j].outs[c])) {
                            eq = false;
                            break;
                        }
                    }
                    if (eq) {
                        Union(machine[i], machine[j]);
                    }
                }
            }
        }
        for (State q : machine) {
            pi[q.i] = Find(q);
        }
    }

    private void Split(State[] machine, State[] pi) {
        for (State q : machine) {
            q.parent = q;
            q.depth = 0;
        }
        for (int i = 0; i < statesNumber - 1; i++) {
            for (int j = i + 1; j < statesNumber; j++) {
                if (pi[machine[i].i] == pi[machine[j].i] && Find(machine[i]) != Find(machine[j])) {
                    boolean eq = true;
                    for (int c = 0; c < inputAlphabetSize; c++) {
                        State w1 = machine[i].trans[c];
                        State w2 = machine[j].trans[c];
                        if (pi[w1.i] != pi[w2.i]) {
                            eq = false;
                            break;
                        }
                    }
                    if (eq) {
                        Union(machine[i], machine[j]);
                    }
                }
            }
        }
        for (State q : machine) {
            pi[q.i] = Find(q);
        }
    }

    private void Union(State x, State y) {
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

    private State Find(State x) {
        if (x.parent == x) {
            return x;
        } else {
            x.parent = Find(x.parent);
            return Find(x.parent);
        }
    }

    private boolean isContains(List<State> machine, State q) {
        for (State s : machine) {
            if (s == q) {
                return true;
            }
        }
        return false;
    }
}