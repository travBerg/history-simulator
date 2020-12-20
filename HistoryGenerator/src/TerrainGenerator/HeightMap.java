package TerrainGenerator;

import java.util.ArrayList;
import java.util.Random;

public class HeightMap implements ITerrainMap {
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
    //Random generator
    Random ran;

    public HeightMap() {
        ran = new Random(999);
        this.r = 1;
        this.sConst = 5;
        this.s = (int) Math.pow(2, sConst) + 1;
        this.weight = 1;
        this.addWeight = 0;
        for(int x = 0; x < s; x++) {
            ArrayList<Integer> i = new ArrayList<>();
            board.add(i);
            for(int y = 0; y < s; y++) {
                board.get(x).add(0);
            }
        }
    }

    public HeightMap(double r, int size, int weight, int addWeight, int seed) {
        ran = new Random(seed);
        this.r = r;
        this.s = size;
        this.weight = weight;
        this.addWeight = addWeight;
        for(int x = 0; x < s; x++) {
            ArrayList<Integer> i = new ArrayList<>();
            board.add(i);

            for(int y = 0; y < s; y++) {
                board.get(x).add(0);
            }

        }
    }

    @Override
    public ArrayList<ArrayList<Integer>> init() {
        //init corners with random ints from 0 - 4
        board.get(0).set(0, ran.nextInt(5));
        int tl = board.get(0).get(0);
        board.get(0).set(s - 1, ran.nextInt(5));
        int tr = board.get(0).get(s - 1);
        board.get(s - 1).set(0, ran.nextInt(5));
        int bl = board.get(s - 1).get(0);
        board.get(s - 1).set(s - 1, ran.nextInt(5));
        int br = board.get(s - 1).get(s - 1);

        //init center value (diamond step)
        int arr[] = new int[]{tl, tr, bl, br};
        int center = average(arr);
        board.get(s / 2).set(s / 2, center);

        //first square step
        int arr1[] = new int[]{tl, tr, center};
        int val = calculateValue(arr1, r);
        board.get(0).set(s / 2, val);
        int arr2[] = new int[]{tl, bl, center};
        val = calculateValue(arr2, r);
        board.get(s / 2).set(0, val);
        int arr3[] = new int[]{tr, br, center};
        val = calculateValue(arr3, r);
        board.get(s / 2).set(s - 1, val);
        int arr4[] = new int[]{bl, br, center};
        val = calculateValue(arr4, r);
        board.get(s - 1).set(s / 2, val);

        step(0, s / 2, 0, s / 2, r);
        step(0, s / 2, s / 2, s - 1, r);
        step(s / 2, s - 1, 0, s / 2, r);
        step(s / 2, s - 1, s / 2, s - 1, r);
        return board;
    }

    public void step(int tRow, int bRow, int lCol, int rCol, double r) {
        //reduce r value
        r *= Math.pow(2, 0 - r);

        //get corners
        int tl = board.get(tRow).get(lCol);
        int tr = board.get(tRow).get(rCol);
        int bl = board.get(bRow).get(lCol);
        int br = board.get(bRow).get(rCol);

        //center (diamond step)
        int arr[] = new int[]{tl, tr, bl, br};
        int center = calculateValue(arr, r);

        int mRow = bRow - ((bRow - tRow) / 2);
        int mCol = rCol - ((rCol - lCol) / 2);
        board.get(mRow).set(mCol, center);

        //square step
        int arr1[] = new int[]{tl, tr, center};
        int val = calculateValue(arr1, r);
        board.get(tRow).set(mCol, val);
        int arr2[] = new int[]{tl, bl, center};
        val = calculateValue(arr2, r);
        board.get(mRow).set(lCol, val);
        int arr3[] = new int[]{tr, br, center};
        val = calculateValue(arr3, r);
        board.get(mRow).set(rCol, val);
        int arr4[] = new int[]{bl, br, center};
        val = calculateValue(arr4, r);
        board.get(bRow).set(mCol, val);

        if(lCol >= (mCol - 1) || tRow >= (mRow - 1)) {
            return;
        } else {
            step(tRow, mRow, lCol, mCol, r);
            step(tRow, mRow, mCol, rCol, r);
            step(mRow, bRow, lCol, mCol, r);
            step(mRow, bRow, mCol, rCol, r);
        }
    }

    public Integer calculateValue(int[] arr, double r) {
        //average plus a random
        int val = average(arr) + (int)(ran.nextGaussian() * ((r - (0 - r)) + 1) + (0 - r));
        if (val < 0) {
            val = 0;
        } else if (val > 4){
            val = 4;
        }
        return val;
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
        return board;
    }
}
