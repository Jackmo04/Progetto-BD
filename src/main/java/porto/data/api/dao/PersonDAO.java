package porto.data.api.dao;

import java.util.List;
import java.util.Optional;

import porto.data.api.Ideology;
import porto.data.api.Person;
import porto.data.api.PersonRole;
import porto.data.api.Planet;
import porto.data.utils.DAOException;

/**
 * Interface for Data Access Object (DAO) that provides methods to interact with
 * Person data.
 * This interface defines methods for retrieving a person by their CUIPerson.
 */
public interface PersonDAO {

    /**
     * Retrieves a person by their CUIPerson.
     * 
     * @param CUIPerson the CUI of the person
     * @return a Person object representing the person with the given CUIPerson
     * @throws DAOException if an error occurs while accessing the database
     */
    Optional<Person> getFromCUI(String CUIPerson) throws DAOException;

    /**
     * Adds a new person to the database.
     * 
     * @param CUI          the CUI of the person
     * @param Username     the username of the person
     * @param Password     the password of the person
     * @param Nome         the first name of the person
     * @param Cognome      the last name of the person
     * @param Razza        the race of the person
     * @param DataNascita the date of birth of the person
     * @param Ideologia    the ideology of the person
     * @param Ruolo        the role of the person
     * @param NumCella     the cell number of the person
     * @param PianetaNascita the planet of birth of the person
     * @throws DAOException if an error occurs while accessing the database
     */
    void addPerson(String CUI, String username, String password ,String name, String surname, String race, String borndate,
            boolean wanted, Ideology ideology, PersonRole role, Planet bornPlanet) throws DAOException;

    /**
     * Retrieves a person by their CUI or username and password.
     * @param cuiUsername the CUI or username of the person
     * @param password the password of the person
     * @return an Optional containing the Person object if the credentials are valid,
     *         or an empty Optional if the credentials are invalid
     * @throws DAOException if an error occurs while accessing the database
     */
    Optional<Person> loginAndGetUser(String cuiUsername , String password) throws DAOException;

    /**
     * Clears the cache of persons.
     * @return the number of persons cleared from the cache
     */
    int clearCache();

    /**
     * Arrests a person by their CUI and assigns them to a cell.
     * This method updates the person's status to "arrested" and assigns them to a
     * specific cell.
     * @param CUI the CUI of the person to be arrested 
     * @throws DAOException if an error occurs while accessing the database
     */
    public void arrestPerson(String CUI ) throws DAOException;

    /**
     * Retrieves a list of persons who are part of the crew of a starship.
     * 
     * @param plate the plate number of the starship
     * @return a list of Person objects representing the crew members of the starship
     * @throws DAOException if an error occurs while accessing the database
     */
    public List<Person> getEquipeOfStarship( String plate) throws DAOException;

    Optional<Person> getFromUsername(String username);
}
