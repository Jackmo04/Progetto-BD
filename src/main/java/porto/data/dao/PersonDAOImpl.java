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
import porto.data.api.PersonRole;
import porto.data.api.Planet;
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
                var name = resultSet.getString("Nome");
                var surname = resultSet.getString("Cognome");
                var razza = resultSet.getString("Razza");
                var borndate = resultSet.getString("DataNascita");
                var wanted = resultSet.getBoolean("Ricercato");
                var ideology = Ideology.fromString(resultSet.getString("Ideologia"));
                var role = PersonRole.fromString(resultSet.getString("Ruolo"));
                var cell = new CellDAOImpl(connection).getFromNumCell(resultSet.getInt("NumCella"));
                var planetCod = resultSet.getString("PianetaNascita");
                var bornPlanet = new PlanetDAOImpl(connection).getFromCodPlanet(planetCod).orElseThrow();
                var person = new PersonImpl(CUI, username, name, surname, razza, borndate, wanted, ideology,
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
            String borndate, boolean wanted,
            Ideology ideology, PersonRole role, Planet bornPlanet) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_PERSON, CUI, username, password,
                        name, surname, race, borndate, wanted ? 1 : 0,
                        ideology.toString(), role.toString(), bornPlanet.codPlanet());) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> loginAndGetUser(String cuiUsername, String password) throws DAOException {
        try (
            var statement = DAOUtils.prepare(connection, Queries.ACCESS_DB_REQUEST, cuiUsername, password);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                return Optional.of(this.getFromCUI(resultSet.getString("CUI")).get());
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

    @Override
    public Optional<Person> getFromUsername(String username) {
        if (cache.stream().anyMatch(person -> person.username().equals(username))) {
            return cache.stream()
                    .filter(person -> person.username().equals(username))
                    .findFirst();
        }
        try (
                var statement = DAOUtils.prepare(connection, Queries.PERSON_FROM_USERNAME, username);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                var CUI = resultSet.getString("CUI");
                return getFromCUI(CUI);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}
