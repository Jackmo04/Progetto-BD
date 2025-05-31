package porto.data.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.ShipModelImpl;
import porto.data.api.ShipModel;
import porto.data.api.dao.ShipModelDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class ShipModelDAOImpl implements ShipModelDAO {

    private final Connection connection;

    /**
     * Constructor for ShipModelDAOImpl.
     * @param connection the database connection
     */
    public ShipModelDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<ShipModel> getFromCode(String codModel) {
        try (
            var statement = DAOUtils.prepare(connection, Queries.MODEL_FROM_CODE, codModel);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var name = resultSet.getString("Nome");
                var size = resultSet.getInt("DimensioneArea");
                var tax = resultSet.getDouble("Prezzo");
                return Optional.of(new ShipModelImpl(codModel, name, size, tax));
            } else {
                return Optional.empty(); // No ship model found with the given code
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}
