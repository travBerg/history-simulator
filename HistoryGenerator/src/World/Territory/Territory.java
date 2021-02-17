package World.Territory;

import TerrainGenerator.ITerrainMap;
import World.World;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class Territory implements ITerritory {
    final int seed;
    //row|column
    final String location;
    final ArrayList<String> neighbors;
    final int height;
    final int rain;
    final int temp;
    final int size;
    final boolean discovered;
    public Territory(final String location, final int seed, final String hrt, final int size) {
        this.seed = seed;
        this.location = location;
        this.height = Integer.parseInt(hrt.substring(0,1));
        this.rain = Integer.parseInt(hrt.substring(1,2));
        this.temp = Integer.parseInt(hrt.substring(2,3));
        this.neighbors = addNeighbors(location, size);
        this.size = size;
        this.discovered = false;
    }

    @Override
    public boolean isDiscovered() { return discovered; }

    @Override
    public String getLocation() { return location; }

    @Override
    public ArrayList<String> getNeighbors() { return neighbors; }

    public static Pair<String, String> parseLocation(final String location) {
        String array[] = location.split("\\|");
        Pair<String, String> nuLocation = new Pair<>(array[0], array[1]);
        return nuLocation;
    }

    public static ArrayList<String> addNeighbors(final String location, final int size) {
        Pair<String, String> coords = parseLocation(location);
        int row = Integer.parseInt(coords.getKey());
        int col = Integer.parseInt(coords.getValue());
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
