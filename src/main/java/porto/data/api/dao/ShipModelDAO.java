package porto.data.api.dao;

import java.util.Optional;
import java.util.Set;

import porto.data.api.ShipModel;

public interface ShipModelDAO {

    /**
     * Retrieves a ship model by its code.
     * @param codModel the code of the ship model
     * @return an Optional containing the ShipModel if found, or empty if not found
     */
    Optional<ShipModel> getFromCode(String codModel);

    /**
     * Retrieves all ship models.
     * @return a Set of all ShipModel objects
     */
    Set<ShipModel> getAll();

    /**
     * Clears the cache of ship models.
     * @return the number of ship models cleared from the cache
     */
    int clearCache();
    
}
