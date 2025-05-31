package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import porto.data.ShipModelImpl;
import porto.data.api.dao.ShipModelDAO;
import porto.data.dao.ShipModelDAOImpl;
import porto.data.utils.DAOUtils;

class TestShipModelDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestShipModelDAO.class);
    private static Connection connection;
    private static Savepoint savepoint;
    private static ShipModelDAO DAO;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();
        DAO = new ShipModelDAOImpl(connection);
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
    public void fromCode() {
        final String CODE = "MF0002";
        LOGGER.info("Testing ShipModelDAO.fromCode with code: {}", CODE);

        var actual = DAO.getFromCode(CODE);
        var expected = Optional.of(new ShipModelImpl(
            "MF0002",
            "Millennium Falcon",
            100,
            180.0
        ));

        assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        LOGGER.info("Testing ShipModelDAO.getAll");

        var actual = DAO.getAll();
        var expected = Set.of(
            new ShipModelImpl("CR9005", "Corvette CR90", 200, 350.0),
            new ShipModelImpl("GR7506", "Trasporto GR-75", 200, 350.0),
            new ShipModelImpl("MF0002", "Millennium Falcon", 100, 180.0),
            new ShipModelImpl("SD0003", "Star Destroyer", 1000, 1500.0),
            new ShipModelImpl("XW0001", "X-Wing", 50, 100.0),
            new ShipModelImpl("TIF004", "TIE Fighter", 50, 100.0)
        );

        assertEquals(expected, actual);
    }

}
