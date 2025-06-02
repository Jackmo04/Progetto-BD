package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import porto.data.dao.PlanetDAOImpl;
import porto.data.dao.RequestDAOImpl;
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
        var actual = requestDAO.getLastRequest("MFALC001");
        var expected = requestDAO.getRequestByCodRequest(1);
        assertEquals(expected, actual);
    }

}
