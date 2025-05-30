package porto.data.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.PlanetImpl;
import porto.data.api.dao.PlanetDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

/**
 * Implementation of the PlanetDAO interface for accessing Planet data from the database.
 * This class provides methods to retrieve Planet information based on specific criteria.
 */
public class PlanetDAOImpl implements PlanetDAO {

    private final Connection connection;

    /**
     * Constructor for PlanetDAOImpl.
     * 
     * @param connection the database connection
     */
    public PlanetDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlanetImpl> getFromCodPlanet(String codPlanet) throws DAOException {

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
