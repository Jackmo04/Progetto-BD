package porto.data.api;

public enum RequestState {

    PENDING,
    APPROVED,
    REJECTED;

    /**
     * Returns the request state based on the given string.
     * @param state the string representation of the request state (Domain = [NULL, A, R])
     * @return the corresponding RequestState enum value
     * @throws IllegalArgumentException if the string does not match any request state
     */
    public static RequestState fromString(String state) {
        if (state == null) {
            return PENDING;
        }
        return switch (state.toLowerCase()) {
            case "a" -> APPROVED;
            case "r" -> REJECTED;
            default -> throw new IllegalArgumentException("Unknown request state: " + state);
        };
    }

    /**
     * Returns the string representation of the request state.
     * @return the string representation
     */
    @Override
    public String toString() {
        return switch (this) {
            case PENDING -> "Pendente";
            case APPROVED -> "Approvata";
            case REJECTED -> "Rifiutata";
        };
    }
}
