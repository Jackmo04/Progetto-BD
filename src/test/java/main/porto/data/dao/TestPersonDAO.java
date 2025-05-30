package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import porto.data.dao.PersonDAOImpl;
import porto.data.utils.DAOUtils;


class TestPersonDAO {

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
        var actual = Set.of(new PersonDAOImpl(connection).getFromCUI("SKWLKE510925T").get().CUI(),
                new PersonDAOImpl(connection).getFromCUI("CHWBCC000101K").get().CUI());
        var expected = Set.of("SKWLKE510925T" ,"CHWBCC000101K" );
        assertEquals(expected, actual);
    }

}
