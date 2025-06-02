package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import porto.data.FlightPurposeImpl;
import porto.data.RequestImpl;
import porto.data.api.RequestState;
import porto.data.api.RequestType;
import porto.data.dao.PlanetDAOImpl;
import porto.data.dao.RequestDAOImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOUtils;

class TestRequestDAO {

    private static Connection connection;
    private static Savepoint savepoint;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        if (connection != null) {
            if (savepoint != null) {
                connection.rollback(savepoint);
            }
            connection.close();
        }
    }

    @Test
    public void fromCodRequest() {
        var actual = new RequestDAOImpl(connection).getRequestByCodRequest(1).get().departurePlanet();
        var expected = new PlanetDAOImpl(connection).getFromCodPlanet("TATO002").get();
        assertEquals(expected, actual);
    }

    @Test
    public void lastRequest() {
        var requestDAO = new RequestDAOImpl(connection);
        var actual = requestDAO.getLastRequest("CR900004");
        var expected = requestDAO.getRequestByCodRequest(2);
        assertEquals(expected, actual);

        var actual1 = requestDAO.getLastRequest("MFALC001");
        var expected1 = requestDAO.getRequestByCodRequest(1);
        assertEquals(expected1, actual1);
    }

    @Test
    public void requestHistory() {
        var requestDAO = new RequestDAOImpl(connection);
        var actual = requestDAO.requestHistory("CR900004");
        var expected = List.of(requestDAO.getRequestByCodRequest(2).get(), requestDAO.getRequestByCodRequest(3).get());
        assertEquals(expected, actual);
    }

    @Test
    public void pendingRquest() {
        var requestDAO = new RequestDAOImpl(connection);
        var actual = requestDAO.pendingRequests();
        var expected = List.of(
            requestDAO.getRequestByCodRequest(4).get(),
            requestDAO.getRequestByCodRequest(6).get());
        assertEquals(expected, actual);
    }

    @Test
    public void acceptRequest() {
        var requestDAO = new RequestDAOImpl(connection);
        requestDAO.acceptEnterRequest(6, "PLPSHV201204N");
        var expected = requestDAO.getRequestByCodRequest(6).get().state();
        assertEquals(expected, RequestState.APPROVED);
    }

    @Test
    public void rejectRequest() {
        var requestDAO = new RequestDAOImpl(connection);
        requestDAO.acceptEnterRequest(6, "PLPSHV201204N");
        var expected = requestDAO.getRequestByCodRequest(6).get().state();
        assertEquals(expected, RequestState.REJECTED);
    }

    // TODO check totalPrice when changed fields @Jackmo04
    @Test
    public void addEntryRequest() {
        var requestDAO = new RequestDAOImpl(connection);
        var starshipDAO = new StarshipDAOImpl(connection);
        var planetDAO = new PlanetDAOImpl(connection);

        requestDAO.addEntryRequest(
            "Richiesta di ingresso per carico di armi",
            new FlightPurposeImpl(1, "Trasporto merci"),
            starshipDAO.fromPlate("MFALC001").get(),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            Set.of()
        );
        
        var actual = requestDAO.getLastRequest("MFALC001").get();
        var expected = new RequestImpl(
            6,
            RequestType.ENTRY,
            actual.dateTime(),
            "Richiesta di ingresso per carico di armi",
            0.0,
            RequestState.PENDING,
            Optional.empty(),
            starshipDAO.fromPlate("MFALC001").get(),
            new FlightPurposeImpl(1, "Trasporto merci"),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            planetDAO.getFromCodPlanet("DTHSTR0").get(),
            Optional.empty()
        );
        assertEquals(expected, actual);
    }

    @Test
    public void addExitRequest() {
        var requestDAO = new RequestDAOImpl(connection);
        var starshipDAO = new StarshipDAOImpl(connection);
        var planetDAO = new PlanetDAOImpl(connection);

        requestDAO.addExitRequest(
            "Richiesta di uscita per carico di armi",
            new FlightPurposeImpl(1, "Trasporto merci"),
            starshipDAO.fromPlate("MFALC001").get(),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            Set.of()
        );
        
        var actual = requestDAO.getLastRequest("MFALC001").get();
        var expected = new RequestImpl(
            7,
            RequestType.EXIT,
            actual.dateTime(),
            "Richiesta di uscita per carico di armi",
            0.0,
            RequestState.PENDING,
            Optional.empty(),
            starshipDAO.fromPlate("MFALC001").get(),
            new FlightPurposeImpl(1, "Trasporto merci"),
            planetDAO.getFromCodPlanet("DTHSTR0").get(),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            Optional.empty()
        );
        assertEquals(expected, actual);
    }

}
