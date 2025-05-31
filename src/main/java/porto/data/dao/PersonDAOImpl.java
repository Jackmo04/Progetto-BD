package porto.data.dao;

import java.sql.Connection;
import java.util.Optional;
import java.util.Random;

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
                var ideology = Ideology.fromString(resultSet.getString("Ideologia"));
                var role = Role.fromString(resultSet.getString("Ruolo"));
                var cell = new CellDAOImpl(connection).getFromNumCell(resultSet.getInt("NumCella"));
                var planetCod = resultSet.getString("PianetaNascita");
                var bornPlanet = new PlanetDAOImpl(connection).getFromCodPlanet(planetCod).orElseThrow();
                var person = new PersonImpl(CUI, username, password, name, surname, razza, borndate, wanted, ideology,
                        role, cell, bornPlanet);
                return Optional.of(person);
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
    public void addPerson(String CUI, String username, String password, String name, String surname, String race,
            String borndate,
            Ideology ideology, Role role, String bornPlanet) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, QueryAction.S1_ADD_PERSON, CUI, username, password,
                        name, surname, race, borndate,
                        ideology.toString(), role.toString(), bornPlanet);) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isValidPerson(String cuiUsername, String password) throws DAOException {

        try (
                var statement = DAOUtils.prepare(connection, QueryAction.S2A_ACCESS_DB_REQUEST, cuiUsername, password);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                return true; // If we found a person with matching CUI and password, return true
            } else {
                return false; // No matching person found
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void arrestPerson(String CUI) throws DAOException {
        var freeCells = new CellDAOImpl(connection).getAllFreeCell();
        var cellToAssign = freeCells.get(new Random().nextInt(freeCells.size())).numCell();

        try (
                var statement = DAOUtils.prepare(connection, QueryAction.A3B_ARREST_PERSON, cellToAssign, CUI);
                var statement2 = DAOUtils.prepare(connection, QueryAction.A3C_DELETE_FROM_EQUIPE, CUI);) {
            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }

    }
}
