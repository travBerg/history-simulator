package World.Resources;

public enum Resource {
    //Resources
    BERRIES("berries", false, true),
    CACTUS("cactus fruit", false, true),
    CORN("corn", false, true),
    FRUIT("fruit", false, true),
    MUSHROOM("mushroom", false, true),
    NUTS("nuts", false, true),
    RICE("rice", false, true),
    WATER("water",true, false),
    WHEAT("wheat", false, true);

    final private String name;
    final private boolean potable;
    final private boolean edible;

    Resource(final String name, final boolean potable, final boolean edible) {
        this.name = name;
        this.potable = potable;
        this.edible = edible;
    }

    public final String getName(){
        return name;
    }
    public final boolean isPotable() {return potable;}
    public final boolean isEdible() {return edible;}
}
