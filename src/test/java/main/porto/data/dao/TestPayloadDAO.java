package main.porto.data.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import porto.data.PayloadTypeImpl;
import porto.data.api.dao.PayloadDAO;
import porto.data.dao.PayloadDAOImpl;
import porto.data.utils.DAOUtils;

public class TestPayloadDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestPayloadDAO.class);
    private static Connection connection;
    private static Savepoint savepoint;
    private static PayloadDAO DAO;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();
        DAO = new PayloadDAOImpl(connection);
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
    public void add() {
        LOGGER.info("Testing PayloadDAO.add");

        var type = new PayloadTypeImpl(2, "Armi", "Armi imperiali", 1200.00);
        int quantity = 10;
        int codRequest = 1;

        DAO.add(type, quantity, codRequest);
    }
    
}
