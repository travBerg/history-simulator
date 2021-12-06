package World.Resources;

public enum Resource {
    WATER("water",true);

    final private String name;
    final private boolean potable;

    Resource(final String name, final boolean potable) {
        this.name = name;
        this.potable = potable;
    }

    public final String getName(){
        return name;
    }
}
