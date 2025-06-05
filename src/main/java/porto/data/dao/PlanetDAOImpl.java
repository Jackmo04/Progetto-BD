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
        if (cache.stream().anyMatch(p -> p.codPlanet().equals(codPlanet))) {
            return cache.stream()
                .filter(p -> p.codPlanet().equals(codPlanet))
                .findFirst();
        }
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
    public Optional<Planet> getFromName(String name) throws DAOException {
        if (cache.stream().anyMatch(p -> p.name().equals(name))) {
            return cache.stream()
                .filter(p -> p.name().equals(name))
                .findFirst();
        }
        try (
            var statement = DAOUtils.prepare(connection, Queries.PLANET_FROM_NAME, name);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var cod = resultSet.getString("CodPianeta");
                var planetName = resultSet.getString("Nome");
                var planet = new PlanetImpl(cod, planetName);
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
    public Set<Planet> getAll() throws DAOException {
        try (
            var statement = DAOUtils.prepare(connection, Queries.PLANETS_ALL);
            var resultSet = statement.executeQuery();
        ) {
            Set<Planet> planets = new HashSet<>();
            while (resultSet.next()) {
                var cod = resultSet.getString("CodPianeta");
                if (cache.stream().anyMatch(p -> p.codPlanet().equals(cod))) {
                    planets.add(cache.stream()
                        .filter(p -> p.codPlanet().equals(cod))
                        .findFirst()
                        .orElseThrow()
                    );
                } else {
                    var name = resultSet.getString("Nome");
                    var planet = new PlanetImpl(cod, name);
                    planets.add(planet);
                    cache.add(planet);
                }
            }
            return planets;
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
