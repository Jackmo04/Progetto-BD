package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import porto.data.PayloadImpl;
import porto.data.PayloadTypeImpl;
import porto.data.api.Payload;
import porto.data.api.PayloadType;
import porto.data.api.dao.PayloadDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class PayloadDAOImpl implements PayloadDAO {

    private final Connection connection;

    /**
     * Constructor for PayloadDAOImpl.
     * @param connection the database connection
     */
    public PayloadDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void add(PayloadType type, int quantity, int codRequest) throws DAOException {
        Objects.requireNonNull(type, "PayloadType cannot be null");
        try (
            var statement = DAOUtils.prepare(connection, Queries.ADD_PAYLOAD, type.code(), quantity, codRequest);
        ) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Error adding payload", e);
        }
        
    }

    @Override
    public Set<Payload> getOfRequest(int codRichiesta) {
        Objects.requireNonNull(codRichiesta, "codRichiesta cannot be null");
        final Set<Payload> payloads = new HashSet<>();
        try (
            var statement = DAOUtils.prepare(connection, Queries.GET_PAYLOADS_OF_REQUEST, codRichiesta);
            var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var typeCode = resultSet.getInt("codTipoCarico");
                var typeName = resultSet.getString("Nome");
                var typeDesc = resultSet.getString("Descrizione");
                var typeUnitPrice = resultSet.getDouble("CostoUnitario");
                var type = new PayloadTypeImpl(typeCode, typeName, typeDesc, typeUnitPrice);
                int quantity = resultSet.getInt("quantita");
                payloads.add(new PayloadImpl(type, quantity));
            }
            return payloads;
        } catch (Exception e) {
            throw new DAOException("Error retrieving payloads of request", e);
        }
    }

}
