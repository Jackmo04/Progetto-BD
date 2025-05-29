package porto.data.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.PlanetImpl;
import porto.data.api.Planet;
import porto.data.api.dao.PlanetDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class PlanetDAOImpl implements PlanetDAO {

    @Override
    public Optional<Planet> getFromCodPlanet(Connection connection, String codPlanet) throws DAOException {

        try (
                var statement = DAOUtils.prepare(connection, Queries.PLANET_FROM_CODPLANET, codPlanet);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                var cod = resultSet.getString("CodPianeta");
                var name = resultSet.getString("Nome");
                var planet = new PlanetImpl(cod, name);
                return Optional.of(planet);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}
