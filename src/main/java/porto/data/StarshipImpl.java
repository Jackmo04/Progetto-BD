package porto.data;

import java.util.Optional;

import porto.data.api.ParkingSpace;
import porto.data.api.Person;
import porto.data.api.ShipModel;
import porto.data.api.Starship;

/**
 * Implementation of the Starship interface.
 */
public record StarshipImpl(
    String plateNumber,
    String name,
    ShipModel model,
    Person capitan
) implements Starship {

    @Override
    @Deprecated
    public Optional<ParkingSpace> parkingSpace() {
        throw new UnsupportedOperationException(
            "Use ParkingSpaceDAO.ofStarship(String starshipPlateNumber) instead of Starship.parkingSpace()"
        );
    }

}
