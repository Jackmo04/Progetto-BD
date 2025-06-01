package porto.data.api;

import java.util.Optional;

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
     * Returns the parking space of the starship, if any.
     * @return an Optional containing the parking space, or empty if not on the station
     */
    Optional<ParkingSpace> parkingSpace();

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

}
