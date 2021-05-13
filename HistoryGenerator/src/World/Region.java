package World;

import World.Territory.Territory;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Region implements IRegion {
    private final ArrayList<String> locations;
    private final String type;
    private final int index;
    private final String name;

    public Region(Territory ter, int idx, ArrayList<String> locations) {
        this.locations = locations;
        //locations.add(ter.getLocation());
        this.index = idx;
        this.type = ter.getCode();
        //TODO: Placeholder
        this.name = "Unnamed " + ter.getBiome() + " Region";
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    //TODO: These are actually territories so I should probably name them as such
    public ArrayList<String> getLocations() {
        return locations;
    }

    @Override
    public JSONObject asJSON() {
        JSONObject territoryJSON = new JSONObject();
        territoryJSON.put("locations", this.locations);
        territoryJSON.put("type", this.type);
        territoryJSON.put("index", this.index);
        territoryJSON.put("name", this.name);
        return territoryJSON;
    }
}
