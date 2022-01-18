package World.Territory;

import TerrainGenerator.ITerrainMap;
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

public class Territory implements ITerritory {
    //row|column
    final int row;
    final int col;
    final ArrayList<String> neighbors;
    final int height;
    final int rain;
    final int temp;
    final int size;
    final Biome biome;
    final String name;
    final boolean discovered;
    final Set<POI> pOI;
    //Resource to amount
    final HashMap<Resource, Integer> resources;

    //For creating new Territories
    public Territory(final String location, final Random rand, final String hrt, final int size, final Biome biome,
                     final HashMap<Integer, River> rivers, final HashMap<String, Integer> locBased) {
        final Pair<Integer, Integer> loc = TerritoryManager.parseLocation(location);
        this.row = loc.getKey();
        this.col = loc.getValue();
        this.height = Integer.parseInt(hrt.substring(0,1));
        this.rain = Integer.parseInt(hrt.substring(1,2));
        this.temp = Integer.parseInt(hrt.substring(2,3));
        this.neighbors = TerritoryManager.addNeighbors(location, size);
        this.size = size;
        this.discovered = false;
        this.biome = biome;
        this.name = "Unnamed " + biome.getName();
        //Populate with POI
        this.pOI = new HashSet<>();
        //First add the wilderness POI to the territory
        pOI.add(new Wilderness(name, this.biome));
        //For now no POI for Oceans
        if(this.biome != Biome.OCEAN) {
            //locBased is for rivers, check if this area has a river
            if(locBased.containsKey(location)) {
                final int i = locBased.get(location);
                POIManager.createRiverPOI(rivers.get(i), location, rand, locBased).map(r -> pOI.add(r));
            }
            //Add watersource POI
            pOI.addAll(POIManager.createWatersourcePOI(pOI, biome, rand, location));
            //Add land POI
            //pOI.addAll(POIManager.createLandPOI(biome, seed, location, this.name));
        } else {
            //TODO: Add Ocean POI
        }
        this.resources = ResourceManager.addResources(pOI, biome, rand);
    }

    @Override
    public boolean isDiscovered() { return discovered; }

    @Override
    public final int getRow() { return row; }

    @Override
    public final int getCol() { return col; }

    @Override
    public final String getName() { return name; }

    @Override
    public final Biome getBiome() { return biome; }

    @Override
    public ArrayList<String> getNeighbors() { return neighbors; }

    @Override @SuppressWarnings("unchecked")
    public JSONObject asJSON() {
        JSONObject territoryJSON = new JSONObject();
        JSONArray neighborsJSON = new JSONArray();

        //Break up neighbors into rows and columns
        for (String Neighborlocation: this.neighbors){
            String[] splitNeighborLocation = Neighborlocation.split("\\|");
            int neighborRow = Integer.parseInt(splitNeighborLocation[0]);
            int neighborCol = Integer.parseInt(splitNeighborLocation[1]);
            JSONObject locationJSON = new JSONObject();
            locationJSON.put("col", neighborRow);
            locationJSON.put("row", neighborCol);
            neighborsJSON.add(locationJSON);
        }
        territoryJSON.put("name", this.getName());
        territoryJSON.put("row", this.row);
        territoryJSON.put("col", this.col);
        territoryJSON.put("height" , this.height);
        territoryJSON.put("rain" , this.rain);
        territoryJSON.put("temp" , this.temp);
        territoryJSON.put("size" , this.size);
        territoryJSON.put("discovered" , this.discovered);
        territoryJSON.put("neighbors" , neighborsJSON);
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
        return territoryJSON;
    }

    @Override
    public Set<POI> getPOI() {
        return pOI;
    }
}
