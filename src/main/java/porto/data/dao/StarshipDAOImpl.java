package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import porto.data.StarshipImpl;
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
    public Set<Starship> ofPerson(String CUIPerson) throws DAOException {
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
                    var codArea = resultSet.getInt("CodArea");
                    var spaceNumber = resultSet.getInt("NumeroPosto");
                    var parkingSpace = new ParkingSpaceDAOImpl(connection).of(codArea, spaceNumber);
                    var modelCode = resultSet.getString("CodModello");
                    var model = new ShipModelDAOImpl(connection).getFromCode(modelCode).orElseThrow();
                    var capitanCUI = resultSet.getString("CUIcapitano");
                    var capitan = new PersonDAOImpl(connection).getFromCUI(capitanCUI).orElseThrow();
                    
                    var ship = new StarshipImpl(plateNumber, name, parkingSpace, model, capitan);
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
    public int clearCache() {
        int size = cache.size();
        cache.clear();
        return size;
    }
}
