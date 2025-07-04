package porto.data.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import porto.data.StarshipImpl;
import porto.data.api.Person;
import porto.data.api.PersonRole;
import porto.data.api.Starship;
import porto.data.api.dao.StarshipDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class StarshipDAOImpl implements StarshipDAO {

    private final Connection connection;
    private final Set<Starship> cache = new HashSet<>();

    /**
     * Constructor for StarshipDAOImpl.
     * @param connection the database connection
     */
    public StarshipDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Starship> fromPlate(String plateNumber) throws DAOException {
        try (
            var statement = DAOUtils.prepare(connection, Queries.SHIP_FROM_PLATE, plateNumber);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                if (cache.stream().anyMatch(ship -> ship.plateNumber().equals(plateNumber))) {
                    return Optional.of(cache.stream().filter(ship -> ship.plateNumber().equals(plateNumber)).findFirst().orElseThrow());
                } else {
                    var name = resultSet.getString("Nome");
                    var modelCode = resultSet.getString("CodModello");
                    var model = new ShipModelDAOImpl(connection).getFromCode(modelCode).orElseThrow();
                    var capitanCUI = resultSet.getString("CUIcapitano");
                    var capitan = new PersonDAOImpl(connection).getFromCUI(capitanCUI).orElseThrow();
                    
                    var ship = new StarshipImpl(plateNumber, name, model, capitan);
                    cache.add(ship);
                    return Optional.of(ship);
                }
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
    public Set<Starship> ofPerson(String CUIPerson) throws DAOException {
        Objects.requireNonNull(CUIPerson, "CUI of the person cannot be null");
        Set<Starship> ships = new HashSet<>();
        try (
            var statement = DAOUtils.prepare(connection, Queries.SHIPS_FROM_PERSON, CUIPerson);
            var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var plateNumber = resultSet.getString("Targa");
                if (cache.stream().anyMatch(ship -> ship.plateNumber().equals(plateNumber))) {
                    ships.add(cache.stream().filter(ship -> ship.plateNumber().equals(plateNumber)).findFirst().orElseThrow());
                } else {
                    var name = resultSet.getString("Nome");
                    var modelCode = resultSet.getString("CodModello");
                    var model = new ShipModelDAOImpl(connection).getFromCode(modelCode).orElseThrow();
                    var capitanCUI = resultSet.getString("CUIcapitano");
                    var capitan = new PersonDAOImpl(connection).getFromCUI(capitanCUI).orElseThrow();
                    
                    var ship = new StarshipImpl(plateNumber, name, model, capitan);
                    cache.add(ship);
                    ships.add(ship);
                }
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return ships;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStarship(Starship starship) throws DAOException {
        Objects.requireNonNull(starship, "Starship cannot be null");
        var plateNumber = starship.plateNumber();
        var name = starship.name();
        var modelCode = starship.model().codModel();
        var capitanCUI = starship.capitan().CUI();
        this.addStarship(plateNumber, name, modelCode, capitanCUI);
        cache.add(starship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStarship(String plateNumber, String name, String codModel, String captainCUI) throws DAOException {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(codModel, "Model code cannot be null");
        Objects.requireNonNull(captainCUI, "Captain CUI cannot be null");
        try (
            var statement = DAOUtils.prepare(connection, Queries.INSERT_STARSHIP, plateNumber, name, codModel, captainCUI);
        ) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCrewMember(String plateNumber, Person member) throws DAOException {
        if (member.role() != PersonRole.CREW_MEMBER) {
            throw new IllegalArgumentException("Person must be a crew member to be added to a starship.");
        }
        this.addCrewMember(plateNumber, member.CUI());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCrewMember(String plateNumber, String memberCUI) throws DAOException {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        Objects.requireNonNull(memberCUI, "Crew member CUI cannot be null");
        try (
            var statement = DAOUtils.prepare(connection, Queries.INSERT_CREW_MEMBER, plateNumber, memberCUI);
        ) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCrewMember(String plateNumber, Person member) throws DAOException {
        this.removeCrewMember(plateNumber, member.CUI());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCrewMember(String plateNumber, String memberCUI) throws DAOException {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        Objects.requireNonNull(memberCUI, "Crew member CUI cannot be null");
        try (
            var statement = DAOUtils.prepare(connection, Queries.REMOVE_CREW_MEMBER, plateNumber, memberCUI);
        ) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Starship, Integer> get50TransportedMost() throws DAOException {
        Map<Starship, Integer> starships = new HashMap<>();
        try (
            var statement = DAOUtils.prepare(connection, Queries.STARSHIPS_TRANSPORTED_MOST);
            var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var shipPlate = resultSet.getString("TargaAstronave");
                var totalPayload = resultSet.getInt("QtaTot");
                var starship = this.fromPlate(shipPlate).orElseThrow();
                starships.put(starship, totalPayload);
            }
            return starships;
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

    @Override
    public Set<Starship> getAll() {
        try (
            var statement = DAOUtils.prepare(connection, Queries.ALL_STARSHIPS);
            var resultSet = statement.executeQuery();
        ) {
            Set<Starship> ships = new HashSet<>();
            while (resultSet.next()) {
                var plateNumber = resultSet.getString("Targa");
                if (cache.stream().anyMatch(ship -> ship.plateNumber().equals(plateNumber))) {
                    ships.add(cache.stream().filter(ship -> ship.plateNumber().equals(plateNumber)).findFirst().orElseThrow());
                } else {
                    var name = resultSet.getString("Nome");
                    var modelCode = resultSet.getString("CodModello");
                    var model = new ShipModelDAOImpl(connection).getFromCode(modelCode).orElseThrow();
                    var capitanCUI = resultSet.getString("CUIcapitano");
                    var capitan = new PersonDAOImpl(connection).getFromCUI(capitanCUI).orElseThrow();
                    
                    var ship = new StarshipImpl(plateNumber, name, model, capitan);
                    cache.add(ship);
                    ships.add(ship);
                }
            }
            return ships;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}
