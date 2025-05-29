package porto.data.api.dao;

import porto.data.api.ParkingSpace;
import porto.data.utils.DAOException;

public interface ParkingSpaceDAO {

    /**
     * Returns a ParkingSpace object identified by the area code and space number.
     * @param codArea the code of the parking area
     * @param spaceNumber the number of the parking space
     * @return a ParkingSpace object
     * @throws DAOException if an error occurs while accessing the database
     */
    ParkingSpace of(int codArea, int spaceNumber) throws DAOException;
}
