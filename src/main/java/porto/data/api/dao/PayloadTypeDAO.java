package porto.data.api.dao;

import java.util.Set;

import porto.data.api.PayloadType;
import porto.data.utils.DAOException;

public interface PayloadTypeDAO {

    /**
     * Retrieves all payload types from the database.
     * @return a set of all payload types
     * @throws DAOException if an error occurs while retrieving the payload types
     */
    Set<PayloadType> getAll() throws DAOException;
}
