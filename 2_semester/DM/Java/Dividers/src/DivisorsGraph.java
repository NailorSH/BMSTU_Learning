import java.util.ArrayList;
import java.util.List;

public class DivisorsGraph {
    private final List<Long> dividers;
    private final List<Pair> graph;
    private final String graphDOT;

    public DivisorsGraph(long x) {
        this.dividers = findDividers(x);
        this.graph = new ArrayList<>();
        this.graphDOT = buildDOTGraph();
    }

    private List<Long> findDividers(long x) {
        List<Long> result = new ArrayList<>();
        for (long d = 1; d <= x / 2; d++) {
            if (x % d == 0) {
                result.add(d);
            }
        }
        result.add(x);

        return result;
    }

    private String buildDOTGraph () {
        for (int i = 0; i < dividers.size(); i++) {
            for (int j = i + 1; j < dividers.size(); j++) {
                if (dividers.get(j) % dividers.get(i) == 0) {
                    boolean isDividerOfDivider = false;
                    for (int k = i + 1; k < j; k++) {
                        if (dividers.get(j) % dividers.get(k) == 0 && dividers.get(k) % dividers.get(i) == 0) {
                            isDividerOfDivider = true;
                            break;
                        }
                    }
                    if (!isDividerOfDivider) {
                        graph.add(new Pair(dividers.get(i), dividers.get(j)));
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder("graph {\n");
        for (var i : graph) {
            result.append(i.getFirst()).append(" -- ").append(i.getSecond()).append("\n");        }
        result.append("}");

        return result.toString();
    }

    public List<Long> getDividers() {
        return dividers;
    }

    public String getGraphDOT() {
        return graphDOT;
    }
}
