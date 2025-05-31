package porto.data.api;

/**
 * Enum representing the different ideologies in the Porto data model.
 * Each ideology has a name associated with it.
 */
public enum Ideology {
    REBEL ("Ribelle"),
    IMPERIAL ("Imperiale"),
    NEUTRAL ("Neutrale");

    private final String name;

    /**
     * Returns the name of the ideology.
     *
     * @return the name of the ideology
     */
    Ideology(final String name) {
        this.name = name;
    }

    public static Ideology fromString(String ideology) {
        return switch (ideology.toLowerCase()) {
            case "ribelle" -> REBEL;
            case "imperiale" -> IMPERIAL;
            case "neutrale" -> NEUTRAL;
            default -> throw new IllegalArgumentException("Unknown ideology: " + ideology);
        };
    }

    @Override
    public String toString() {
        return name;
    }
    
}
