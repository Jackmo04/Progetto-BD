package porto.data.api.dao;

import java.util.Set;

import porto.data.api.Payload;
import porto.data.api.PayloadType;
import porto.data.utils.DAOException;

public interface PayloadDAO {

    /**
     * Adds a new payload to the database.
     * @param type the type of payload to add
     * @param quantity the quantity of the payload
     * @param codRequest the code of the request associated with the payload
     * @throws DAOException if an error occurs while adding the payload
     */
    void add(PayloadType type, int quantity, int codRequest) throws DAOException;

    Set<Payload> getOfRequest(int codRichiesta);
}
