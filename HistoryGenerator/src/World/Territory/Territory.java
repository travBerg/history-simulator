package World.Territory;

import TerrainGenerator.ITerrainMap;

public abstract class Territory implements ITerritory {
    int seed;
    public Territory(int seed) {
        this.seed = seed;

    }
}
