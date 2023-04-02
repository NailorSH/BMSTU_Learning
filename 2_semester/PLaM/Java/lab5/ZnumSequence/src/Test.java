import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        IntegerSequence sequence = new IntegerSequence(Arrays.asList(10, 0, 123, 5, 666));

        // Поток всех цифр
        Map<Integer, Long> digitCount = sequence.getDigitStream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        System.out.println("Цифры=Количество цифр: " + digitCount);

        // Число с максимальной суммой цифр
        sequence.getMaxDigitSum().ifPresent(max -> System.out.println("Максимальная сумма цифр в числе: " + max));
    }
}
