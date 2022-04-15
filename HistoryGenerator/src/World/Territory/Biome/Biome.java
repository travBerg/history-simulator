package World.Territory.Biome;

import World.Animals.Animal;
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

@FunctionalInterface
interface AniMapProducer<K,V> {
    Map<K,V> getAniMap();
}

public enum Biome implements MapProducer, AniMapProducer{
    ALPINETUNDRA(Biome.ALPTUNDRA_CODE, "Alpine Tundra", Biome::initTundraResMap,0.4f, Biome::initOceanAniMap),
    BADLANDS(Biome.BADLANDS_CODE, "Badlands", Biome::initAridResMap,0.5f, Biome::initOceanAniMap),
    DECIDUOUSFOREST(Biome.DECFOREST_CODE, "Deciduous Forest", Biome::initDeciduousResMap,0.1f, Biome::initDefaultAnimalMap),
    DECIDUOUSHILLS(Biome.DECHILLS_CODE, "Deciduous Hills", Biome::initDeciduousResMap, 0.2f, Biome::initDefaultAnimalMap),
    DECIDUOUSMOUNTAIN(Biome.DECMOUNTAIN_CODE, "Deciduous Mountain", Biome::initDeciduousResMap, 0.4f, Biome::initMountainAniMap),
    GLACIER(Biome.GLACIER_CODE, "Glacier", Biome::initGlacierResMap, 0.4f, Biome::initOceanAniMap),
    GRASSLAND(Biome.GRASSLAND_CODE, "Grassland", Biome::initGrassResMap, 0.1f, Biome::initGrasslandAniMap),
    GRASSHILLS(Biome.GRASSHILLS_CODE, "Grassy Hills", Biome::initGrassResMap, 0.2f, Biome::initGrasslandAniMap),
    JUNGLE(Biome.JUNGLE_CODE, "Jungle", Biome::initJungleResMap, 0.2f, Biome::initDefaultAnimalMap),
    JUNGLEHILLS(Biome.JUNGLEHILLS_CODE, "Jungle Hills", Biome::initJungleResMap, 0.3f, Biome::initDefaultAnimalMap),
    JUNGLEMOUNTAIN(Biome.JUNGLEMOUNTAIN_CODE, "Jungle Mountain", Biome::initJungleResMap, 0.4f, Biome::initDefaultAnimalMap),
    MOUNTAIN(Biome.MOUNTAIN_CODE, "Mountain", Biome::initDefaultResMap, 0.4f, Biome::initMountainAniMap),
    MOUNTAINTAIGA(Biome.MOUNTAINTAIGA_CODE, "Mountain Taiga", Biome::initTaigaResMap, 0.4f, Biome::initMountainAniMap),
    OCEAN(Biome.OCEAN_CODE, "Ocean", Biome::initOceanResMap, 0f, Biome::initOceanAniMap),
    SANDYDESERT(Biome.SANDYDESERT_CODE, "Sandy Desert", Biome::initAridResMap, 0.1f, Biome::initOceanAniMap),
    SAVANNA(Biome.SAVANNA_CODE, "Savanna", Biome::initAridResMap, 0.1f, Biome::initDefaultAnimalMap),
    SHRUBLAND(Biome.SHRUBLAND_CODE, "Shrubland", Biome::initAridResMap, 0.1f, Biome::initMountainAniMap),
    SHRUBLANDHILLS(Biome.SHRUBLANDHILLS_CODE, "Shrubland Hills", Biome::initAridResMap, 0.2f, Biome::initMountainAniMap),
    SWAMP(Biome.SWAMP_CODE, "Swamp", Biome::initSwampResMap, 0.05f, Biome::initDefaultAnimalMap),
    TAIGA(Biome.TAIGA_CODE, "Taiga", Biome::initTaigaResMap, 0.1f, Biome::initDefaultAnimalMap),
    TAIGAHILLS(Biome.TAIGAHILLS_CODE, "Taiga Hills", Biome::initTaigaResMap, 0.2f, Biome::initDefaultAnimalMap),
    TUNDRA(Biome.TUNDRA_CODE, "Tundra", Biome::initTundraResMap, 0.1f, Biome::initOceanAniMap),
    TUNDRAHILLS(Biome.TUNDRAHILLS_CODE, "Tundra Hills", Biome::initTundraResMap, 0.2f, Biome::initOceanAniMap);

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
    private final Map<Animal, Pair<Integer,Integer>> animals;

