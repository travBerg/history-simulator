package World.Resources;

public enum Resource {
    WATER("water",true);

    final String name;
    final boolean potable;

    Resource(final String name, final boolean potable) {
        this.name = name;
        this.potable = potable;
    }
}
