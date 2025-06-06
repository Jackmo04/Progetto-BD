package porto.data.api;

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

}
