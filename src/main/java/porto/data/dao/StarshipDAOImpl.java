package porto.data.dao;

import java.sql.Connection;
import java.util.Set;

import porto.data.StarshipImpl;
import porto.data.api.Starship;
import porto.data.api.dao.StarshipDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class StarshipDAOImpl implements StarshipDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Starship> ofPerson(Connection connection, String CUIPerson) throws DAOException {
        Set<Starship> ships = Set.of();
        try (
            var statement = DAOUtils.prepare(connection, Queries.SHIPS_FROM_PERSON, CUIPerson);
            var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var plateNumber = resultSet.getString("Targa");
                var name = resultSet.getString("Nome");
                var codArea = resultSet.getInt("CodArea");
                var spaceNumber = resultSet.getInt("NumeroPosto");
                var parkingSpace = codArea == 0 || spaceNumber == 0
                    ? null
                    : new ParkingSpaceDAOImpl().of(connection, codArea, spaceNumber);
                var modelCode = resultSet.getString("CodModello");
                var model = new ShipModelDAOImpl().getFromCode(connection, modelCode);
                var capitanCUI = resultSet.getString("CUIcapitano");
                var capitan = new PersonDAOImpl().getFromCUI(connection, capitanCUI);
                var ship = new StarshipImpl(plateNumber, name, parkingSpace, model, capitan);
                ships.add(ship);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return ships;
    }
}
