package World;

import World.Territory.Biome.Biome;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public interface IRegion {
    public int getIndex();
    public String getName();
    public String getType();
    public ArrayList<String> getLocations();
    public Biome getBiome();
}
