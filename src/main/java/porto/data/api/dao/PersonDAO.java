package porto.data.api.dao;

import java.util.Optional;

import porto.data.api.Person;
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
            String ideology, String role, String bornPlanet) throws DAOException;
}
