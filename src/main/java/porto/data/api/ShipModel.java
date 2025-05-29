package porto.data.api;

public interface ShipModel {

    /**
     * Returns the code of the ship model.
     * @return the code of the ship model
     */
    String codModel();

    /**
     * Returns the name of the ship model.
     * @return the name of the ship model
     */
    String name();

    /**
     * Returns the size of the ship model.
     * @return the size of the ship model
     */
    int size();

    /**
     * Returns the tax associated with the size of the ship model.
     * @return the tax amount
     */
    double tax();
}
