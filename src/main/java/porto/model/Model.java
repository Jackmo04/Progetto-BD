package porto.model;

import java.sql.Connection;

import porto.data.api.Person;

public interface Model {
    
    boolean login(String username, String password);

    Person getLoggedUser();

    /**
     * Create a model from a database connection.
     * @param connection
     * @return
     */
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
