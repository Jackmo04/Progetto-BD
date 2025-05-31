package porto.data.api.dao;

import java.util.Optional;

import porto.data.api.ParkingArea;

public interface ParkingAreaDAO {

    /**
     * Retrieves a parking area by its code.
     * @param codArea the code of the parking area
     * @return an Optional containing the ParkingArea if found, or empty if not found
     */
    Optional<ParkingArea> getFromCode(int codArea);
}
