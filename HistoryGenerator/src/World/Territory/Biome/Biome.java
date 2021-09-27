package World.Territory.Biome;

public enum Biome {
    ALPINETUNDRA(Biome.ALPTUNDRA_CODE, "Alpine Tundra", 0.4f),
    BADLANDS(Biome.BADLANDS_CODE, "Badlands", 0.5f),
    DECIDUOUSFOREST(Biome.DECFOREST_CODE, "Deciduous Forest", 0.1f),
    DECIDUOUSHILLS(Biome.DECHILLS_CODE, "Deciduous Hills", 0.2f),
    DECIDUOUSMOUNTAIN(Biome.DECMOUNTAIN_CODE, "Deciduous Mountain", 0.4f),
    GLACIER(Biome.GLACIER_CODE, "Glacier", 0.4f),
    GRASSLAND(Biome.GRASSLAND_CODE, "Grassland", 0.1f),
    GRASSHILLS(Biome.GRASSHILLS_CODE, "Grassy Hills", 0.2f),
    JUNGLE(Biome.JUNGLE_CODE, "Jungle", 0.2f),
    JUNGLEHILLS(Biome.JUNGLEHILLS_CODE, "Jungle Hills", 0.3f),
    JUNGLEMOUNTAIN(Biome.JUNGLEMOUNTAIN_CODE, "Jungle Mountain", 0.4f),
    MOUNTAIN(Biome.MOUNTAIN_CODE, "Mountain", 0.4f),
    MOUNTAINTAIGA(Biome.MOUNTAINTAIGA_CODE, "Mountain Taiga", 0.4f),
    OCEAN(Biome.OCEAN_CODE, "Ocean", 0f),
    SANDYDESERT(Biome.SANDYDESERT_CODE, "Sandy Desert", 0.1f),
    SAVANNA(Biome.SAVANNA_CODE, "Savanna", 0.1f),
    SHRUBLAND(Biome.SHRUBLAND_CODE, "Shrubland", 0.1f),
    SHRUBLANDHILLS(Biome.SHRUBLANDHILLS_CODE, "Shrubland Hills", 0.2f),
    SWAMP(Biome.SWAMP_CODE, "Swamp", 0.05f),
    TAIGA(Biome.TAIGA_CODE, "Taiga", 0.1f),
    TAIGAHILLS(Biome.TAIGAHILLS_CODE, "Taiga Hills", 0.2f),
    TUNDRA(Biome.TUNDRA_CODE, "Tundra", 0.1f),
    TUNDRAHILLS(Biome.TUNDRAHILLS_CODE, "Tundra Hills", 0.2f);


    public static final String ALPTUNDRA_CODE = "AT";
    public static final String BADLANDS_CODE = "BD";
    public static final String DECFOREST_CODE = "DF";
    public static final String DECHILLS_CODE = "DH";
    public static final String DECMOUNTAIN_CODE = "DM";
    public static final String GLACIER_CODE = "GM";
    public static final String GRASSLAND_CODE = "GL";
    public static final String GRASSHILLS_CODE = "GH";
    public static final String JUNGLE_CODE = "JG";
    public static final String JUNGLEHILLS_CODE = "JH";
    public static final String JUNGLEMOUNTAIN_CODE = "JM";
    public static final String MOUNTAIN_CODE = "MN";
    public static final String MOUNTAINTAIGA_CODE = "MT";
    public static final String OCEAN_CODE = "OC";
    public static final String SANDYDESERT_CODE = "SD";
    public static final String SAVANNA_CODE = "SV";
    public static final String SHRUBLAND_CODE = "SB";
    public static final String SHRUBLANDHILLS_CODE = "SH";
    public static final String SWAMP_CODE = "SW";
    public static final String TAIGA_CODE = "TG";
    public static final String TAIGAHILLS_CODE = "TH";
    public static final String TUNDRA_CODE = "TU";
    public static final String TUNDRAHILLS_CODE = "UH";

    private final String code;
    private final String nameText;
    private final float caveChance;

    Biome(String code, String name, float caveChance){
        this.code = code;
        this.nameText = name;
        this.caveChance = caveChance;
    }

    public String getCode() { return this.code; }
    public String getName() {return this.nameText;}
    public float getCaveChance() { return this.caveChance; }


}
