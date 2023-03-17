import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class PrefixList implements Iterable<String> {
    private StringBuilder s;
    private String w;
    private List<Integer> indexes;

    public PrefixList(StringBuilder s, String w) {
        this.s = s;
        this.w = w;
        this.indexes = new ArrayList<>();
        findSubstringIndexes();
    }

    private void findSubstringIndexes() {
        int index = s.indexOf(w);
        while (index >= 0) {
            indexes.add(index);
            index = s.indexOf(w, index + 1);
        }
    }

    public Iterator<String> iterator() {
        return new PrefixIterator();
    }

    private class PrefixIterator implements Iterator<String> {
        private int pos;

        public PrefixIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < indexes.size();
        }

        public String next() {
            int index = indexes.get(pos++);
            return s.substring(index);
        }
    }
}
