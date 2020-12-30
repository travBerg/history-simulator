package View.DebugView;

import Controller.IController;
import View.IView;
import World.IWorld;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DebugView implements IView {

    public void render(IWorld world) {

    }

    public void listRegions(ArrayList<Pair<Integer, String>> regionList) {
        System.out.println("Enter the number of a region you'd like to explore:");
        int count = 1;
        for (Pair<Integer, String> i : regionList) {
            System.out.println(count + ": " + i.getValue());
            count++;
        }
        System.out.println("0: exit");

    }

    public void exploreRegion(ArrayList<Pair<String, String>> terr) {
        int count = 1;
        for (Pair<String, String> i : terr) {
            final String name = i.getValue();
            System.out.println(count + ": " + name + " (loc: " + i.getKey() +")");
            count++;
        }
        System.out.println("0: <-");

    }

}
