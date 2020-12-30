package World.Territory;

import World.World;

public interface ITerritory {
    //The biome code for the territory
    public String getCode();
    //The name of the territory
    public String getName();
    //The location of the territory
    public String getLocation();
    //Determine if territory has been discovered
    public boolean isDiscovered();
}
