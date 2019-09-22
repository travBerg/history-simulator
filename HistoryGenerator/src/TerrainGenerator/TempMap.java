package TerrainGenerator;

import java.util.ArrayList;
import java.util.Random;

public class TempMap implements ITerrainMap {


    //size constant
    int sConst;
    //row and col size (derived)
    int s;
    //setting for including poles. 0 = North, 1 = South, 2 = Both
    int poles;
    //noise variance
    double nVar;
    //noise mean
    double nMean;
    ArrayList<ArrayList<Integer>> board = new ArrayList<>();
    Random r;

    public TempMap() {
        this.r = new Random(999);
        this.sConst = 5;
        this.s = (int) Math.pow(2, sConst) + 1;
        this.poles = 2;
        this.nVar = 0.5;
        this.nMean = 0;
        for(int x = 0; x < s; x++) {
            ArrayList<Integer> i = new ArrayList<>();
            board.add(i);
            for(int y = 0; y < s; y++) {
                board.get(x).add(0);
            }
        }
    }

    public TempMap(int sConst, int poles, double nVar, double nMean, int seed) {
        this.r = new Random(seed);
        this.sConst = sConst;
        this.s = (int) Math.pow(2, sConst) + 1;
        this.poles = poles;
        this.nVar = nVar;
        this.nMean = nMean;
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
        //North pole (northern hemisphere)
        for(int x = 0; x < s; x++) {
            double mod = 0;
            switch (this.poles) {
                case 0:
                    mod = ((double) s - (Math.abs(x - (double) s) - 1)) / (double) s;
                    break;
                case 1:
                    mod = (Math.abs(x - (double) s) - 1) / (double) s;
                    break;
                default:
                    double mid = s / 2;
                    mod = (mid - Math.abs(x - mid)) / mid;
                    break;
            }
            //System.out.println(mod);
            int rVal = (int)Math.round(mod * 4);
            for(int y = 0; y < s; y++) {
                int noise =(int)Math.round(r.nextGaussian() * nVar + nMean);
                int val = noise + rVal;
                if (val > 4) {
                    val = 4;
                } else if (val < 0){
                    val = 0;
                }
                board.get(x).set(y, val);
            }
        }
        /**
        double mid = s / 2;
        //System.out.println(mid);
        for(int x = 0; x < s; x++) {
            double mod = (mid - Math.abs(x - mid)) / mid;
            System.out.println(mod);
            int rVal = (int)Math.round(mod * 4);
            for(int y = 0; y < s; y++) {
                int noise =(int)Math.round(r.nextGaussian() * nVar + nMean);
                int val = noise + rVal;
                if (val > 4) {
                    val = 4;
                } else if (val < 0){
                    val = 0;
                }
                board.get(x).set(y, val);
            }
        }**/
        return board;
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
