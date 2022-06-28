package World;

import World.Territory.Biome.Biome;
import World.Territory.Territory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Region implements IRegion {
    //This here is the list of the locations of the territories
    private final ArrayList<String> locations;
    private final String type;
    private final Biome biome;
    private final int index;
    private final String name;
    private final String nameMeaning;
    private final Optional<String> discoverer;

    public Region(Territory ter, int idx, ArrayList<String> locations) {
        this.locations = locations;
        //locations.add(ter.getLocation());
        this.index = idx;
        this.type = ter.getBiome().getCode();
        this.biome = ter.getBiome();
        //TODO: Placeholder
        this.name = "Unnamed " + ter.getBiome().getName() + " Region";
        this.nameMeaning = name;
        this.discoverer = Optional.empty();
    }

    /**
     * Constructor for renaming a Region after discovery
     * @param name name of the biome
     * @param nameMeaning meaning of the name
     * @param discoverer the group that discovered the region
     * @param old the old unnamed region
     */
    public Region(final String name, final String nameMeaning, final String discoverer, final Region old) {
        this.locations = old.locations;
        this.index = old.index;
        this.type = old.type;
        this.biome = old.biome;
        this.name = name;
        this.nameMeaning = nameMeaning;
        this.discoverer = Optional.of(discoverer);
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
    public Biome getBiome() {return biome;}
}
