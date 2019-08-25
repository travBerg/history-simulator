package TerrainGenerator;

import java.util.ArrayList;

public class RainMap implements ITerrainMap {
    //noise constant
    double r;
    //size constant
    int sConst;
    //row and col size (derived)
    int s;
    //weighting for value (maybe broken)
    int weight;
    int addWeight;
    ArrayList<ArrayList<Integer>> board = new ArrayList<>();

    @Override
    public ArrayList<ArrayList<Integer>> init() {
        return null;
    }

    public void step(int tRow, int bRow, int lCol, int rCol, double r) {

    }

    public Integer average(int[] nums) {
        int sum = 0;
        for(int i: nums) {
            sum += i;
        }
        if(addWeight != 0) {
            sum += weight;
        }
        return sum / nums.length;
    }

    @Override
    public String render() {
        String out = "";
        for(int x = 0; x < s; x++) {
            for(int y = 0; y < s; y++) {
                int val = board.get(x).get(y);
                if (val < 0) {
                    out += val + " ";
                } else {
                    out += " " + val + " ";
                }

            }
            out += "\n";
        }
        return out;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getBoard() {
        return null;
    }
}
