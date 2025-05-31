package porto.data.api;

public enum Role {
    ADMIN("Admin"),
    CAPTAIN("Capitano"),
    CREW_MEMBER("Astronauta");

    private final String name;

    /**
     * Returns the name of the role.
     *
     * @return the name of the role
     */
    Role(final String name) {
        this.name = name;
    }

}
