package World;

import World.Territory.Territory;

import java.util.ArrayList;

public class Biome implements IBiome {
    final ArrayList<String> locations;
    final String type;

    public Biome(Territory ter) {
        this.locations = new ArrayList<String>();
        this.type = ter.getCode();
    }
}
