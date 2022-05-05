package WordGenerator;

import javafx.util.Pair;

import java.util.List;

public class Gram {
    private final int total;
    private final List<Pair<String, Integer>> nextLetters;

    public Gram (final int total, final List<Pair<String, Integer>> next) {
        this.total = total;
        this.nextLetters = next;
    }

    public int getTotal() { return total; }
    public List<Pair<String, Integer>> getNextLetters() { return nextLetters; }
}
