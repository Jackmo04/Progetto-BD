package porto.data.api;

import java.sql.Connection;
import java.util.Optional;

import porto.data.PersonImpl;

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
    Optional<PersonImpl> capitan();

    /**
     * Returns the last request made by the starship.
     * @param connection the database connection to use
     * @return the last request made by the starship
     */
    Request lastRequest(Connection connection);
}
