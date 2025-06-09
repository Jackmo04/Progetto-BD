package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import porto.data.PayloadTypeImpl;
import porto.data.api.dao.PayloadTypeDAO;
import porto.data.dao.PayloadTypeDAOImpl;
import porto.data.utils.DAOUtils;

public class TestPayloadTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestPayloadTypeDAO.class);
    private static Connection connection;
    private static Savepoint savepoint;
    private static PayloadTypeDAO DAO;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();
        DAO = new PayloadTypeDAOImpl(connection);
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
    public void beforeEach() {
        LOGGER.info("Resetting DAO cache before each test.");
        DAO.clearCache();
    }

    @Test
    public void getAll() {
        LOGGER.info("Testing PayloadTypeDAO.getAll");

        var actual = DAO.getAll();
        var expected = Set.of(
            new PayloadTypeImpl(1, "Persone", "Prigionieri, turisti, ecc.", 500.00),
            new PayloadTypeImpl(2, "Armi", "Armi imperiali", 1200.00),
            new PayloadTypeImpl(3, "Dati", "Informazioni classificate", 2000.00),
            new PayloadTypeImpl(4, "Droidi", "Componenti per droidi", 150.00),
            new PayloadTypeImpl(5, "Alimentari", "Razioni standard", 50.00)
        );

        assertEquals(expected, actual);
    }
}
