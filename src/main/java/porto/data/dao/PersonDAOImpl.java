package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import porto.data.PersonImpl;
import porto.data.api.Ideology;
import porto.data.api.Person;
import porto.data.api.Role;
import porto.data.api.dao.PersonDAO;
import porto.data.queries.Queries;
import porto.data.queries.QueryAction;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class PersonDAOImpl implements PersonDAO {

    private final Connection connection;
    private final Set<Person> cache = new HashSet<>();

    /**
     * Constructor for PlanetDAOImpl.
     * 
     * @param connection the database connection
     */
    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> getFromCUI(String CUIPerson) throws DAOException {
        if (cache.stream().anyMatch(person -> person.CUI().equals(CUIPerson))) {
            return cache.stream()
                .filter(person -> person.CUI().equals(CUIPerson))
                .findFirst();
        }
        try (
            var statement = DAOUtils.prepare(connection, Queries.PERSON_FROM_CUI, CUIPerson);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var CUI = resultSet.getString("CUI");
                var username = resultSet.getString("Username");
                var password = resultSet.getString("Password");
                var name = resultSet.getString("Nome");
                var surname = resultSet.getString("Cognome");
                var razza = resultSet.getString("Razza");
                var borndate = resultSet.getString("DataNascita");
                var wanted = resultSet.getBoolean("Ricercato");
                var ideology = Ideology.fromString(resultSet.getString("Ideologia"));
                var role = Role.fromString(resultSet.getString("Ruolo"));
                var cell = new CellDAOImpl(connection).getFromNumCell(resultSet.getInt("NumCella"));
                var planetCod = resultSet.getString("PianetaNascita");
                var bornPlanet = new PlanetDAOImpl(connection).getFromCodPlanet(planetCod).orElseThrow();
                var person = new PersonImpl(CUI, username, password, name, surname, razza, borndate, wanted, ideology,
                        role, cell, bornPlanet);
                cache.add(person);
                return Optional.of(person);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void addPerson(String CUI, String username, String password, String name, String surname, String race,
            String borndate,
            Ideology ideology, Role role, String bornPlanet) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, QueryAction.S1_ADD_PERSON, CUI, username, password,
                        name, surname, race, borndate,
                        ideology.toString(), role.toString(), bornPlanet);
        ) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int clearCache() {
        int size = cache.size();
        cache.clear();
        return size;
    }
}
