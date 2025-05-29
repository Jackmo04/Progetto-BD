package porto.data.api.dao;

import java.sql.Connection;

import porto.data.api.Person;
import porto.data.utils.DAOException;

public interface PersonDAO {

    /**
     * Retrieves a person by their CUIPerson.
     * @param connection the database connection
     * @param CUIPerson the CUI of the person
     * @return a Person object representing the person with the given CUIPerson
     * @throws DAOException if an error occurs while accessing the database
     */
    Person getFromCUI(Connection connection, String CUIPerson) throws DAOException;
}
