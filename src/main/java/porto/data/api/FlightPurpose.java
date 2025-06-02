package porto.data.api;

/**
 * Interface representing a flight purpose.
 * Provides methods to get the code and name of the flight purpose.
 */
public interface FlightPurpose {

    /**
     * Gets the code of the flight purpose.
     *
     * @return the code of the flight purpose
     */
    Integer codFlightPurpose();

    /**
     * Gets the name of the flight purpose.
     *
     * @return the name of the flight purpose
     */
    String flightPurposeName();

}
