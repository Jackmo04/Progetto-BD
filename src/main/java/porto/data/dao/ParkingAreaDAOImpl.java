package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import porto.data.ParkingAreaImpl;
import porto.data.api.ParkingArea;
import porto.data.api.dao.ParkingAreaDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class ParkingAreaDAOImpl implements ParkingAreaDAO {

    private final Connection connection;
    private final Set<ParkingArea> cache = new HashSet<>();

    /**
     * Constructor for ParkingAreaDAOImpl.
     * @param connection the database connection
     */
    public ParkingAreaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ParkingArea> getFromCode(int codArea) {
        if (cache.stream().anyMatch(area -> area.codArea() == codArea)) {
            return cache.stream().filter(area -> area.codArea() == codArea).findFirst();
        }
        try (
            var statement = DAOUtils.prepare(connection, Queries.AREA_FROM_CODE, codArea);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var name = resultSet.getString("Nome");
                var area = new ParkingAreaImpl(codArea, name);
                cache.add(area);
                return Optional.of(area);
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
