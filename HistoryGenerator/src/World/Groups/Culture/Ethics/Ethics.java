package World.Groups.Culture.Ethics;

public class Ethics {
    private final EthicsType type;
    //TODO: Perhaps add specific beliefs and morals to add more diversity outside of the main ethos type.
    // Example from wikipedia: The Bedouins’ ethos comprised courage, hospitality, loyalty to family,
    // and pride of ancestry.
    public Ethics(final EthicsType type) {
        this.type = type;
    }

    public EthicsType getType() {
        return type;
    }
}
