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

    public static Role fromString(String role) {
        return switch (role.toLowerCase()) {
            case "astronauta" -> CREW_MEMBER;
            case "capitano" -> CAPTAIN;
            case "admin" -> ADMIN;
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

    @Override
    public String toString() {
        return name;
    }

}
