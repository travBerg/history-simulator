package World.Groups.Culture.Ethics;

public enum EthicsType {
    //INDIVIDUALISM(),
    COLLECTIVISM("It's our reliance on each other, our community, that ensures our life and prosperity."),
    MILITARISM("It's a harsh world. The only way to survive is to take what we must from others. Strength above all."),
    //STOICISM(),
    SPIRITUALISM("We must place our trust in the divine to guide us through the trials of this world. " +
            "Life's purpose is to achieve spiritual awakening");

    private final String description;

    EthicsType(final String description) {
        this.description = description;
    }
}
