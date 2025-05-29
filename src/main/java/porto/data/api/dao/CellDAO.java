package porto.data.api.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.api.Cell;
import porto.data.api.Person;
import porto.data.utils.DAOException;

public interface CellDAO
 {

    /**
     * Retrieves a Person from the database using the cell number.
     *
     * @param connection the database connection
     * @param numCell the cell number of the person
     * @return the Person associated with the given cell number
     * @throws DAOException if an error occurs during the retrieval
     */
    Optional<Cell> getFromNumCell(Connection connection, Integer numCell) throws DAOException;
}