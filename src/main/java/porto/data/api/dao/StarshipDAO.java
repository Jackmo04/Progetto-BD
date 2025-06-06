package porto.data.api.dao;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import porto.data.api.Person;
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
    void addStarship(Starship starship) throws DAOException;

    /**
     * Adds a starship to the database and cache.
     * @param plateNumber the plate number of the starship
     * @param name the name of the starship
     * @param codModel the code of the ship model
     * @param captainCUI the CUI of the captain of the starship
     * @throws DAOException if an error occurs while accessing the database
     */
    void addStarship(String plateNumber, String name, String codModel, String captainCUI) throws DAOException;

    /**
     * Adds a crew member to a starship.
     * @param plateNumber the plate number of the starship
     * @param member the person to add as a crew member
     * @throws DAOException if an error occurs while accessing the database
     */
    void addCrewMember(String plateNumber, Person member) throws DAOException;

    /**
     * Adds a crew member to a starship.
     * <br><br>
     * <b>Note:</b> This method does not check the role of the person associated with the provided CUI.
     * Use {@link #addCrewMember(String, Person)} instead to ensure the person is a crew member.
     * @param plateNumber the plate number of the starship
     * @param memberCUI the CUI of the person to add as a crew member
     * @throws DAOException if an error occurs while accessing the database
     */
    void addCrewMember(String plateNumber, String memberCUI) throws DAOException;

    /**
     * Removes a crew member from a starship.
     * @param plateNumber the plate number of the starship
     * @param member the person to remove from the crew
     * @throws DAOException if an error occurs while accessing the database
     */
    void removeCrewMember(String plateNumber, Person member) throws DAOException;

    /**
     * Removes a crew member from a starship.
     * @param plateNumber the plate number of the starship
     * @param memberCUI the CUI of the person to remove from the crew
     * @throws DAOException if an error occurs while accessing the database
     */
    void removeCrewMember(String plateNumber, String memberCUI) throws DAOException;

    /**
     * Returns a map of the 50 (or less) starships who transported the most loads
     * and the total amount of they have transported.
     * @return a map where the key is the Starship and the value is the total amount of payloads transported
     * @throws DAOException if an error occurs while accessing the database
     */
    Map<Starship, Integer> get50TransportedMost() throws DAOException;

    /**
     * Clears the cache of starships.
     * @return the number of starships cleared from the cache
     */
    int clearCache();

    Set<Starship> getAll();
}
