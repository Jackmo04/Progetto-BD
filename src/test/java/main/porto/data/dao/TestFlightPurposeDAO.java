package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import porto.data.dao.FlightPurposeDAOImpl;
import porto.data.utils.DAOUtils;

class TestFlightPurposeDAO {

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
    public void getAllFlightPurpose() {
        var actual = new FlightPurposeDAOImpl(connection).getSetFlightPurposeByCode().size();
        var expected = 5;
        assertEquals(expected, actual);
    }

}
