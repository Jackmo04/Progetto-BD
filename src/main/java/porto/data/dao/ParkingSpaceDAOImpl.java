package porto.data.dao;

import java.sql.Connection;

import porto.data.ParkingSpaceImpl;
import porto.data.api.ParkingSpace;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.utils.DAOException;

public class ParkingSpaceDAOImpl implements ParkingSpaceDAO{

    public ParkingSpace of(Connection connection, int codArea, int spaceNumber) throws DAOException {
        return new ParkingSpaceImpl(new ParkingAreaDAOImpl().getFromCode(connection, codArea), spaceNumber);
    }
}
