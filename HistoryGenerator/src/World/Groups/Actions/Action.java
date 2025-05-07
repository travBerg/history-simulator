package World.Groups.Actions;

public enum Action {
    SETTLE(0),
    WANDER(1);

    private int id;

    Action(final int id) {
        this.id = id;
    }

    public int getId(){ return id; }
}
