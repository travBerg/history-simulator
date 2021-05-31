package World.Territory;

import javafx.util.Pair;

import java.util.ArrayList;

public class TerritoryManager {
    public static Pair<Integer, Integer> parseLocation(final String location) {
        String array[] = location.split("\\|");
        final int row = Integer.parseInt(array[0]);
        final int col = Integer.parseInt(array[1]);
        Pair<Integer, Integer> nuLocation = new Pair<>(row, col);
        return nuLocation;
    }

    public static ArrayList<String> addNeighbors(final String location, final int size) {
        Pair<Integer, Integer> coords = parseLocation(location);
        int row = coords.getKey();
        int col = coords.getValue();
        ArrayList<String> neighbors = new ArrayList<>();

        boolean odd = (row % 2 != 0);
        if(odd) {
            //top, bottom, left, and all the rights
            for(int i = row - 1; i <= row + 1; i++) {
                for(int j = col - 1; j <= col + 1; j++){
                    if(!(i < row && j < col) && !(i > row && j < col) &&
                            j >= 0 && j < size && i >= 0 && i < size) {
                        neighbors.add(i + "|" + j);
                    }
                }
            }
        } else {
            //top, bottom, right, and all the lefts
            for(int i = row - 1; i <= row + 1; i++) {
                for(int j = col - 1; j <= col + 1; j++){
                    if(!(i < row && j > col) && !(i > row && j > col) &&
                            j >= 0 && j < size && i >= 0 && i < size) {
                        neighbors.add(i + "|" + j);
                    }
                }
            }
        }
        /*
        for(int i = row - 1; i <= row + 1; i++) {
            for(int j = col - 1; j <= col + 1; j++){
                if(j >= 0 && j < size && i >= 0 && i < size) {
                    neighbors.add(i + "|" + j);
                }
            }
        }*/
        return neighbors;
    }
}
