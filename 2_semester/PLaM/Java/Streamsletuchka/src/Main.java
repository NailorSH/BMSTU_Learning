import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        IntegerArray arr1 = new IntegerArray(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), 3);

        IntegerArray arr2 = new IntegerArray(new ArrayList<>(Arrays.asList(7, 222, 4, 65, 87, 666, 445)), 3);

        System.out.println(arr1);
        System.out.println(arr1.getAllSum());

        System.out.println();

        System.out.println(arr2);
        System.out.println(arr2.getAllSum());
    }
}

class IntegerArray{
    private ArrayList<Integer> array;
    private int k;

    public IntegerArray(ArrayList<Integer> x, int k){
        this.array = x;
        this.k = k;
    }

    public int getAllSum(){
        return array.stream()
                .filter(x -> x%k == 0)
                .reduce(0, Integer::sum);
    }

    public String toString() {
        return array.toString() + "; K = " + k;
    }
}