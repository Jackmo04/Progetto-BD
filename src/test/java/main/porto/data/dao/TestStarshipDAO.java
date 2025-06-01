package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import porto.data.PersonImpl;
import porto.data.PlanetImpl;
import porto.data.ShipModelImpl;
import porto.data.StarshipImpl;
import porto.data.api.Person;
import porto.data.api.Ideology;
import porto.data.api.Role;
import porto.data.api.dao.StarshipDAO;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOException;
import porto.data.utils.DAOUtils;

class TestStarshipDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestStarshipDAO.class);
    private static Connection connection;
    private static Savepoint savepoint;
    private static StarshipDAO DAO;

    private static final Person CAPITAN_MULDRT = new PersonImpl(
        "MULDRT600322D",
        "D.Maul",
        "",
        "Darth",
        "Maul",
        "Zabrak",
        "1960-03-22",
        false,
        Ideology.IMPERIAL,
        Role.CAPTAIN,
        Optional.empty(),
        new PlanetImpl("DANT010", "Dantooine")
    );

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("PortoMorteNera", "root", "");
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();
        DAO = new StarshipDAOImpl(connection);
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
    public void fromPlate() {
        final String PLATE = "CR900004";
        LOGGER.info("Testing StarshipDAO.fromPlate with plate: {}", PLATE);

        var actual = DAO.fromPlate(PLATE);
        var expected = Optional.of(new StarshipImpl(
            "CR900004",
            "Tantive IV",
            new ShipModelImpl("CR9005", "Corvette CR90", 200, 350.0),
            CAPITAN_MULDRT
        ));
        assertEquals(expected, actual);

        // Test with non-existent plate
        final String NON_EXISTENT_PLATE = "NONEXISTENT";
        LOGGER.info("Testing StarshipDAO.fromPlate with non-existent plate: {}", NON_EXISTENT_PLATE);

        actual = DAO.fromPlate(NON_EXISTENT_PLATE);
        expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void fromPersonCUI() {
        final String CUI = "STRMTR0000001";
        LOGGER.info("Testing StarshipDAO.ofPerson with CUI: {}", CUI);

        var actual = DAO.ofPerson(CUI);
        var expected = Set.of(
            new StarshipImpl(
                "CR900004",
                "Tantive IV",
                new ShipModelImpl("CR9005", "Corvette CR90", 200, 350.0),
                CAPITAN_MULDRT),
            new StarshipImpl(
                "STARD003",
                "Executor",
                new ShipModelImpl("SD0003", "Star Destroyer", 1000, 1500.0),
                CAPITAN_MULDRT));
        assertEquals(expected, actual);

        // Test with non-existent CUI
        final String NON_EXISTENT_CUI = "NONEXISTENT";
        LOGGER.info("Testing StarshipDAO.ofPerson with non-existent CUI: {}", NON_EXISTENT_CUI);

        actual = DAO.ofPerson(NON_EXISTENT_CUI);
        expected = Set.of();
        assertEquals(expected, actual);
    }

    @Test
    public void add() {
        final String PLATE = "ADDED001";
        LOGGER.info("Testing StarshipDAO.add with plate: {}", PLATE);

        var ship = new StarshipImpl(
            PLATE,
            "New Ship",
            new ShipModelImpl("GR7506", "Trasporto GR-75", 200, 350.0),
            CAPITAN_MULDRT
        );
        DAO.addStarship(ship);
        
        var actual = DAO.fromPlate(PLATE);
        var expected = Optional.of(ship);
        assertEquals(expected, actual);

        // Test adding a ship with an existing plate
        LOGGER.info("Testing StarshipDAO.add with existing plate: {}", PLATE);

        var duplicateShip = new StarshipImpl(
            PLATE,
            "Duplicate Ship",
            new ShipModelImpl("GR7506", "Trasporto GR-75", 200, 350.0),
            CAPITAN_MULDRT
        );
        
        assertThrows(
            DAOException.class,
            () -> DAO.addStarship(duplicateShip),
            "Expected DAOException when adding a ship with an existing plate"
        );
    }

}
