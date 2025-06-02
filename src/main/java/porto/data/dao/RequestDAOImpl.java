package porto.data.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import porto.data.RequestImpl;
import porto.data.api.FlightPurpose;
import porto.data.api.Payload;
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
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                var codRequestDB = resultSet.getInt("CodRichiesta");
                var type = RequestType.fromString(resultSet.getString("EntrataUscita"));
                var dateTime = resultSet.getTimestamp("DataOra");
                var description = resultSet.getString("Descrizione");
                var totalPrice = resultSet.getDouble("CostoTotale");
                var starship = new StarshipDAOImpl(connection).fromPlate(resultSet.getString("TargaAstronave"))
                        .get();
                var codTipoViaggio = resultSet.getInt("Scopo");
                var purpose = flightPurpose.stream()
                        .filter(fp -> fp.code() == codTipoViaggio)
                        .findFirst().get();
                var departurePlanet = new PlanetDAOImpl(connection)
                        .getFromCodPlanet(resultSet.getString("PianetaProvenienza")).get();
                var destinationPlanet = new PlanetDAOImpl(connection)
                        .getFromCodPlanet(resultSet.getString("PianetaDestinazione")).get();
                var request = new RequestImpl(codRequestDB, type, dateTime, description, totalPrice, 
                    starship, purpose, departurePlanet, destinationPlanet);
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
    public Request addExitRequest(
        String description, 
        FlightPurpose purpose, 
        Starship starship, 
        Planet destinationPlanet,
        Set<Payload> payloads
    ) throws DAOException {
        return this.addRequest(description, purpose, starship, destinationPlanet, payloads, Queries.INSERT_EXIT_REQUEST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Request addEntryRequest(
        String description,
        FlightPurpose purpose,
        Starship starship,
        Planet originPlanet,
        Set<Payload> payloads
    ) throws DAOException {
        return this.addRequest(description, purpose, starship, originPlanet, payloads, Queries.INSERT_ENTRY_REQUEST);
    }

    private Request addRequest(
        String description,
        FlightPurpose purpose,
        Starship starship,
        Planet planet,
        Set<Payload> payloads,
        String query
    ) throws DAOException {
        try (
            var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setObject(1, description);
            statement.setObject(2, starship.plateNumber());
            statement.setObject(3, purpose.code());
            statement.setObject(4, planet.codPlanet());
            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                var codRequest = resultSet.getInt(1);
                if (!payloads.isEmpty()) {
                    this.addPayloadsToRequest(payloads, codRequest);
                }
                return getRequestByCodRequest(codRequest).orElseThrow();
            } else {
                throw new DAOException("Failed to retrieve generated key for request.");
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private void addPayloadsToRequest(Set<Payload> payloads, int codRequest) throws DAOException {
        if (payloads.isEmpty()) {
            return;
        }
        var payloadDAO = new PayloadDAOImpl(connection);
        var totalPrice = payloads.stream()
            .mapToDouble(Payload::totalPrice)
            .sum();
        payloads.forEach(pl -> {
            payloadDAO.add(pl.type(), pl.quantity(), codRequest);
        });
        try (
            var statement = DAOUtils.prepare(connection, Queries.UPDATE_REQUEST_TOTAL_PRICE, totalPrice, codRequest);
        ) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Failed to update total price for request.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Request> getLastRequest(String plate) throws DAOException {
        try (
            var statement = DAOUtils.prepare(connection, QueryAction.S4_LAST_REQUEST, plate);
            var resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                return getRequestByCodRequest(resultSet.getInt("CodRichiesta"));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Request> pendingRequests() throws DAOException {
        var requests = new ArrayList<Request>();
        try (
                var statement = DAOUtils.prepare(connection, QueryAction.A1_PENDING_REQUEST);
                var resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                requests.add(getRequestByCodRequest(resultSet.getInt("CodRichiesta")).get());
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return requests;
    }

    @Override
    public void acceptEnterRequest(int codRequest, String CUIAdmin) throws DAOException {
        var request = getRequestByCodRequest(codRequest).get();
        var parking = new ParkingSpaceDAOImpl(connection).getAllFree().stream().toList().getFirst();

        try (
                var statement = DAOUtils.prepare(connection, QueryAction.A2_ACCEPT_REQUEST, CUIAdmin,
                        request.codRichiesta());
                var statement2 = DAOUtils.prepare(connection, QueryAction.A2_ASSIGN_PARKING,
                        parking.parkingArea().codArea(), parking.spaceNumber(), request.codRichiesta());) {
            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void rejectRequest(int codRequest, String CUIAdmin) throws DAOException {
        var request = getRequestByCodRequest(codRequest).get();
        try (
                var statement = DAOUtils.prepare(connection, QueryAction.A2_REJECT_REQUEST, CUIAdmin,
                        request.codRichiesta());) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<RequestState> state(int codRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'state'");
    }

    @Override
    public Optional<RequestState> state(Request request) {
        return this.state(request.codRichiesta());
    }

    @Override
    public Optional<Timestamp> dateTimeManaged(int codRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dateTimeManaged'");
    }

    @Override
    public Optional<Timestamp> dateTimeManaged(Request request) {
        return this.dateTimeManaged(request.codRichiesta());
    }

    @Override
    public Optional<Person> managedBy(int codRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'managedBy'");
    }

    @Override
    public Optional<Person> managedBy(Request request) {
        return this.managedBy(request.codRichiesta());
    }

}
