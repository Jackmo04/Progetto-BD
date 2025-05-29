package porto.data.dao;

import java.sql.Connection;

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

    public ParkingSpace of(int codArea, int spaceNumber) throws DAOException {
        return new ParkingSpaceImpl(new ParkingAreaDAOImpl(connection).getFromCode(codArea), spaceNumber);
    }
}
