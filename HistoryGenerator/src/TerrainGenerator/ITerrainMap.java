package TerrainGenerator;

import java.util.ArrayList;

public interface ITerrainMap {
    /*
     creates and returns the map
     */
    ArrayList<ArrayList<Integer>> init();

    //steps through the diamond/square
    //void step(int tRow, int bRow, int lCol, int rCol, double r);

    //helper function to average values
    //Integer average(int[] nums);

    //renders the board as a string for debugging
    String render();

    ArrayList<ArrayList<Integer>> getBoard();
}
