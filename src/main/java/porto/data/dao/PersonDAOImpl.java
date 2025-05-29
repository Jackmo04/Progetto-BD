package porto.data.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.PersonImpl;
import porto.data.api.Person;
import porto.data.api.dao.PersonDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class PersonDAOImpl implements PersonDAO{

        private final Connection connection;

    /**
     * Constructor for PlanetDAOImpl.
     * 
     * @param connection the database connection
     */
    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Person> getFromCUI(String CUIPerson) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, Queries.PERSON_FROM_CUI, CUIPerson);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                var CUI = resultSet.getString("CUI");
                var username = resultSet.getString("Username");
                var password = resultSet.getString("Password");
                var name = resultSet.getString("Nome");
                var surname = resultSet.getString("Cognome");
                var razza = resultSet.getString("Razza");
                var borndate = resultSet.getString("DataNascita");
                var wanted = resultSet.getBoolean("Ricercato");
                var ideology = resultSet.getString("Ideologia");
                var role = resultSet.getString("Ruolo");
                var cell = new CellDAOImpl(connection).getFromNumCell(resultSet.getInt("NumCella"));
                var bornPlanet = new PlanetDAOImpl(connection).getFromCodPlanet(resultSet.getString("PianetaNascita")).get();
                var person = new PersonImpl(CUI, username, password, name, surname, razza, borndate, wanted, ideology, role, cell, bornPlanet);
                return Optional.of(person);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }

}
}
