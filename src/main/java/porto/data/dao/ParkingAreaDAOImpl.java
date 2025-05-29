package porto.data.dao;

import java.sql.Connection;

import porto.data.ParkingAreaImpl;
import porto.data.api.ParkingArea;
import porto.data.api.dao.ParkingAreaDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class ParkingAreaDAOImpl implements ParkingAreaDAO {

    private final Connection connection;

    /**
     * Constructor for ParkingAreaDAOImpl.
     * @param connection the database connection
     */
    public ParkingAreaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ParkingArea getFromCode(int codArea) {
        try (
            var statement = DAOUtils.prepare(connection, Queries.AREA_FROM_CODE, codArea);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var name = resultSet.getString("Nome");
                var area = new ParkingAreaImpl(codArea, name);
                return area;
            } else {
                return null; // No area found with the given code
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}
