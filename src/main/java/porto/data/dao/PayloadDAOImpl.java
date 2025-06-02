package porto.data.dao;

import java.sql.Connection;
import java.util.Objects;

import porto.data.PayloadImpl;
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

    public Payload add(PayloadType type, int quantity, int codRequest) throws DAOException {
        Objects.requireNonNull(type, "PayloadType cannot be null");
        try (
            var statement = DAOUtils.prepare(connection, Queries.ADD_PAYLOAD, type.code(), quantity, codRequest);
        ) {
            statement.executeUpdate();
            return new PayloadImpl(type, quantity, codRequest, quantity * type.unitPrice());
        } catch (Exception e) {
            throw new DAOException("Error adding payload", e);
        }
        
    }

}
