package porto.data.api.dao;

import porto.data.api.ShipModel;

public interface ShipModelDAO {

    /**
     * Retrieves a ship model by its code.
     * @param codModel the code of the ship model
     * @return the ship model, or null if not found
     */
    ShipModel getFromCode(String codModel);
}
