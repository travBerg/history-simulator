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
        this.neighbors = TerritoryManager.addNeighbors(location, size);
        this.size = size;
        this.discovered = false;
    }

    @Override
    public boolean isDiscovered() { return discovered; }

    @Override
    public String getLocation() { return location; }

    @Override
    public ArrayList<String> getNeighbors() { return neighbors; }

}