    Biome(final String code, final String name, final MapProducer<Resource, Pair<Integer,Integer>> resources,
          final float caveChance, final AniMapProducer<Animal, Pair<Integer, Integer>> animals){
        this.code = code;
        this.nameText = name;
        this.caveChance = caveChance;
        this.resources = Collections.unmodifiableMap(resources.getMap());
        this.animals = Collections.unmodifiableMap(animals.getAniMap());
    }

    public String getCode() { return this.code; }
    public String getName() {return this.nameText;}
    public float getCaveChance() { return this.caveChance; }
    public Map<Resource, Pair<Integer, Integer>> getResourceStats() {return resources;}
    public Map<Animal, Pair<Integer, Integer>> getAnimals() {return animals;}

    /**
     * Here are the functions for making the resource stat maps
     * Pair is <mean, std dev>
     * vvv
    */
    private static Map<Resource, Pair<Integer, Integer>> initDefaultResMap() {
        return Stream.of(
            new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(1000, 300)),
            new AbstractMap.SimpleImmutableEntry<>(Resource.WHEAT, new Pair<>(200, 300))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer,Integer>> initAridResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(-50, 100)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.CACTUS, new Pair<>(100, 50))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer,Integer>> initDeciduousResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(1000, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.BERRIES, new Pair<>(200, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.NUTS, new Pair<>(200, 200)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.MUSHROOM, new Pair<>(100, 350))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer, Integer>> initGlacierResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(Integer.MAX_VALUE, 0))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer,Integer>> initGrassResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(1000, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.WHEAT, new Pair<>(200, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.CORN, new Pair<>(200, 300))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer,Integer>> initJungleResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(1500, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.BERRIES, new Pair<>(100, 100)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.FRUIT, new Pair<>(300, 400)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.RICE, new Pair<>(100, 100)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.NUTS, new Pair<>(200, 200))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer,Integer>> initSwampResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(-100, 100)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.MUSHROOM, new Pair<>(200, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.RICE, new Pair<>(300, 300))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer,Integer>> initTaigaResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(1000, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.BERRIES, new Pair<>(150, 100)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.MUSHROOM, new Pair<>(200, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.NUTS, new Pair<>(300, 300))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer, Integer>> initTundraResMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, new Pair<>(1500, 300)),
                new AbstractMap.SimpleImmutableEntry<>(Resource.BERRIES, new Pair<>(100, 100))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Resource, Pair<Integer,Integer>> initOceanResMap() {
        return new HashMap<>();
    }

    /**
     * Here are the functions for making the animal stat maps
     * Pair is <mean, std dev>
     * vvv
     */

    private static Map<Animal, Pair<Integer,Integer>> initDefaultAnimalMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Animal.CHICKEN, new Pair<>(150, 30))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Animal, Pair<Integer,Integer>> initGrasslandAniMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Animal.COW, new Pair<>(200,50))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Animal, Pair<Integer,Integer>> initMountainAniMap() {
        return Stream.of(
                new AbstractMap.SimpleImmutableEntry<>(Animal.GOAT, new Pair<>(150, 30))
        ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<Animal, Pair<Integer,Integer>> initOceanAniMap() {
        return new HashMap<>();
    }


    @Override
    public Map<Resource, Pair<Integer, Integer>> getMap() {
        return resources;
    }

    @Override
    public Map<Animal, Pair<Integer, Integer>> getAniMap() { return animals; }
}
