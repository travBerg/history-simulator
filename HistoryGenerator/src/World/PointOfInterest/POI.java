package World.PointOfInterest;

import java.awt.*;

public abstract class POI {
    final String name;
    POI(final String name){
        this.name = name;
    }
    public String getName() { return name; }
}
