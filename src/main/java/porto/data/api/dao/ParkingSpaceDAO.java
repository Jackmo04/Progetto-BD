package porto.data.api.dao;

import java.util.Optional;

import porto.data.api.ParkingSpace;
import porto.data.utils.DAOException;

public interface ParkingSpaceDAO {

    /**
     * Returns a ParkingSpace object identified by the area code and space number.
     * @param codArea the code of the parking area
     * @param spaceNumber the number of the parking space
     * @return an Optional containing the ParkingSpace if found, or empty if not found
     * @throws DAOException if an error occurs while accessing the database
     */
    Optional<ParkingSpace> of(int codArea, int spaceNumber) throws DAOException;

    /**
     * Returns a ParkingSpace object associated with a starship identified by its plate number.
     * @param starshipPlateNumber the plate number of the starship
     * @return an Optional containing the ParkingSpace if found, or empty if not found
     * @throws DAOException if an error occurs while accessing the database
     */
    Optional<ParkingSpace> ofStarship(String starshipPlateNumber) throws DAOException;

    /**
     * Returns the number of people currently on the station.
     * @return the number of people on the station
     * @throws DAOException if an error occurs while accessing the database
     */
    int getNumberOfPeopleOnStation() throws DAOException;

    /**
     * Clears the cache of ParkingSpace objects.
     * @return the number of ParkingSpace objects removed from the cache
     */
    int clearCache();
}
