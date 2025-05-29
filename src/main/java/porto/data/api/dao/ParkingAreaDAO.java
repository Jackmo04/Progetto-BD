package porto.data.api.dao;

import java.sql.Connection;

import porto.data.api.ParkingArea;

public interface ParkingAreaDAO {

    /**
     * Retrieves a parking area by its code.
     * @param connection the database connection
     * @param codArea the code of the parking area
     * @return the parking area if found, or null if not found
     */
    ParkingArea getFromCode(Connection connection, int codArea);
}
