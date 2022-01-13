package World.Territory.Biome;

import World.Resources.Resource;
import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
interface MapProducer<K,V> {
    Map<K,V> getMap();
}

public enum Biome implements MapProducer{
    ALPINETUNDRA(Biome.ALPTUNDRA_CODE, "Alpine Tundra", Biome::initDefaultResMap,0.4f),
    BADLANDS(Biome.BADLANDS_CODE, "Badlands", Biome::initDefaultResMap,0.5f),
    DECIDUOUSFOREST(Biome.DECFOREST_CODE, "Deciduous Forest", Biome::initDefaultResMap,0.1f),
    DECIDUOUSHILLS(Biome.DECHILLS_CODE, "Deciduous Hills", Biome::initDefaultResMap, 0.2f),
    DECIDUOUSMOUNTAIN(Biome.DECMOUNTAIN_CODE, "Deciduous Mountain", Biome::initDefaultResMap, 0.4f),
    GLACIER(Biome.GLACIER_CODE, "Glacier", Biome::initDefaultResMap, 0.4f),
    GRASSLAND(Biome.GRASSLAND_CODE, "Grassland", Biome::initDefaultResMap, 0.1f),
    GRASSHILLS(Biome.GRASSHILLS_CODE, "Grassy Hills", Biome::initDefaultResMap, 0.2f),
    JUNGLE(Biome.JUNGLE_CODE, "Jungle", Biome::initDefaultResMap, 0.2f),
    JUNGLEHILLS(Biome.JUNGLEHILLS_CODE, "Jungle Hills", Biome::initDefaultResMap, 0.3f),
    JUNGLEMOUNTAIN(Biome.JUNGLEMOUNTAIN_CODE, "Jungle Mountain", Biome::initDefaultResMap, 0.4f),
    MOUNTAIN(Biome.MOUNTAIN_CODE, "Mountain", Biome::initDefaultResMap, 0.4f),
    MOUNTAINTAIGA(Biome.MOUNTAINTAIGA_CODE, "Mountain Taiga", Biome::initDefaultResMap, 0.4f),
    OCEAN(Biome.OCEAN_CODE, "Ocean", Biome::initDefaultResMap, 0f),
    SANDYDESERT(Biome.SANDYDESERT_CODE, "Sandy Desert", Biome::initDefaultResMap, 0.1f),
    SAVANNA(Biome.SAVANNA_CODE, "Savanna", Biome::initDefaultResMap, 0.1f),
    SHRUBLAND(Biome.SHRUBLAND_CODE, "Shrubland", Biome::initDefaultResMap, 0.1f),
    SHRUBLANDHILLS(Biome.SHRUBLANDHILLS_CODE, "Shrubland Hills", Biome::initDefaultResMap, 0.2f),
    SWAMP(Biome.SWAMP_CODE, "Swamp", Biome::initDefaultResMap, 0.05f),
    TAIGA(Biome.TAIGA_CODE, "Taiga", Biome::initDefaultResMap, 0.1f),
    TAIGAHILLS(Biome.TAIGAHILLS_CODE, "Taiga Hills", Biome::initDefaultResMap, 0.2f),
    TUNDRA(Biome.TUNDRA_CODE, "Tundra", Biome::initDefaultResMap, 0.1f),
    TUNDRAHILLS(Biome.TUNDRAHILLS_CODE, "Tundra Hills", Biome::initDefaultResMap, 0.2f);

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
    private final Map<Resource, Pair<Integer,Integer>> resources;

    Biome(final String code, final String name, final MapProducer<Resource, Pair<Integer,Integer>> resources, final float caveChance){
        this.code = code;
        this.nameText = name;
        this.caveChance = caveChance;
        this.resources = Collections.unmodifiableMap(resources.getMap());
    }

    public String getCode() { return this.code; }
    public String getName() {return this.nameText;}
    public float getCaveChance() { return this.caveChance; }
    public Map<Resource, Pair<Integer, Integer>> getResourceStats() {return resources;}

    //All resource pairs are <mean, std dev>
    private static Map<Resource, Pair<Integer, Integer>> initDefaultResMap() {
        return Stream.of(
            new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(1000, 300)),
            new AbstractMap.SimpleImmutableEntry<>(Resource.WHEAT, new Pair<>(200, 300))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<Resource, Pair<Integer, Integer>> getMap() {
        return resources;
    }
}
