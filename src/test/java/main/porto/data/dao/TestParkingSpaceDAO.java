package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import porto.data.ParkingAreaImpl;
import porto.data.ParkingSpaceImpl;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.dao.ParkingSpaceDAOImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOUtils;

class TestParkingSpaceDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestParkingSpaceDAO.class);
    private static Connection connection;
    private static Savepoint savepoint;
    private static ParkingSpaceDAO DAO;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        connection.setAutoCommit(false);
        DAO = new ParkingSpaceDAOImpl(connection);
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
        LOGGER.info("Starting a new test, creating a new savepoint...");
        savepoint = connection.setSavepoint();
        LOGGER.info("Resetting DAO cache...");
        DAO.clearCache();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        if (savepoint != null) {
            LOGGER.info("Rolling back to the previous savepoint...");
            connection.rollback(savepoint);
        }
    }

    @Test
    public void of() {
        final int AREA_CODE = 4;
        final String AREA_NAME = "Rifornimento";
        final int PLACE_NUMBER = 1;
        LOGGER.info("Testing ParkingSpaceDAO.of with area code: {}, place number: {}", AREA_CODE, PLACE_NUMBER);

        var actual = DAO.of(AREA_CODE, PLACE_NUMBER);
        var expected = Optional.of(new ParkingSpaceImpl(
            new ParkingAreaImpl(AREA_CODE, AREA_NAME),
            PLACE_NUMBER
        ));
        assertEquals(expected, actual);

        // Test with non-existent area code and place number
        final int NON_EXISTENT_AREA_CODE = 999;
        final int NON_EXISTENT_PLACE_NUMBER = 999;
        LOGGER.info("Testing ParkingSpaceDAO.of with non-existent area code: {}, place number: {}", 
            NON_EXISTENT_AREA_CODE, NON_EXISTENT_PLACE_NUMBER);
        
        actual = DAO.of(NON_EXISTENT_AREA_CODE, NON_EXISTENT_PLACE_NUMBER);
        expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void ofStarship() {
        // Test with a docked starship
        final String PLATE_NUMBER_MF = "MFALC001";
        final int AREA_CODE_MF = 2;
        final int PLACE_NUMBER_MF = 1;
        LOGGER.info("Testing ParkingSpaceDAO.ofStarship with plate number: {}", PLATE_NUMBER_MF);

        var actual = DAO.ofStarship(PLATE_NUMBER_MF);
        var expected = Optional.of(new ParkingSpaceImpl(
            new ParkingAreaImpl(AREA_CODE_MF, "Beta"),
            PLACE_NUMBER_MF
        ));
        assertEquals(expected, actual);

        // Test with an undocked starship
        final String PLATE_NUMBER_TF = "TIEF0005";
        LOGGER.info("Testing ParkingSpaceDAO.ofStarship with plate number: {}", PLATE_NUMBER_TF);

        actual = DAO.ofStarship(PLATE_NUMBER_TF);
        expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfPeopleOnStation() {
        final int NUMBER_OF_PEOPLE = 7;
        LOGGER.info("Testing ParkingSpaceDAO.getNumberOfPeopleOnStation");

        int actual = DAO.getNumberOfPeopleOnStation();
        int expected = NUMBER_OF_PEOPLE;
        assertEquals(expected, actual);

        // Remove crew members from starships
        final var starshipDAO = new StarshipDAOImpl(connection);
        starshipDAO.removeCrewMember("MFALC001", "CHWBCC000101K");
        starshipDAO.removeCrewMember("CR900004", "STRMTR0000003");
        LOGGER.info("Removed crew members from starships, now testing again.");

        actual = DAO.getNumberOfPeopleOnStation();
        expected = NUMBER_OF_PEOPLE - 2; // 2 crew members removed
        assertEquals(expected, actual);
    }

    @Test
    public void getAllFree() {
        LOGGER.info("Testing ParkingSpaceDAO.getAllFree");

        var actual = DAO.getAllFree().size();
        var expected = 13;
        assertEquals(expected, actual);
    }
}
