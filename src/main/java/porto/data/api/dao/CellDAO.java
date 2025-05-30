package porto.data.api.dao;

import java.util.Optional;

import porto.data.CellImpl;
import porto.data.utils.DAOException;

/**
 * Interface for Data Access Object (DAO) that provides methods to interact with Cell data.
 */
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
    Optional<CellImpl> getFromNumCell(Integer numCell) throws DAOException;
}