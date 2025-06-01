package porto.data.dao;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import porto.data.FlightPurposeImpl;
import porto.data.api.FlightPurpose;
import porto.data.api.dao.FlightPurposeDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class FlightPurposeDAOImpl implements FlightPurposeDAO {

    private final Connection connection;
    private final Set<FlightPurpose> cache = new HashSet<>();

    /**
     * Constructor for RequestDAOImpl.
     * @param connection the database connection
     */
    public FlightPurposeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Set<FlightPurpose> getAll() throws DAOException {
        var setPurpose = new HashSet<FlightPurpose>();
        try (
            var statement = DAOUtils.prepare(connection, Queries.ALL_FLIGHT_PURPOSE);
            var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var codFlightPurpose = resultSet.getInt("CodTipoViaggio");
                if (cache.stream().anyMatch(fp -> fp.codFlightPurpose().equals(codFlightPurpose))) {
                    setPurpose.add(cache.stream()
                        .filter(fp -> fp.codFlightPurpose().equals(codFlightPurpose))
                        .findFirst().get()
                    );
                } else {
                    var name = resultSet.getString("Nome");
                    var flightPurpose = new FlightPurposeImpl(codFlightPurpose, name);
                    cache.add(flightPurpose);
                    setPurpose.add(flightPurpose);
                }
            }
            return setPurpose;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}
