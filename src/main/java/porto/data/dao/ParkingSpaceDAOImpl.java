package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import porto.data.ParkingSpaceImpl;
import porto.data.api.ParkingSpace;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class ParkingSpaceDAOImpl implements ParkingSpaceDAO {

    private final Connection connection;
    private final Set<ParkingSpace> cache = new HashSet<>();
    
    /**
     * Constructor for ParkingSpaceDAOImpl.
     * @param connection the database connection
     */
    public ParkingSpaceDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ParkingSpace> of(int codArea, int spaceNumber) throws DAOException {
        if (cache.stream().anyMatch(space -> space.parkingArea().codArea() == codArea && space.spaceNumber() == spaceNumber)) {
            return cache.stream()
                .filter(space -> space.parkingArea().codArea() == codArea && space.spaceNumber() == spaceNumber)
                .findFirst();
        }
        var area = new ParkingAreaDAOImpl(connection).getFromCode(codArea);
        Optional<ParkingSpace> space = area.map(a -> new ParkingSpaceImpl(a, spaceNumber));
        if (space.isPresent()) {
            cache.add(space.get());
        }
        return space;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ParkingSpace> ofStarship(String starshipPlateNumber) throws DAOException {
        try (
            var statement = DAOUtils.prepare(connection, Queries.SPACE_FROM_PLATE, starshipPlateNumber);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var codArea = resultSet.getInt("CodArea");
                var spaceNumber = resultSet.getInt("NumeroPosto");
                return this.of(codArea, spaceNumber);
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
