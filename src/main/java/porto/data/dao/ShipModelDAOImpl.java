package porto.data.dao;

import java.sql.Connection;

import porto.data.ShipModelImpl;
import porto.data.api.ShipModel;
import porto.data.api.dao.ShipModelDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class ShipModelDAOImpl implements ShipModelDAO {

    @Override
    public ShipModel getFromCode(Connection connection, String codModel) {
        try (
            var statement = DAOUtils.prepare(connection, Queries.MODEL_FROM_CODE, codModel);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var name = resultSet.getString("Nome");
                var size = resultSet.getInt("DimensioneArea");
                var tax = resultSet.getDouble("Prezzo");
                return new ShipModelImpl(codModel, name, size, tax);
            } else {
                return null; // No ship model found with the given code
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}
