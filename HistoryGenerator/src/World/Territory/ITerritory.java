package World.Territory;

import World.World;

import java.util.ArrayList;

public interface ITerritory {
    //The biome code for the territory
    public String getCode();
    //The name of the territory
    public String getName();
    //The location of the territory
    public String getLocation();
    //Determine if territory has been discovered
    public boolean isDiscovered();
    //Get name of biome type
    public String getBiome();
    //Get the list of neighbors
    public ArrayList<String> getNeighbors();
}
