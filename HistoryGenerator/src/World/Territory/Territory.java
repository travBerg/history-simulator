package World.Territory;

import TerrainGenerator.ITerrainMap;
import World.PointOfInterest.POI;
import World.PointOfInterest.POIManager;
import World.Rivers.River;
import World.Territory.Biome.Biome;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Territory implements ITerritory {
    final int seed;
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

    public Territory(final String location, final int seed, final String hrt, final int size, final Biome biome,
                     final HashMap<Integer, River> rivers, final HashMap<String, Integer> locBased) {
        this.seed = seed;
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
        this.name = "Unnamed " + biome.getName() + " Region";
        //Populate with POI
        this.pOI = new HashSet<>();
        if(locBased.containsKey(location)) {
            final int i = locBased.get(location);
            pOI.add(POIManager.createRiverPOI(rivers.get(i), location, seed, locBased));
        }
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

        territoryJSON.put("row", this.row);
        territoryJSON.put("col", this.col);
        territoryJSON.put("height" , this.height);
        territoryJSON.put("rain" , this.rain);
        territoryJSON.put("temp" , this.temp);
        territoryJSON.put("size" , this.size);
        territoryJSON.put("discovered" , this.discovered);
        territoryJSON.put("neighbors" , neighborsJSON);
        JSONArray pOIArray = new JSONArray();
        this.pOI.stream().forEach(p -> pOIArray.add(POIManager.toJSONPOI(p)));
        territoryJSON.put("poi", pOIArray);
        return territoryJSON;
    }
}
