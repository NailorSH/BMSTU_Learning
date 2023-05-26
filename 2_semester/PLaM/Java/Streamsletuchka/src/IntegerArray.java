//import java.util.ArrayList;
//
//public class IntegerArray{
//    private ArrayList<Integer> array;
//    private int k;
//
//    public IntegerArray(ArrayList<Integer> x, int k){
//        this.array = x;
//        this.k = k;
//    }
//
//    public int getAllSum(){
//        return array.stream()
//                .filter(x -> x%k == 0)
//                .reduce(0, Integer::sum);
//    }
//
//    public String toString() {
//        String result = array.toString() + "; K = " + k;
//        return result;
//    }
//}
