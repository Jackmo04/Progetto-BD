package porto.data.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import porto.data.queries.QueryAction;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

/**
 * Implementation of the RequestDAO interface.
 * Provides methods to interact with requests in the database.
 */
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
        flightPurpose = new FlightPurposeDAOImpl(connection).getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Request> getRequestByCodRequest(Integer codRequest) throws DAOException {
        try (
                var statement = DAOUtils.prepare(connection, Queries.REQUEST_FROM_COD, codRequest);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                Integer codRequestDB = resultSet.getInt("CodRichiesta");
                RequestType type = RequestType.fromString(resultSet.getString("EntrataUscita"));
                Timestamp dateTime = resultSet.getTimestamp("DataOra");
                String description = resultSet.getString("Descrizione");
                double totalPrice = resultSet.getDouble("CostoTotale");
                RequestState state = RequestState.fromString(resultSet.getString("Esito"));
                Optional<Timestamp> dateTimeManaged = Optional.ofNullable(resultSet.getTimestamp("DataEsito"));
                Starship starship = new StarshipDAOImpl(connection).fromPlate(resultSet.getString("TargaAstronave"))
                        .get();
                Integer codTipoViaggio = resultSet.getInt("Scopo");
                FlightPurpose purpose = flightPurpose.stream()
                        .filter(fp -> fp.codFlightPurpose().equals(codTipoViaggio))
                        .findFirst().get();
                Planet departurePlanet = new PlanetDAOImpl(connection)
                        .getFromCodPlanet(resultSet.getString("PianetaProvenienza")).get();
                Planet destinationPlanet = new PlanetDAOImpl(connection)
                        .getFromCodPlanet(resultSet.getString("PianetaDestinazione")).get();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExitRequest(String description, Starship starship, String scope, Planet destinationPlanet)
            throws DAOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addExitRequest'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntryRequest(String description, Starship starship, String scope, Planet originPlanet)
            throws DAOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addEntryRequest'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Request> getLastRequest(String plate) throws DAOException {
                try (
                var statement = DAOUtils.prepare(connection, QueryAction.S4_LAST_REQUEST, plate);
                var resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                return getRequestByCodRequest(resultSet.getInt("CodRichiesta"));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Request> requestHistory(String plate) throws DAOException {
        var requests = new ArrayList<Request>();               
        try (
                var statement = DAOUtils.prepare(connection, QueryAction.C6_REQUEST_HISTORY, plate);
                var resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                requests.add(getRequestByCodRequest(resultSet.getInt("CodRichiesta")).get());
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return requests;
    }

}
