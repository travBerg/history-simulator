package World.Territory;

import World.Territory.Biome.Biome;
import World.World;

import java.util.ArrayList;

public interface ITerritory {
    //The biome code for the territory
    public String getName();
    //The location of the territory
    public String getLocation();
    //Determine if territory has been discovered
    public boolean isDiscovered();
    //Get name of biome type
    public Biome getBiome();
    //Get the list of neighbors
    public ArrayList<String> getNeighbors();
}
