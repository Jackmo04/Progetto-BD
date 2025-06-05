package porto.model;

import java.sql.Connection;
import java.util.Set;

import porto.data.api.Person;
import porto.data.api.Planet;

public interface Model {
    
    boolean login(String username, String password);

    Person getLoggedUser();

    Set<Planet> getAllPlanets();

    boolean isUserRegistered(String cui, String username);
    
    /**
     * Create a model from a database connection.
     * @param connection
     * @return
     */
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

    void registerUser(String cui, String username, String password, String name, String surname, String race,
            String dob, boolean wanted, String ideology, boolean isCaptain, String planet);

}
