package porto.data.dao;

import java.sql.Connection;

import porto.data.ParkingAreaImpl;
import porto.data.api.ParkingArea;
import porto.data.api.dao.ParkingAreaDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class ParkingAreaDAOImpl implements ParkingAreaDAO {

    @Override
    public ParkingArea getFromCode(Connection connection, int codArea) {
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
