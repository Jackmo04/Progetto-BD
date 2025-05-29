package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import porto.data.PlanetImpl;
import porto.data.dao.PlanetDAOImpl;
import porto.data.utils.DAOUtils;


class TestPlanetDAO {

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
    public void fromCodPlanet() {
        var actual = Set.of(new PlanetDAOImpl().getFromCodPlanet(connection, "DTHSTR0"),
                new PlanetDAOImpl().getFromCodPlanet(connection, "CORU001"));
        var expected = Set.of(
                Optional.of(new PlanetImpl("DTHSTR0", "Morte Nera")),
                Optional.of(new PlanetImpl("CORU001", "Coruscant"))
        );
        assertEquals(expected, actual);
    }

}
