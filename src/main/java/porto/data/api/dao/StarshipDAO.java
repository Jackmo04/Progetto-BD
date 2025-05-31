package porto.data.api.dao;

import java.util.Optional;
import java.util.Set;

import porto.data.api.Starship;
import porto.data.utils.DAOException;

public interface StarshipDAO {

    /**
     * Returns a starship by its plate number.
     * @param plateNumber the plate number of the starship
     * @return an Optional containing the Starship if found, or empty if not found
     * @throws DAOException if an error occurs while accessing the database
     */
    Optional<Starship> fromPlate(String plateNumber) throws DAOException;

    /**
     * Returns all the starships of which the specified person is the capitan or a crew member.
     * @param CUIPerson the CUI of the person whose starships are to be retrieved
     * @return a set of Starship objects owned by the person
     * @throws DAOException if an error occurs while accessing the database
     */
    Set<Starship> ofPerson(String CUIPerson) throws DAOException;

    /**
     * Adds a starship to the database and cache.
     * @param starship the starship to add
     * @throws DAOException if an error occurs while accessing the database
     */
    void add(Starship starship) throws DAOException;

    /**
     * Clears the cache of starships.
     * @return the number of starships cleared from the cache
     */
    int clearCache();
}
