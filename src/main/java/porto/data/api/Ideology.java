package porto.data.api;

/**
 * Enum representing the different ideologies in the Porto data model.
 * Each ideology has a name associated with it.
 */
public enum Ideology {
    RIBELLE ("Ribelle"),
    IMPERIALE ("Imperiale"),
    NEUTRALE ("Neutrale");

    private final String name;

    /**
     * Returns the name of the ideology.
     *
     * @return the name of the ideology
     */
    Ideology(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
