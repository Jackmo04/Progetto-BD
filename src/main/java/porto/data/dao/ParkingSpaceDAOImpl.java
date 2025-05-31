package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import porto.data.ParkingSpaceImpl;
import porto.data.api.ParkingSpace;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.utils.DAOException;

public class ParkingSpaceDAOImpl implements ParkingSpaceDAO {

    private final Connection connection;
    private final Set<ParkingSpace> cache = new HashSet<>();
    
    /**
     * Constructor for ParkingSpaceDAOImpl.
     * @param connection the database connection
     */
    public ParkingSpaceDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    public Optional<ParkingSpace> of(int codArea, int spaceNumber) throws DAOException {
        // Check if the parking space is already in the cache
        if (cache.stream().anyMatch(space -> space.parkingArea().codArea() == codArea && space.spaceNumber() == spaceNumber)) {
            return cache.stream()
                .filter(space -> space.parkingArea().codArea() == codArea && space.spaceNumber() == spaceNumber)
                .findFirst();
        }
        var area = new ParkingAreaDAOImpl(connection).getFromCode(codArea);
        return area.map(a -> new ParkingSpaceImpl(a, spaceNumber));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int clearCache() {
        int size = cache.size();
        cache.clear();
        return size;
    }
}
