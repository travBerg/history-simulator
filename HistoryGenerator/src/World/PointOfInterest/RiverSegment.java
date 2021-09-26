package World.PointOfInterest;

public class RiverSegment extends POI{
    final int riverId;
    final String name;
    //location of river source
    final String in;
    //location river flows to
    final String out;

    public RiverSegment(final int riverId, final String name, final String in, final String out){
        this.riverId = riverId;
        this.name = name;
        this.in = in;
        this.out = out;
    }
}
