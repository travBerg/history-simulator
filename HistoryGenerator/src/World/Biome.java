package World;

import World.Territory.Territory;

import java.util.ArrayList;

public class Biome implements IBiome {
    final ArrayList<String> locations;
    final String type;
    final int index;

    public Biome(Territory ter, int idx) {
        this.locations = new ArrayList<String>();
        locations.add(ter.getLocation());
        this.index = idx;
        this.type = ter.getCode();
    }

    public int getIndex() {
        return this.index;
    }

    public void addLocation(String loc) {
        this.locations.add(loc);
    }

}
