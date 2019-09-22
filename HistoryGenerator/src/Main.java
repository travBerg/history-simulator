import TerrainGenerator.*;

public class Main {

    public static void main(String[] args) {
        //ITerrainMap heightMap = new HeightMap(1, 5, 0, 0);
        //heightMap.init();
        //System.out.println(heightMap.render());
        //ITerrainMap rainMap = new HeightMap(1, 5, 0, 0);
        //rainMap.init();
        //System.out.println(heightMap.render());
        //ITerrainMap tempMap = new TempMap();
        //tempMap.init();
        //System.out.println(tempMap.render());
        IGenerator generator = new TerrainGen(7, 690);
        System.out.println(((TerrainGen) generator).render());
    }

}
