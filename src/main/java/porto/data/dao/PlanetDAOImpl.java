package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import porto.data.PlanetImpl;
import porto.data.api.Planet;
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
    private final Set<Planet> cache = new HashSet<>();

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
    public Optional<Planet> getFromCodPlanet(String codPlanet) throws DAOException {

        try (
            var statement = DAOUtils.prepare(connection, Queries.PLANET_FROM_CODPLANET, codPlanet);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var cod = resultSet.getString("CodPianeta");
                var name = resultSet.getString("Nome");
                var planet = new PlanetImpl(cod, name);
                cache.add(planet);
                return Optional.of(planet);
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

}
