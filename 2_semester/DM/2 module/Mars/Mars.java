import java.util.*;
public class Mars {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Expedition expedition = new Expedition(n);
        expedition.readGraph(scanner);
        expedition.printMinimalGroup();
    }
}

class Expedition {
    private final Vertex[] graph;
    private final int number;
    private Map<Vertex, Boolean> minimalGroup;

    public Expedition(int n) {
        number = n;
        graph = new Vertex[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new Vertex(i);
        }
    }

    public void readGraph(Scanner scanner) {
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                String consistency = scanner.next();
                if (consistency.equals("+")) {
                    graph[i].edges.add(graph[j]);
                }
            }
        }
    }

    private void findMinimalGroup() {
        List<Connection> connections = new ArrayList<>();
        for (var vertex : graph) {
            if (!vertex.visited) {
                vertex.group = 1;
                Connection component = new Connection();
                connections.add(component);
                dfs(vertex, component);
            }
        }
        List<List<Boolean>> combinations = generateCombinations(connections.size());
        int[] sumsOfCombinations = new int[combinations.size()];
        int minimalSum = number;
        for (int i = 0; i < combinations.size(); i++) {
            int sum = 0;
            for (int j = 0; j < connections.size(); j++) {
                if (!combinations.get(i).get(j)) {
                    sum -= connections.get(j).volume;
                } else {
                    sum += connections.get(j).volume;
                }
            }
            sum = Math.abs(sum);

            if (sum < minimalSum) {
                minimalSum = sum;
            }
            sumsOfCombinations[i] = sum;
        }
        minimalGroup = new HashMap<>();
        for (int i = 0; i < combinations.size(); i++) {
            if (sumsOfCombinations[i] == minimalSum) {
                Map<Vertex, Boolean> mergedSet = new HashMap<>();
                for (int j = 0; j < connections.size(); j++) {
                    Map<Vertex, Boolean> subset = connections.get(j).secondPart;
                    if (combinations.get(i).get(j)) {
                        subset = connections.get(j).firstPart;
                    }
                    mergedSet.putAll(subset);
                }

                if (minimalGroup.size() == 0 || compare(mergedSet, minimalGroup)) {
                    minimalGroup = mergedSet;
                }
            }
        }
    }

    public boolean compare(Map<Vertex, Boolean> first, Map<Vertex, Boolean> second) {
        if (first.size() != second.size()) {
            return first.size() < second.size();
        }
        List<Vertex> firstList = new ArrayList<>(first.keySet());
        List<Vertex> secondList = new ArrayList<>(second.keySet());

        firstList.sort(Comparator.comparingInt(v -> v.index));
        secondList.sort(Comparator.comparingInt(v -> v.index));

        for (int i = 0; i < firstList.size(); i++) {
            if (firstList.get(i).index < secondList.get(i).index) {
                return true;
            }
            if (firstList.get(i).index > secondList.get(i).index) {
                return false;
            }
        }
        return false;
    }

    public List<Vertex> getMinimalGroup() {
        try {
            findMinimalGroup();
            List<Vertex> minimalsList = new ArrayList<>(minimalGroup.keySet());
            minimalsList.sort(Comparator.comparingInt(a -> a.index));
            return minimalsList;
        } catch (RuntimeException e) {
            return null;
        }

    }

    public void printMinimalGroup() {
        try {
            for (Vertex vertex : getMinimalGroup()) {
                System.out.print((vertex.index + 1) + " ");
            }
            System.out.println();
        } catch (NullPointerException e) {
            System.out.println("No solution");
        }
    }

    private static void dfs(Vertex vertex, Connection component) {
        vertex.visited = true;
        if (vertex.group == -1) {
            component.firstPart.put(vertex, true);
        } else {
            component.secondPart.put(vertex, true);
        }
        component.volume += vertex.group;
        for (Vertex u : vertex.edges) {
            if (vertex.group * u.group == 1) {
                throw new RuntimeException("no solution");
            }
            if (!u.visited) {
                u.group = -vertex.group;
                dfs(u, component);
            }
        }
    }

    private static List<List<Boolean>> generateCombinations(int n) {
        List<List<Boolean>> combinations = new ArrayList<>();
        generateCombinationInner(new ArrayList<>(), 0, n, combinations);
        return combinations;
    }

    private static void generateCombinationInner(List<Boolean> mix, int i, int n, List<List<Boolean>> combinations) {
        if (i != n) {
            List<Boolean> trueEndingMix = new ArrayList<>(mix);
            List<Boolean> falseEndingMix = new ArrayList<>(mix);
            trueEndingMix.add(true);
            falseEndingMix.add(false);
            generateCombinationInner(trueEndingMix, i + 1, n, combinations);
            generateCombinationInner(falseEndingMix, i + 1, n, combinations);
        } else {
            combinations.add(new ArrayList<>(mix));
        }
    }
}

class Connection {
    int volume;
    Map<Vertex, Boolean> firstPart;

    Map<Vertex, Boolean> secondPart;

    Connection() {
        firstPart = new HashMap<>();
        secondPart = new HashMap<>();
    }
}

class Vertex {
    List<Vertex> edges;
    int group;
    int index;
    boolean visited;

    public Vertex(int index) {
        this.index = index;
        edges = new ArrayList<>();
        visited = false;
    }
}