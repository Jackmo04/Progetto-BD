package porto.data;

import java.sql.Timestamp;
import java.util.Optional;

import porto.data.api.FlightPurpose;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestState;
import porto.data.api.RequestType;
import porto.data.api.Starship;

public record RequestImpl(
    int codRichiesta,
    RequestType type,
    Timestamp dateTime,
    String description,
    double totalPrice,
    RequestState state,
    Optional<Timestamp> dateTimeManaged,
    Optional<String> managedBy,
    Starship starship,
    FlightPurpose purpose,
    Planet departurePlanet,
    Planet destinationPlanet
) implements Request {

    @Override
    public Planet destinationPlane() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'destinationPlane'");
    }
    
}
