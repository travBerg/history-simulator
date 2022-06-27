package World.Territory;

import TerrainGenerator.ITerrainMap;
import World.Animals.Animal;
import World.Groups.Group;
import World.PointOfInterest.POI;
import World.PointOfInterest.POIManager;
import World.PointOfInterest.Wilderness;
import World.Resources.Resource;
import World.Resources.ResourceManager;
import World.Rivers.River;
import World.Territory.Biome.Biome;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.regex.Pattern;

public class Territory {//implements ITerritory {
    //row|column
    private final String location;
    private final int row;
    private final int col;
    private final ArrayList<String> neighbors;
    private final int height;
    private final int rain;
    private final int temp;
    private final int size;
    private final Biome biome;
    private final String name;
    private final String nameMeaning;
    private final Optional<String> discovered;
    private final Set<POI> pOI;
    //Resource to amount
    private final HashMap<Resource, Integer> resources;
    private final Set<Animal> animals;
    private final Set<Group> groups;

    //For creating new Territories
    public Territory(final String location, final Random rand, final String hrt, final int size, final Biome biome,
                     final HashMap<Integer, River> rivers, final HashMap<String, Integer> locBased) {
        this.location = location;
        final Pair<Integer, Integer> loc = TerritoryManager.parseLocation(location);
        this.row = loc.getKey();
        this.col = loc.getValue();
        this.height = Integer.parseInt(hrt.substring(0,1));
        this.rain = Integer.parseInt(hrt.substring(1,2));
        this.temp = Integer.parseInt(hrt.substring(2,3));
        this.neighbors = TerritoryManager.addNeighbors(location, size);
        this.size = size;
        this.discovered = Optional.empty();
        this.biome = biome;
        this.name = "Unnamed " + biome.getName();
        this.nameMeaning = this.name;
        //Populate with POI
        this.pOI = new HashSet<>();
        //First add the wilderness POI to the territory
        pOI.add(new Wilderness(name, this.biome));
        //For now no POI for Oceans
        if(this.biome != Biome.OCEAN) {
            //locBased is for rivers, check if this area has a river
            if(locBased.containsKey(location)) {
                final int i = locBased.get(location);
                POIManager.createRiverPOI(rivers.get(i), location, rand, locBased).map(pOI::add);
            }
            //Add watersource POI
            pOI.addAll(POIManager.createWatersourcePOI(pOI, biome, rand, location));
            //Add land POI
            //pOI.addAll(POIManager.createLandPOI(biome, seed, location, this.name));
        } else {
            //TODO: Add Ocean POI
        }
        final Pair<HashMap<Resource, Integer>, Set<Animal>> resAndAni = ResourceManager.addResources(pOI, biome, rand);
        this.resources = resAndAni.getKey();
        this.animals = resAndAni.getValue();
        this.groups = new HashSet<>();
    }

    /**
     * Constructor for when the first group is added to territory before time starts
     * @param old territory before it had group
     * @param group new group
     */
    public Territory(final Territory old, final Group group, final Random rand) {
        this.location = old.getLocation();
        this.row = old.getRow();
        this.col = old.getCol();
        this.neighbors = old.getNeighbors();
        this.height = old.height;
        this.rain = old.rain;
        this.temp = old.temp;
        this.size = old.size;
        this.biome = old.getBiome();
        //Have the group name it
        final Pair<String, String> names = TerritoryManager.createTerName(old, group, rand);
        this.name = names.getKey();
        this.nameMeaning = names.getValue();
        this.resources = old.getResources();
        this.animals = old.animals;
        this.groups = new HashSet<>();
        this.groups.add(group);
        this.discovered = Optional.of(group.getId());
        //Name/discover poi
        this.pOI = POIManager.discoverAllPOI(old.getPOI(), group, rand, this);
    }

    //@Override
    public final String isDiscovered() { return discovered.orElse(""); }

    //@Override
    public final int getRow() { return row; }

    //@Override
    public final int getCol() { return col; }

    //@Override
    public final String getName() { return name; }

    public String getNameMeaning() { return nameMeaning; }

    //@Override
    public final Biome getBiome() { return biome; }

    //@Override
    public ArrayList<String> getNeighbors() { return neighbors; }

    //@Override @SuppressWarnings("unchecked")
    public JSONObject asJSON() {
        //TODO: Move to manager class
        JSONObject territoryJSON = new JSONObject();
        //JSONArray neighborsJSON = new JSONArray();

        //Break up neighbors into rows and columns
        /* Dont think I actually need all this
        for (String Neighborlocation: this.neighbors){
            String[] splitNeighborLocation = Neighborlocation.split("\\|");
            int neighborRow = Integer.parseInt(splitNeighborLocation[0]);
            int neighborCol = Integer.parseInt(splitNeighborLocation[1]);
            JSONObject locationJSON = new JSONObject();
            locationJSON.put("col", neighborCol);
            locationJSON.put("row", neighborRow);
            neighborsJSON.add(locationJSON);
        }*/
        territoryJSON.put("name", this.getName());
        territoryJSON.put("nameMeaning", this.getNameMeaning());
        territoryJSON.put("location", this.location);
        territoryJSON.put("row", this.row);
        territoryJSON.put("col", this.col);
        territoryJSON.put("height" , this.height);
        territoryJSON.put("rain" , this.rain);
        territoryJSON.put("temp" , this.temp);
        territoryJSON.put("size" , this.size);
        territoryJSON.put("discovered" , this.discovered.orElse(""));
        //territoryJSON.put("neighbors" , neighborsJSON);
        //POI
        final JSONArray pOIArray = new JSONArray();
        this.pOI.stream().forEach(p -> pOIArray.add(POIManager.toJSONPOI(p)));
        territoryJSON.put("poi", pOIArray);
        //Resources
        final JSONArray resourceArray = new JSONArray();
        this.resources.entrySet().stream().forEach(e->{
            final JSONObject res = new JSONObject();
            res.put("resource", ResourceManager.toJSONRes(e.getKey()));
            res.put("quantity", e.getValue());
            resourceArray.add(res);
        });
        territoryJSON.put("resourceCaps", resourceArray);
        final JSONArray aniArray = new JSONArray();
        this.animals.forEach(a->{
            aniArray.add(a.getName());
        });
        territoryJSON.put("animals", aniArray);
        final JSONArray grpArray = new JSONArray();
        this.groups.forEach(g->{
            grpArray.add(g.getId());
        });
        territoryJSON.put("groupsPresent", grpArray);
        return territoryJSON;
    }

    //@Override
    public Set<POI> getPOI() {
        return pOI;
    }

    public HashMap<Resource, Integer> getResources() {
        return resources;
    }

    //@Override
    public String getLocation() {
        return location;
    }
}
