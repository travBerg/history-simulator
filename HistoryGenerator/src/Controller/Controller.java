package Controller;

import View.IView;
import World.IWorld;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Controller implements IController{
    private IWorld world;
    private IView view;
    public Controller (IWorld world, IView view) {
        this.view = view;
        this.world = world;
    }

    public void go() {
        //TODO: This part will generate history
        //Now we view it
        explore();
    }

    public void explore() {
        boolean exit = false;
        while (!exit) {
            exit = listRegions();
        }
        System.out.println("\nGoodbye!");
    }

    //List all regions in world
    public boolean listRegions() {
        ArrayList<Pair<Integer, String>> regionList = world.getRegionList();
        view.listRegions(regionList);
        Scanner in = new Scanner(System.in);
        int idx = in.nextInt();
        if (idx == 0) {
            return true;
        } else {
            //get official index of region and explore it
            return exploreRegion(regionList.get(idx - 1).getKey());
        }
    }

    //Explore a single region based on index
    public boolean exploreRegion(int idx) {
        ArrayList<Pair<String, String>> terr = world.getTerritories(idx);
        view.exploreRegion(terr);
        Scanner in = new Scanner(System.in);
        int ii = in.nextInt();
        if (ii == 0) {
            return listRegions();
        } else {
            //TODO: Placeholder for explore territory
            return exploreTerritory(terr.get(ii - 1).getKey());
        }
    }

    //Explore a single Territory
    public boolean exploreTerritory(String loc) {
        return true;
    }
}
