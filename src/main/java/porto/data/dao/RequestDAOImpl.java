package porto.data.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

import porto.data.CellImpl;
import porto.data.RequestImpl;
import porto.data.api.FlightPurpose;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestState;
import porto.data.api.RequestType;
import porto.data.api.Starship;
import porto.data.api.dao.RequestDAO;
import porto.data.queries.Queries;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

public class RequestDAOImpl implements RequestDAO {

    private final Connection connection;
    private final Set<FlightPurpose> flightPurpose;

    /**
     * Constructor for RequestDAOImpl.
     * 
     * @param connection the database connection
     */
    public RequestDAOImpl(Connection connection) {
        this.connection = connection;
        flightPurpose = new FlightPurposeDAOImpl(connection).getSetFlightPurposeByCode();
    }

    @Override
    public Optional<Request> getRequestByCodRequest(Integer codRequest) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, Queries.REQUEST_FROM_COD, codRequest);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                Integer codRequestDB = resultSet.getInt("CodRichiesta");
                RequestType type = RequestType.fromString(resultSet.getString("TipoRichiesta"));
                Timestamp dateTime = resultSet.getTimestamp("DataOraRichiesta");
                String description = resultSet.getString("Descrizione");
                double totalPrice = resultSet.getDouble("CostoTotale");
                RequestState state = RequestState.fromString(resultSet.getString("Esito"));
                Optional<Timestamp> dateTimeManaged = Optional.ofNullable(resultSet.getTimestamp("DataOraGestione"));
                Starship starship = new StarshipDAOImpl(connection).fromPlate(resultSet.getString("TargaAstronave"))
                        .get();
                Integer codTipoViaggio = resultSet.getInt("CodTipoViaggio");
                FlightPurpose purpose = flightPurpose.stream()
                        .filter(fp -> fp.codFlightPurpose().equals(codTipoViaggio))
                        .findFirst().get();
                Planet departurePlanet = new PlanetDAOImpl(connection)
                        .getFromCodPlanet(resultSet.getString("CodPianetaPartenza")).get();
                Planet destinationPlanet = new PlanetDAOImpl(connection)
                        .getFromCodPlanet(resultSet.getString("CodPianetaDestinazione")).get();
                Optional<Person> managedBy = new PersonDAOImpl(connection).getFromCUI(resultSet.getString("GestitaDa"));

                var request = new RequestImpl(codRequestDB, type, dateTime, description, totalPrice, state,
                        dateTimeManaged, starship, purpose, departurePlanet, destinationPlanet, managedBy);
                return Optional.of(request);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void addExitRequest(String description, Starship starship, String scope, Planet destinationPlanet)
            throws DAOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addExitRequest'");
    }

    @Override
    public void addEntryRequest(String description, Starship starship, String scope, Planet originPlanet)
            throws DAOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addEntryRequest'");
    }

    @Override
    public String getDettaliedRequest(Integer codRequest) throws DAOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDettaliedRequest'");
    }

}
