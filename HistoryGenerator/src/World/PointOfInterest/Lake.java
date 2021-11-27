package World.PointOfInterest;

import World.Rivers.River;
import World.Territory.Biome.Biome;

import javax.swing.text.html.Option;
import java.util.Optional;

public class Lake extends POI {
    private final Optional<RiverSegment> river;
    public Lake(final String name, final Optional<RiverSegment> river, final Biome biome) {
        super(name);
        this.river = river;
    }

    public final Optional<RiverSegment> getRiver() {
        return river;
    }

}
