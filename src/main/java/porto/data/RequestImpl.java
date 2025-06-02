package porto.data;

import java.sql.Timestamp;

import porto.data.api.FlightPurpose;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestType;
import porto.data.api.Starship;

/**
 * Implementation of the Request interface.
 * Represents a request with various attributes such as code, type, date, description, total price, state,
 * managed date, starship, flight purpose, departure and destination planets, and the person managing the request.
 */
public record RequestImpl(
    int codRichiesta,
    RequestType type,
    Timestamp dateTime,
    String description,
    double totalPrice,
    Starship starship,
    FlightPurpose purpose,
    Planet departurePlanet,
    Planet destinationPlanet
) implements Request {
    
}
