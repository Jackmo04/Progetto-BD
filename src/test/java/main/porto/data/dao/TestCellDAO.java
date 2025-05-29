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

import porto.data.CellImpl;
import porto.data.dao.CellDAOImpl;
import porto.data.utils.DAOUtils;


class TestCellDAO {

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
    public void fromNumCell() {
        var actual = Set.of(new CellDAOImpl().getFromNumCell(connection, 1),
                new CellDAOImpl().getFromNumCell(connection, 2));
        var expected = Set.of(
                Optional.of(new CellImpl(1, 5)),
                Optional.of(new CellImpl(2, 3))
        );
        assertEquals(expected, actual);
    }

}
