package porto.model;

import java.sql.Connection;

public interface Model {
    

    /**
     * Create a model from a database connection.
     * @param connection
     * @return
     */
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
