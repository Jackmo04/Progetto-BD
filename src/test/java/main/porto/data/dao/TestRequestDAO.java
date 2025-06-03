package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import porto.data.FlightPurposeImpl;
import porto.data.PayloadImpl;
import porto.data.RequestImpl;
import porto.data.api.RequestState;
import porto.data.api.RequestType;
import porto.data.dao.PayloadTypeDAOImpl;
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

    @BeforeEach
    public void beforeEach() throws SQLException {
        savepoint = connection.setSavepoint();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        if (connection != null && savepoint != null) {
            connection.rollback(savepoint);
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
        var expected = requestDAO.getRequestByCodRequest(3);
        assertEquals(expected, actual);

        var actual1 = requestDAO.getLastRequest("MFALC001");
        var expected1 = requestDAO.getRequestByCodRequest(1);
        assertEquals(expected1, actual1);
    }

    @Test
    public void requestHistory() {
        var requestDAO = new RequestDAOImpl(connection);
        var actual = requestDAO.requestHistory("CR900004");
        var expected = List.of(
            requestDAO.getRequestByCodRequest(2).get(),
            requestDAO.getRequestByCodRequest(3).get()
        );
        assertEquals(expected, actual);
    }

    @Test
    public void pendingRequest() {
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
        final int COD_REQ = 5;
        requestDAO.acceptEnterRequest(COD_REQ, "PLPSHV201204N");
        
        assertEquals(RequestState.APPROVED, requestDAO.state(COD_REQ).get());
        assertEquals("PLPSHV201204N", requestDAO.managedBy(COD_REQ).get().CUI());
    }

    @Test
    public void rejectRequest() {
        var requestDAO = new RequestDAOImpl(connection);
        final int COD_REQ = 5;
        requestDAO.rejectRequest(COD_REQ, "PLPSHV201204N");
        
        assertEquals(RequestState.REJECTED, requestDAO.state(COD_REQ).get());
        assertEquals("PLPSHV201204N", requestDAO.managedBy(COD_REQ).get().CUI());
    }

    @Test
    public void addEntryRequestNoPayloads() {
        var requestDAO = new RequestDAOImpl(connection);
        var starshipDAO = new StarshipDAOImpl(connection);
        var planetDAO = new PlanetDAOImpl(connection);

        // Without payloads
        requestDAO.addEntryRequest(
            "Richiesta di ingresso senza carichi",
            new FlightPurposeImpl(5, "Esplorazione"),
            starshipDAO.fromPlate("MFALC001").get(),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            Set.of()
        );
        
        var actual = requestDAO.getLastRequest("MFALC001").get();
        var expected = new RequestImpl(
            0,
            RequestType.ENTRY,
            actual.dateTime(),
            "Richiesta di ingresso senza carichi",
            180.0,
            starshipDAO.fromPlate("MFALC001").get(),
            new FlightPurposeImpl(5, "Esplorazione"),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            planetDAO.getFromCodPlanet("DTHSTR0").get()
        );
        // Cannot compare code() and dateTime() as they are generated at runtime
        assertEquals(expected.type(), actual.type());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.totalPrice(), actual.totalPrice());
        assertEquals(expected.starship(), actual.starship());
        assertEquals(expected.purpose(), actual.purpose());
        assertEquals(expected.departurePlanet(), actual.departurePlanet());
        assertEquals(expected.destinationPlanet(), actual.destinationPlanet());
    }

    @Test
    public void addEntryRequestWithPayloads() {
        var requestDAO = new RequestDAOImpl(connection);
        var starshipDAO = new StarshipDAOImpl(connection);
        var planetDAO = new PlanetDAOImpl(connection);
        var payloadTypeDAO = new PayloadTypeDAOImpl(connection);
        var payloadType = payloadTypeDAO.getAll().stream().findFirst().orElseThrow();
        final int payloadQuantity = 10;

        // With payloads
        requestDAO.addEntryRequest(
            "Richiesta di ingresso con carichi",
            new FlightPurposeImpl(1, "Trasporto merci"),
            starshipDAO.fromPlate("MFALC001").get(),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            Set.of(
                new PayloadImpl(payloadType, payloadQuantity)                
            )
        );
        
        var actual = requestDAO.getLastRequest("MFALC001").get();
        var expected = new RequestImpl(
            0,
            RequestType.ENTRY,
            actual.dateTime(),
            "Richiesta di ingresso con carichi",
            180.0 + payloadType.unitPrice() * payloadQuantity,
            starshipDAO.fromPlate("MFALC001").get(),
            new FlightPurposeImpl(1, "Trasporto merci"),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            planetDAO.getFromCodPlanet("DTHSTR0").get()
        );
        // Cannot compare code() and dateTime() as they are generated at runtime
        assertEquals(expected.type(), actual.type());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.totalPrice(), actual.totalPrice());
        assertEquals(expected.starship(), actual.starship());
        assertEquals(expected.purpose(), actual.purpose());
        assertEquals(expected.departurePlanet(), actual.departurePlanet());
        assertEquals(expected.destinationPlanet(), actual.destinationPlanet());
    }

    @Test
    public void addExitRequest() {
        var requestDAO = new RequestDAOImpl(connection);
        var starshipDAO = new StarshipDAOImpl(connection);
        var planetDAO = new PlanetDAOImpl(connection);

        // Without payloads
        requestDAO.addExitRequest(
            "Richiesta di uscita senza carichi",
            new FlightPurposeImpl(5, "Esplorazione"),
            starshipDAO.fromPlate("MFALC001").get(),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            Set.of()
        );
        
        var actual = requestDAO.getLastRequest("MFALC001").get();
        var expected = new RequestImpl(
            0,
            RequestType.EXIT,
            actual.dateTime(),
            "Richiesta di uscita senza carichi",
            180.0,
            starshipDAO.fromPlate("MFALC001").get(),
            new FlightPurposeImpl(5, "Esplorazione"),
            planetDAO.getFromCodPlanet("DTHSTR0").get(),
            planetDAO.getFromCodPlanet("HOTH003").get()
        );
        // Cannot compare code() and dateTime() as they are generated at runtime
        assertEquals(expected.type(), actual.type());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.totalPrice(), actual.totalPrice());
        assertEquals(expected.starship(), actual.starship());
        assertEquals(expected.purpose(), actual.purpose());
        assertEquals(expected.departurePlanet(), actual.departurePlanet());
        assertEquals(expected.destinationPlanet(), actual.destinationPlanet());
    }

    @Test
    public void addExitRequestWithPayloads() {
        var requestDAO = new RequestDAOImpl(connection);
        var starshipDAO = new StarshipDAOImpl(connection);
        var planetDAO = new PlanetDAOImpl(connection);
        var payloadTypeDAO = new PayloadTypeDAOImpl(connection);
        var payloadType = payloadTypeDAO.getAll().stream().findFirst().orElseThrow();
        final int payloadQuantity = 10;

        // With payloads
        requestDAO.addExitRequest(
            "Richiesta di uscita con carichi",
            new FlightPurposeImpl(1, "Trasporto merci"),
            starshipDAO.fromPlate("MFALC001").get(),
            planetDAO.getFromCodPlanet("HOTH003").get(),
            Set.of(
                new PayloadImpl(payloadType, payloadQuantity)                
            )
        );
        
        var actual = requestDAO.getLastRequest("MFALC001").get();
        var expected = new RequestImpl(
            0,
            RequestType.EXIT,
            actual.dateTime(),
            "Richiesta di uscita con carichi",
            180.0 + payloadType.unitPrice() * payloadQuantity,
            starshipDAO.fromPlate("MFALC001").get(),
            new FlightPurposeImpl(1, "Trasporto merci"),
            planetDAO.getFromCodPlanet("DTHSTR0").get(),
            planetDAO.getFromCodPlanet("HOTH003").get()
        );
        // Cannot compare code() and dateTime() as they are generated at runtime
        assertEquals(expected.type(), actual.type());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.totalPrice(), actual.totalPrice());
        assertEquals(expected.starship(), actual.starship());
        assertEquals(expected.purpose(), actual.purpose());
        assertEquals(expected.departurePlanet(), actual.departurePlanet());
        assertEquals(expected.destinationPlanet(), actual.destinationPlanet());
    }

}
