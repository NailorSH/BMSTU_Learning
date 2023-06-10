import java.util.HashMap;
import java.util.Map;

public class Connection {
    int volume;
    Map<Vertex, Boolean> firstPart;

    Map<Vertex, Boolean> secondPart;

    Connection() {
        firstPart = new HashMap<>();
        secondPart = new HashMap<>();
    }
}