package TerrainGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TerrainGen implements IGenerator{
    ITerrainMap heightMap;
    ITerrainMap rainMap;
    ITerrainMap tempMap;
    HashMap<String, String> rawTerrain;
    HashMap<String, String> terrain;
    HashMap<String, String> csvMap;

    /**
     * This is the constructor with most settings for customizing terrain
     * @param size is the size constant used to derive the actual terrain size
     * @param r is the noise constant for the heightmap (and rainmap currently)
     * @param poles is for temp map. 0 = north pole, 1 = south pole, 2 = both poles
     * @param nVar noise variance for the temp map
     * @param nMean noise mean for the temp map (can bring average temp higher or lower)
     */
    public TerrainGen(int size, double r, int poles, double nVar, double nMean){
        this.heightMap = new HeightMap(1, size, 0, 0);
        this.heightMap.init();
        //THIS IS TEMPORARY UNTIL RAINMAP GETS UPDATED
        this.rainMap = new HeightMap(1, size, 0, 0);
        this.rainMap.init();
        //temp map
        this.tempMap = new TempMap(size, 2, 0.5, 0);
        this.tempMap.init();

        this.rawTerrain = new HashMap<>();
        this.csvMap = this.loadCSVs();
        this.terrain = this.create(this.heightMap, this.rainMap, this.tempMap);
    }

    public TerrainGen(int size){
        this.heightMap = new HeightMap(1, size, 0, 0);
        this.heightMap.init();
        //THIS IS TEMPORARY UNTIL RAINMAP GETS UPDATED
        this.rainMap = new HeightMap(1, size, 0, 0);
        this.rainMap.init();
        //temp map
        this.tempMap = new TempMap(size, 2, 0.5, 0);
        this.tempMap.init();

        this.rawTerrain = new HashMap<>();
        this.csvMap = this.loadCSVs();
        this.terrain = this.create(this.heightMap, this.rainMap, this.tempMap);
    }

    //maps must be preinitialized if using this constructor!!!
    public TerrainGen(ITerrainMap hmap, ITerrainMap rmap, ITerrainMap tmap) {
        this.heightMap = hmap;
        this.rainMap = rmap;
        this.tempMap = tmap;
        this.rawTerrain = new HashMap<>();
        this.csvMap = this.loadCSVs();
        this.terrain = this.create(this.heightMap, this.rainMap, this.tempMap);
    }

    public HashMap<String, String> create(ITerrainMap hmap, ITerrainMap rmap, ITerrainMap tmap){
        HashMap<String, String> map = new HashMap<>();
        //compile the terrain data into one map
        for (int hR = 0; hR < hmap.getBoard().size(); hR++) {
            for (int hC = 0; hC < hmap.getBoard().size(); hC++) {
                String key = Integer.toString(hR) + "|" + Integer.toString(hC);
                //add heightmap numbers
                String val = Integer.toString(hmap.getBoard().get(hR).get(hC));
                //add rainmap numbers
                val += Integer.toString(rmap.getBoard().get(hR).get(hC));
                //add tempmap numbers
                val += Integer.toString(tmap.getBoard().get(hR).get(hC));
                this.rawTerrain.put(key,val);
                String type = csvMap.get(val);
                map.put(key, type);
            }
        }

        System.out.println(rawTerrain);
        System.out.println(map);

        return map;
    }

    public String keyify(int x) {
        if(x < 10) {
            return "0" + Integer.toString(x);
        } else {
            return Integer.toString(x);
        }
    }

    public HashMap<String,String> loadCSVs() {
        HashMap<String,String> map = new HashMap<>();
        //list of all csvs
        String[] files = {"src\\TerrainGenerator\\Resources\\Height0.csv", "src\\TerrainGenerator\\Resources\\Height1.csv",
                "src\\TerrainGenerator\\Resources\\Height2.csv", "src\\TerrainGenerator\\Resources\\Height3.csv",
                "src\\TerrainGenerator\\Resources\\Height4.csv"};
        //general working dir
        String workingDirectory = System.getProperty("user.dir");
        //height accumulator since its a while loop
        int heightNum = 0;
        //iterate through files, parsing them into the map. (height, rain, temp)
        for (String f:files) {
            String line = "";
            System.out.println(workingDirectory + File.separator + f);
            try (BufferedReader br = new BufferedReader(new FileReader(workingDirectory + File.separator + f))) {
                int rainNum = 0;
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    for (int temp = 0; temp < row.length; temp++) {
                        String key = Integer.toString(heightNum) + Integer.toString(rainNum) + Integer.toString(temp);
                        map.put(key, row[temp]);
                    }
                    rainNum++;
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
            heightNum++;
            //System.out.println(heightNum);
        }
        //System.out.println(map);
        return map;
    }

    public String render() {
        String out = "";
        int s = heightMap.getBoard().size();
        for(int x = 0; x < s; x++) {
            for(int y = 0; y < s; y++) {
                String key = Integer.toString(x) + "|" + Integer.toString(y);
                out += terrain.get(key) + " ";

            }
            out += "\n";
        }
        return out;
    }
}
