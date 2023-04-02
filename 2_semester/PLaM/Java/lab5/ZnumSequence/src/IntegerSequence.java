import java.util.*;
import java.util.stream.Stream;

public class IntegerSequence {

    private List<Integer> sequence;

    public IntegerSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    public Stream<Integer> getDigitStream() {
        return sequence.stream()
                .flatMap(x -> Arrays.stream(String.valueOf(x).split("")))
                .map(Integer::valueOf);
    }

    public Optional<Integer> getMaxDigitSum() {
        return sequence.stream()
                .max(Comparator.comparingInt(this::getDigitSum));
    }

    private int getDigitSum(int n) {
        return String.valueOf(n).chars()
                .map(Character::getNumericValue)
                .sum();
    }
}
