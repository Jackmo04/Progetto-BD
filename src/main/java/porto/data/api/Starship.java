package porto.data.api;

import java.util.Optional;

import porto.data.api.dao.ParkingSpaceDAO;

public interface Starship {

    /**
     * Returns the plate number of the starship.
     * @return the plate number
     */
    String plateNumber();

    /**
     * Returns the name of the starship.
     * @return the name
     */
    String name();

    /**
     * Returns the model of the starship.
     * @return the ship model
     */
    ShipModel model();

    /**
     * Returns the captain of the starship.
     * @return the captain
     */
    Person capitan();

    /**
     * Returns the parking space where the starship is docked.
     * @return an Optional containing the parking space if present, otherwise empty
     * @deprecated Use {@link ParkingSpaceDAO#ofStarship(String starshipPlateNumber)} instead.
     */
    @Deprecated
    Optional<ParkingSpace> parkingSpace(); // TODO Remove this method when all references to Starship.parkingSpace() are replaced
}
