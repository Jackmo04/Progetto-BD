package porto.data;

import java.sql.Connection;
import java.util.Optional;

import porto.data.api.ParkingSpace;
import porto.data.api.Request;
import porto.data.api.ShipModel;
import porto.data.api.Starship;

/**
 * Implementation of the Starship interface.
 */
public record StarshipImpl(
    String plateNumber,
    String name,
    Optional<ParkingSpace> parkingSpace,
    ShipModel model,
    PersonImpl capitan
) implements Starship {

    public Request lastRequest(Connection connection) {
        // TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
