package porto.data.api.dao;

import java.util.Optional;

import porto.data.PersonImpl;
import porto.data.utils.DAOException;

/**
 * Interface for Data Access Object (DAO) that provides methods to interact with Person data.
 * This interface defines methods for retrieving a person by their CUIPerson.
 */
public interface PersonDAO {

    /**
     * Retrieves a person by their CUIPerson.
     * @param CUIPerson the CUI of the person
     * @return a Person object representing the person with the given CUIPerson
     * @throws DAOException if an error occurs while accessing the database
     */
    Optional<PersonImpl> getFromCUI(String CUIPerson) throws DAOException;
}
