package porto.data;

import java.sql.Timestamp;
import java.util.Optional;

import porto.data.api.FlightPurpose;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestState;
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
    RequestState state,
    Optional<Timestamp> dateTimeManaged,   
    Starship starship,
    FlightPurpose purpose,
    Planet departurePlanet,
    Planet destinationPlanet,
    Optional<Person> managedBy
) implements Request {
    
}
