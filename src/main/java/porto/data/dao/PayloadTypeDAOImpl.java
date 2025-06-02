package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import porto.data.PayloadTypeImpl;
import porto.data.api.PayloadType;
import porto.data.api.dao.PayloadTypeDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class PayloadTypeDAOImpl implements PayloadTypeDAO {

    private final Connection connection;
    private final Set<PayloadType> cache = new HashSet<>();

    /**
     * Constructor for PlanetDAOImpl.
     * 
     * @param connection the database connection
     */
    public PayloadTypeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PayloadType> getAll() throws DAOException {
        try (
            var statement = DAOUtils.prepare(connection, Queries.PAYLOAD_TYPE_ALL);
            var resultSet = statement.executeQuery();
        ) {
            Set<PayloadType> types = new HashSet<>();
            while (resultSet.next()) {
                var code = resultSet.getInt("CodTipoCarico");
                if (cache.stream().anyMatch(p -> p.code() == code)) {
                    types.add(cache.stream()
                        .filter(p -> p.code() == code)
                        .findFirst()
                        .orElseThrow()
                    );
                } else {
                    var name = resultSet.getString("Nome");
                    var description = resultSet.getString("Descrizione");
                    var unitPrice = resultSet.getDouble("PrezzoUnitario");
                    var type = new PayloadTypeImpl(code, name, description, unitPrice);
                    cache.add(type);
                    types.add(type);
                }
            }
            return types;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
