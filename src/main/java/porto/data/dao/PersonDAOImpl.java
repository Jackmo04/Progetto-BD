package porto.data.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Random;

import porto.data.PersonImpl;
import porto.data.api.Ideology;
import porto.data.api.Person;
import porto.data.api.Role;
import porto.data.api.dao.PersonDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

/**
 * Implementation of the PersonDAO interface.
 * Provides methods to interact with person data in the database.
 */
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
                cache.add(person);
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
                var statement = DAOUtils.prepare(connection, Queries.ADD_PERSON, CUI, username, password,
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
    public boolean isValidPerson(String cuiUsername, String password) throws DAOException {

        try (
                var statement = DAOUtils.prepare(connection, Queries.ACCESS_DB_REQUEST, cuiUsername, password);
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
    public int clearCache() {
        int size = cache.size();
        cache.clear();
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void arrestPerson(String CUI) throws DAOException {
        var freeCells = new CellDAOImpl(connection).getAllFreeCell();
        var cellToAssign = freeCells.get(new Random().nextInt(freeCells.size())).numCell();

        try (
                var statement = DAOUtils.prepare(connection, Queries.ARREST_PERSON, cellToAssign, CUI);
                var statement2 = DAOUtils.prepare(connection, Queries.DELETE_FROM_EQUIPE, CUI);) {
            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Person> getEquipeOfStarship(String plate) throws DAOException {
        final List<String> equipe = new ArrayList<>();
        try (
                var statement = DAOUtils.prepare(connection, Queries.SHOW_EQUIPE_STARSHIP, plate);
                var resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                equipe.add(resultSet.getString("CUI"));

            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return equipe.stream().map(t -> getFromCUI(t).get())
                .distinct()
                .toList();
    }
}
