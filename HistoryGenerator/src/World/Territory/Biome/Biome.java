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
    ALPINETUNDRA(Biome.ALPTUNDRA_CODE, "Alpine Tundra", Biome::initTundraResMap,0.4f, Biome::initOceanAniMap, 0.1f,
            TermConstants.ALPTUNDRA_TERMS, concatenate(SubBiomeConstants.MOUNTAIN_SUB, SubBiomeConstants.TUNDRA_SUB)),
    BADLANDS(Biome.BADLANDS_CODE, "Badlands", Biome::initAridResMap,0.5f, Biome::initOceanAniMap, 0.1f,
            TermConstants.BADLANDS_TERMS, concatenate(SubBiomeConstants.BADLANDS_SUB, SubBiomeConstants.ARID_SUB)),
    DECIDUOUSFOREST(Biome.DECFOREST_CODE, "Deciduous Forest", Biome::initDeciduousResMap,0.1f, Biome::initDefaultAnimalMap, 0.3f,
            TermConstants.DECFOREST_TERMS, SubBiomeConstants.FOREST_SUB),
    DECIDUOUSHILLS(Biome.DECHILLS_CODE, "Deciduous Hill", Biome::initDeciduousResMap, 0.2f, Biome::initDefaultAnimalMap, 0.3f,
            TermConstants.DECHILLS_TERMS, concatenate(SubBiomeConstants.FOREST_SUB, SubBiomeConstants.HILLS_SUB)),
    DECIDUOUSMOUNTAIN(Biome.DECMOUNTAIN_CODE, "Deciduous Mountain", Biome::initDeciduousResMap, 0.4f, Biome::initMountainAniMap, 0.1f,
            TermConstants.DECMOUNTAIN_TERMS, concatenate(SubBiomeConstants.FOREST_SUB, SubBiomeConstants.MOUNTAIN_SUB)),
    GLACIER(Biome.GLACIER_CODE, "Glacier", Biome::initGlacierResMap, 0.4f, Biome::initOceanAniMap, 0f,
            TermConstants.GLACIER_TERMS, SubBiomeConstants.GLACIER_SUB),
    GRASSLAND(Biome.GRASSLAND_CODE, "Grassland", Biome::initGrassResMap, 0.1f, Biome::initGrasslandAniMap, 0.3f,
            TermConstants.GRASSLAND_TERMS, SubBiomeConstants.GRASSLAND_SUB),
    GRASSHILLS(Biome.GRASSHILLS_CODE, "Grassy Hill", Biome::initGrassResMap, 0.2f, Biome::initGrasslandAniMap, 0.3f,
            TermConstants.GRASSHILLS_TERMS, SubBiomeConstants.HILLS_SUB),
    JUNGLE(Biome.JUNGLE_CODE, "Jungle", Biome::initJungleResMap, 0.2f, Biome::initDefaultAnimalMap, 0.2f,
            TermConstants.JUNGLE_TERMS, SubBiomeConstants.JUNGLE_SUB),
    JUNGLEHILLS(Biome.JUNGLEHILLS_CODE, "Jungle Hill", Biome::initJungleResMap, 0.3f, Biome::initDefaultAnimalMap, 0.2f,
            TermConstants.JUNGLEHILLS_TERMS, concatenate(SubBiomeConstants.JUNGLE_SUB, SubBiomeConstants.HILLS_SUB)),
    JUNGLEMOUNTAIN(Biome.JUNGLEMOUNTAIN_CODE, "Jungle Mountain", Biome::initJungleResMap, 0.4f, Biome::initDefaultAnimalMap, 0.2f,
            TermConstants.JUNGLEMOUNTAIN_TERMS, concatenate(SubBiomeConstants.JUNGLE_SUB, SubBiomeConstants.MOUNTAIN_SUB)),
    MOUNTAIN(Biome.MOUNTAIN_CODE, "Mountain", Biome::initDefaultResMap, 0.4f, Biome::initMountainAniMap, 0.1f,
            TermConstants.MOUNTAIN_TERMS, SubBiomeConstants.MOUNTAIN_SUB),
    MOUNTAINTAIGA(Biome.MOUNTAINTAIGA_CODE, "Mountain Taiga", Biome::initTaigaResMap, 0.4f, Biome::initMountainAniMap, 0.1f,
            TermConstants.MOUNTAINTAIGA_TERMS, concatenate(SubBiomeConstants.MOUNTAIN_SUB, SubBiomeConstants.TAIGA_SUB, SubBiomeConstants.FOREST_SUB)),
    OCEAN(Biome.OCEAN_CODE, "Ocean", Biome::initOceanResMap, 0f, Biome::initOceanAniMap, 0f,
            TermConstants.OCEAN_TERMS, SubBiomeConstants.OCEAN_SUB),
    SANDYDESERT(Biome.SANDYDESERT_CODE, "Sandy Desert", Biome::initAridResMap, 0.1f, Biome::initOceanAniMap, 0.1f,
            TermConstants.SANDYDESERT_TERMS, concatenate(SubBiomeConstants.ARID_SUB, SubBiomeConstants.DESERT_SUB)),
    SAVANNA(Biome.SAVANNA_CODE, "Savanna", Biome::initAridResMap, 0.1f, Biome::initDefaultAnimalMap, 0.3f,
            TermConstants.SAVANNA_TERMS, concatenate(SubBiomeConstants.GRASSLAND_SUB, SubBiomeConstants.SAVANNA_SUB)),
    SHRUBLAND(Biome.SHRUBLAND_CODE, "Shrubland", Biome::initAridResMap, 0.1f, Biome::initMountainAniMap, 0.2f,
            TermConstants.SHRUBLAND_TERMS, concatenate(SubBiomeConstants.ARID_SUB, SubBiomeConstants.SHRUBLAND_SUB)),
    SHRUBLANDHILLS(Biome.SHRUBLANDHILLS_CODE, "Shrubland Hill", Biome::initAridResMap, 0.2f, Biome::initMountainAniMap, 0.2f,
            TermConstants.SHRUBLANDHILLS_TERMS, concatenate(SubBiomeConstants.ARID_SUB, SubBiomeConstants.SHRUBLAND_SUB, SubBiomeConstants.HILLS_SUB)),
    SWAMP(Biome.SWAMP_CODE, "Swamp", Biome::initSwampResMap, 0.05f, Biome::initDefaultAnimalMap, 0.2f,
            TermConstants.SWAMP_TERMS, SubBiomeConstants.SWAMP_SUB),
    TAIGA(Biome.TAIGA_CODE, "Taiga", Biome::initTaigaResMap, 0.1f, Biome::initDefaultAnimalMap, 0.2f,
            TermConstants.TAIGA_TERMS, concatenate(SubBiomeConstants.TAIGA_SUB, SubBiomeConstants.FOREST_SUB)),
    TAIGAHILLS(Biome.TAIGAHILLS_CODE, "Taiga Hill", Biome::initTaigaResMap, 0.2f, Biome::initDefaultAnimalMap, 0.2f,
            TermConstants.TAIGAHILLS_TERMS, concatenate(SubBiomeConstants.TAIGA_SUB, SubBiomeConstants.FOREST_SUB, SubBiomeConstants.HILLS_SUB)),
    TUNDRA(Biome.TUNDRA_CODE, "Tundra", Biome::initTundraResMap, 0.1f, Biome::initOceanAniMap, 0.1f,
            TermConstants.TUNDRA_TERMS, SubBiomeConstants.TUNDRA_SUB),
    TUNDRAHILLS(Biome.TUNDRAHILLS_CODE, "Tundra Hill", Biome::initTundraResMap, 0.2f, Biome::initOceanAniMap, 0.1f,
            TermConstants.TUNDRAHILLS_TERMS, concatenate(SubBiomeConstants.TUNDRA_SUB, SubBiomeConstants.HILLS_SUB));

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

    private static class TermConstants {
        //TODO: Separate into nouns and adjectives
        public static final String[] ALPTUNDRA_TERMS = new String[]{"alpine", "mountain", "snow", "grey", "cloud", "cold"};
        public static final String[] BADLANDS_TERMS = new String[]{"rock", "sun", "shadow", "dust", "clay", "sand"};
        public static final String[] DECFOREST_TERMS = new String[]{"tree", "green", "leaf", "forest", "root", "wood"};
        public static final String[] DECHILLS_TERMS = new String[]{"hill", "forest", "tree", "wood", "green", "stone"};
        public static final String[] DECMOUNTAIN_TERMS = new String[]{"mountain", "forest", "tree", "stone", "cloud", "wood"};
        public static final String[] GLACIER_TERMS = new String[]{"snow", "ice", "frost", "cold", "wind", "glacier"};
        public static final String[] GRASSLAND_TERMS = new String[]{"grass", "plain", "wind", "sun", "rain", "fire"};
        public static final String[] GRASSHILLS_TERMS = new String[]{"grass", "hill", "wind", "stone", "sun", "rain"};
        public static final String[] JUNGLE_TERMS = new String[]{"jungle", "tree", "rain", "color", "mist", "water"};
        public static final String[] JUNGLEHILLS_TERMS = new String[]{"jungle", "hill", "rain", "stone", "bird", "color"};
        public static final String[] JUNGLEMOUNTAIN_TERMS = new String[]{"jungle", "mountain", "cloud", "stone", "rain", "bird"};
        public static final String[] MOUNTAIN_TERMS = new String[]{"mountain", "rock", "stone", "cloud", "sun", "bird"};
        public static final String[] MOUNTAINTAIGA_TERMS = new String[]{"mountain", "stone", "pine", "cloud", "cold", "snow"};
        public static final String[] OCEAN_TERMS = new String[]{"water", "ocean", "fish"};
        public static final String[] SANDYDESERT_TERMS = new String[]{"sand", "sun", "dune", "hot", "fire", "dry"};
        public static final String[] SAVANNA_TERMS = new String[]{"grass", "savanna", "rock", "wind", "dry", "shade"};
        public static final String[] SHRUBLAND_TERMS = new String[]{"shrub", "grass", "dry", "tangle", "root", "sun"};
        public static final String[] SHRUBLANDHILLS_TERMS = new String[]{"shrub", "hill", "dry", "tangle", "wind", "sun"};
        public static final String[] SWAMP_TERMS = new String[]{"swamp", "root", "green", "water", "reed", "tree"};
        public static final String[] TAIGA_TERMS = new String[]{"pine", "tree", "cold", "snow", "frost", "taiga"};
        public static final String[] TAIGAHILLS_TERMS = new String[]{"pine", "tree", "hill", "taiga", "snow", "frost"};
        public static final String[] TUNDRA_TERMS = new String[]{"snow", "tundra", "grey", "frost", "cold", "wind"};
        public static final String[] TUNDRAHILLS_TERMS = new String[]{"tundra", "hill", "snow", "frost", "cold", "wind"};
    }

    private static class SubBiomeConstants {
        public static final String[] MOUNTAIN_SUB = new String[]{"alps", "mountains", "peaks", "rise", "range", "valley",
                "highlands", "summit", "slopes"};
        public static final String[] BADLANDS_SUB = new String[]{"badlands"};
        public static final String[] FOREST_SUB = new String[]{"woods", "woodland", "forest", "glade", "wilds",
                "grove", "thicket"};
        public static final String[] HILLS_SUB = new String[]{"hills", "bluffs", "valley", "slopes", "hillside"};
        public static final String[] GLACIER_SUB = new String[]{"iceberg", "glacier", "icefield"};
        public static final String[] GRASSLAND_SUB = new String[]{"plains", "grassland", "fields", "green", "flats",
                "meadow", "pasture", "expanse", "prairie"};
        public static final String[] JUNGLE_SUB = new String[]{"jungle", "rainforest", "wilds", "bush", "gardens", "paradise"};
        public static final String[] TAIGA_SUB = new String[]{"pines", "taiga"};
        public static final String[] OCEAN_SUB = new String[]{"waters", "expanse", "deep", "abyss", "trench", "domain",
                "tides", "waves"};
        public static final String[] DESERT_SUB = new String[]{"desert", "dunes", "flats", "expanse"};
        public static final String[] ARID_SUB = new String[]{"basin", "wasteland", "wastes", "deadland", "barrens", "gulch"};
        public static final String[] SAVANNA_SUB = new String[]{"savanna"};
        public static final String[] SHRUBLAND_SUB = new String[]{"shrubland"};
        public static final String[] SWAMP_SUB = new String[]{"marsh", "swamp", "wetlands", "bog", "rot"};
        public static final String[] TUNDRA_SUB = new String[]{"tundra", "frostland", "icefield", "expanse", "desert"};
    }

    private final String code;
    private final String nameText;
    private final float caveChance;
    private final Map<Resource, Pair<Integer,Integer>> resources;
    private final Map<Animal, Pair<Integer,Integer>> animals;
    private final float groupChance;
    private final String[] terms;
    private final String[] subBiomes;

    Biome(final String code, final String name, final MapProducer<Resource, Pair<Integer,Integer>> resources,
          final float caveChance, final AniMapProducer<Animal, Pair<Integer, Integer>> animals, final float groupChance,
          final String[] terms, final String[] sub){
        this.code = code;
        this.nameText = name;
        this.caveChance = caveChance;
        this.resources = Collections.unmodifiableMap(resources.getMap());
        this.animals = Collections.unmodifiableMap(animals.getAniMap());
        this.groupChance = groupChance;
        this.terms = terms;
        this.subBiomes = sub;
    }

    public String getCode() { return this.code; }
    public String getName() {return this.nameText;}
    public float getCaveChance() { return this.caveChance; }
    public Map<Resource, Pair<Integer, Integer>> getResourceStats() {return resources;}
    public Map<Animal, Pair<Integer, Integer>> getAnimals() {return animals;}
    public float getGroupChance() { return groupChance; }
    public String[] getTerms() { return terms; }
    public String[] getSubBiomes() { return subBiomes; }

    /**
     * Useful method for concatenating string arrays here
     * @param arrays
     * @return
     */
    private static String[] concatenate(String[] ...arrays) {
        return Stream.of(arrays).flatMap(Stream::of).toArray(String[]::new);
    }

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
