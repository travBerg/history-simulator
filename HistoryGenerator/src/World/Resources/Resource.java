package World.Resources;

public enum Resource {
    //Resources
    BERRIES("berries", false, true, false),
    CACTUS("cactus fruit", false, true, true),
    CORN("corn", false, true, false),
    FRUIT("fruit", false, true, false),
    MUSHROOM("mushroom", false, true, false),
    NUTS("nuts", false, true, false),
    RICE("rice", false, true, true),
    WATER("water",true, false, false),
    WHEAT("wheat", false, true, false);

    final private String name;
    final private boolean potable;
    final private boolean edible;
    //If true, large amounts of fresh water (lake, river) increase available amount
    final private boolean freshWaterBoost;

    Resource(final String name, final boolean potable, final boolean edible, final boolean fresh) {
        this.name = name;
        this.potable = potable;
        this.edible = edible;
        this.freshWaterBoost = fresh;
    }

    public final String getName(){
        return name;
    }
    public final boolean isPotable() {return potable;}
    public final boolean isEdible() {return edible;}
    public final boolean isFreshBoosted() {return freshWaterBoost;}
}
