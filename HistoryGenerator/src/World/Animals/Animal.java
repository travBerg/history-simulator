package World.Animals;

import World.Resources.Resource;
import javafx.util.Pair;

import java.lang.reflect.Parameter;

public enum Animal {
    //Animal
    GOAT("goat", new Pair[]{new Pair(Resource.GOATMEAT, false), new Pair(Resource.GOATMILK, true),
        new Pair(Resource.LEATHER, false)}),
    CHICKEN("chicken", new Pair[]{new Pair(Resource.CHICKENMEAT, false), new Pair(Resource.EGGS, true)}),
    COW("cow", new Pair[]{new Pair(Resource.BEEF, false), new Pair(Resource.COWMILK, true),
        new Pair(Resource.LEATHER, false)});

    final String name;
    final Pair<Resource, Boolean>[] resources;

    /**
     *
     * @param name name of the resource
     * @param resources array of pairs of resources that come from this animal and a boolean for whether the animal must
     *                  be domesticated to harvest it
     */
    Animal(final String name, final Pair<Resource, Boolean>[] resources){
        this.name = name;
        this.resources = resources;
    }

    public final String getName() {return  name;}
    public final Pair<Resource, Boolean>[] getResources() {return resources;}
}
