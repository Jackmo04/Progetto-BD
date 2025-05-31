package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import porto.data.ShipModelImpl;
import porto.data.api.ShipModel;
import porto.data.api.dao.ShipModelDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class ShipModelDAOImpl implements ShipModelDAO {

    private final Connection connection;
    private final Set<ShipModel> cache = new HashSet<>();

    /**
     * Constructor for ShipModelDAOImpl.
     * @param connection the database connection
     */
    public ShipModelDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ShipModel> getFromCode(String codModel) {
        if (cache.stream().anyMatch(model -> model.codModel().equals(codModel))) {
            return cache.stream().filter(model -> model.codModel().equals(codModel)).findFirst();
        }
        try (
            var statement = DAOUtils.prepare(connection, Queries.MODEL_FROM_CODE, codModel);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var name = resultSet.getString("Nome");
                var size = resultSet.getInt("DimensioneArea");
                var tax = resultSet.getDouble("Prezzo");
                var model = new ShipModelImpl(codModel, name, size, tax);
                cache.add(model);
                return Optional.of(model);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
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
