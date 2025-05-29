package porto.data.api.dao;

import porto.data.api.ParkingArea;

public interface ParkingAreaDAO {

    /**
     * Retrieves a parking area by its code.
     * @param codArea the code of the parking area
     * @return the parking area if found, or null if not found
     */
    ParkingArea getFromCode(int codArea);
}
