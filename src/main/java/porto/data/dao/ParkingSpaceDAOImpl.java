package porto.data.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.ParkingSpaceImpl;
import porto.data.api.ParkingSpace;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.utils.DAOException;

public class ParkingSpaceDAOImpl implements ParkingSpaceDAO {

    private final Connection connection;
    
    /**
     * Constructor for ParkingSpaceDAOImpl.
     * @param connection the database connection
     */
    public ParkingSpaceDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Optional<ParkingSpace> of(int codArea, int spaceNumber) throws DAOException {
        var area = new ParkingAreaDAOImpl(connection).getFromCode(codArea);
        return area.map(a -> new ParkingSpaceImpl(a, spaceNumber));
    }
}
