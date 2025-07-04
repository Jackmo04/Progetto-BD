package porto.data.api;

import java.sql.Timestamp;

/**
 * Interface representing a request in the Porto system.
 * Provides methods to access various attributes of a request such as code, type, date, description,
 * total price, state, management details, starship, flight purpose, and departure/destination planets.
 */
public interface Request {

    /**
     * Returns the unique identifier of the request.
     * @return the request ID
     */
    int codRichiesta();

    /**
     * Returns the type of the request.
     * @return the request type
     */
    RequestType type();

    /**
     * Returns the date and time when the request was created.
     * @return the date and time of the request
     */
    Timestamp dateTime();

    /**
     * Returns the description of the request.
     * @return the request description
     */
    String description();

    /**
     * Returns the total price associated with the request.
     * @return the total price
     */
    double totalPrice();

    /**
     * Returns the starship associated with the request.
     * @return the starship
     */
    Starship starship();

    /**
     * Returns the purpose of the flight associated with the request.
     * @return the flight purpose
     */
    FlightPurpose purpose();

    /**
     * Returns the planet from which the starship departed.
     * @return the departure planet
     */
    Planet departurePlanet();

    /**
     * Returns the planet to which the starship is destined.
     * @return the destination planet
     */
    Planet destinationPlanet();

}
