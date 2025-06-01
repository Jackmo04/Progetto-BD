package porto.data.api.dao;

import java.util.Set;

import porto.data.api.FlightPurpose;
import porto.data.utils.DAOException;

public interface FlightPurposeDAO {

    /**
     * Retrieves the flight purpose by its code.
     *
     * @param code the code of the flight purpose
     * @return the flight purpose associated with the given code
     * @throws DAOException if an error occurs while accessing the database
     */
    Set<FlightPurpose> getSetFlightPurposeByCode() throws DAOException;

}
