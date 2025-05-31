package porto.data.api.dao;

import java.util.Optional;

import porto.data.api.Planet;
import porto.data.utils.DAOException;

/**
 * Interface for Data Access Object (DAO) that provides methods to interact with Planet data.
 * This interface defines methods for retrieving a planet by its codPlanet.
 */
public interface PlanetDAO {

    /**
     * Retrieves a planet by its codPlanet.
     * @param codPlanet the code of the planet
     * @return an Optional containing the Planet object if found, or empty if not found
     * throws DAOException if an error occurs while accessing the database
     * @throws DAOException if an error occurs while accessing the database
     * */
    Optional<Planet> getFromCodPlanet( String codPlanet) throws DAOException;

    /**
     * Clears the cache of planets.
     * @return the number of planets removed from the cache
     */
    int clearCache();
}