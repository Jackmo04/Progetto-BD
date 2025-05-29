package porto.data.api;

public enum RequestType {
    
    ENTER,
    EXIT;

    /**
     * Returns the request type based on the given string.
     * @param type the string representation of the request type (Domain = [E, U])
     * @return the corresponding RequestType enum value
     * @throws IllegalArgumentException if the string does not match any request type
     */
    public static RequestType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "e" -> ENTER;
            case "u" -> EXIT;
            default -> throw new IllegalArgumentException("Unknown request type: " + type);
        };
    }

    /**
     * Returns the string representation of the request type.
     * @return the string representation
     */
    @Override
    public String toString() {
        return switch (this) {
            case ENTER -> "Entrata";
            case EXIT -> "Uscita";
        };
    }
}
