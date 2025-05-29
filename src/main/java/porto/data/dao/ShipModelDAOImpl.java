package porto.data.dao;

import java.sql.Connection;

import porto.data.api.ShipModel;
import porto.data.api.dao.ShipModelDAO;

public class ShipModelDAOImpl implements ShipModelDAO {

    @Override
    public ShipModel getFromCode(Connection connection, String codModel) {
        // TODO Placeholder implementation
        return new porto.data.ShipModelImpl(codModel, "Model Name", 100, 500.0);
    }

}
