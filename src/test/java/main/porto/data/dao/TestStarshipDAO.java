package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
import porto.data.api.PersonRole;
import porto.data.api.Starship;
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
        "Darth",
        "Maul",
        "Zabrak",
        "1960-03-22",
        false,
        Ideology.IMPERIAL,
        PersonRole.CAPTAIN,
        Optional.empty(),
        new PlanetImpl("DANT010", "Dantooine")
    );

    private static final Person CREW_STRMTR = new PersonImpl(
        "STRMTR0000002",
        "Trooper2",
        "Stormtrooper",
        "00002",
        "Clone",
        "2000-01-01",
        false,
        Ideology.IMPERIAL,
        PersonRole.CREW_MEMBER,
        Optional.empty(),
        new PlanetImpl("DTHSTR0", "Morte Nera")
    );

    private static final Starship SHIP_XWING = new StarshipImpl(
        "XWING002",
        "Red Five",
        new ShipModelImpl("XW0001", "X-Wing", 50, 100.0),
        CAPITAN_MULDRT
    );

    private static final Starship SHIP_TANTIVE = new StarshipImpl(
        "CR900004",
        "Tantive IV",
        new ShipModelImpl("CR9005", "Corvette CR90", 200, 350.0),
        CAPITAN_MULDRT
    );

    private static final Starship SHIP_EXECUTOR = new StarshipImpl(
        "STARD003",
        "Executor",
        new ShipModelImpl("SD0003", "Star Destroyer", 1000, 1500.0),
        CAPITAN_MULDRT
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

    @AfterEach
    public void afterEach() throws SQLException {
        LOGGER.info("Rolling back to savepoint after each test.");
        if (savepoint != null) {
            connection.rollback(savepoint);
        }
        savepoint = connection.setSavepoint();
    }

    @Test
    public void fromPlate() {
        final String PLATE = "CR900004";
        LOGGER.info("Testing StarshipDAO.fromPlate with plate: {}", PLATE);

        var actual = DAO.fromPlate(PLATE);
        var expected = Optional.of(SHIP_TANTIVE);
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
        var expected = Set.of(SHIP_TANTIVE, SHIP_EXECUTOR);
        assertEquals(expected, actual);

        // Test with non-existent CUI
        final String NON_EXISTENT_CUI = "NONEXISTENT";
        LOGGER.info("Testing StarshipDAO.ofPerson with non-existent CUI: {}", NON_EXISTENT_CUI);

        actual = DAO.ofPerson(NON_EXISTENT_CUI);
        expected = Set.of();
        assertEquals(expected, actual);
    }

    @Test
    public void addStarship() {
        final String PLATE = "ADDED001";
        LOGGER.info("Testing StarshipDAO.addStarship with plate: {}", PLATE);

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

    @Test
    public void addCrewMember() {
        final String PLATE = "STARD003";
        LOGGER.info("Testing StarshipDAO.addCrewMember with plate: {}, member CUI: {}", PLATE, CREW_STRMTR.CUI());

        var actual = DAO.ofPerson(CREW_STRMTR.CUI());
        var expected = Set.of(SHIP_XWING);
        assertEquals(expected, actual);

        DAO.addCrewMember(PLATE, CREW_STRMTR);
        
        actual = DAO.ofPerson(CREW_STRMTR.CUI());
        expected = Set.of(SHIP_XWING, SHIP_EXECUTOR);
        assertEquals(expected, actual);

        // Test adding a crew member to a non-existent ship
        final String NON_EXISTENT_PLATE = "NONEXISTENT";
        LOGGER.info("Testing StarshipDAO.addCrewMember with non-existent plate: {}", NON_EXISTENT_PLATE);
        assertThrows(
            DAOException.class,
            () -> DAO.addCrewMember(NON_EXISTENT_PLATE, CREW_STRMTR),
            "Expected DAOException when adding a crew member to a non-existent ship"
        );

        // Test adding a person who doesn't have the CREW_MEMBER role
        LOGGER.info("Testing StarshipDAO.addCrewMember with a person who is not a crew member: {}", CAPITAN_MULDRT.CUI());
        assertThrows(
            IllegalArgumentException.class, 
            () -> DAO.addCrewMember(PLATE, CAPITAN_MULDRT),
            "Expected IllegalArgumentException when adding a person who is not a crew member"
        );

    }

    @Test
    public void removeCrewMember() {
        LOGGER.info(
            "Testing StarshipDAO.removeCrewMember with plate: {}, member CUI: {}", 
            SHIP_XWING.plateNumber(), 
            CREW_STRMTR.CUI()
        );

        var actual = DAO.ofPerson(CREW_STRMTR.CUI());
        var expected = Set.of(SHIP_XWING);
        assertEquals(expected, actual);

        DAO.removeCrewMember(SHIP_XWING.plateNumber(), CREW_STRMTR);
        
        actual = DAO.ofPerson(CREW_STRMTR.CUI());
        expected = Set.of();
        assertEquals(expected, actual);

    }

    @Test
    public void topTransporting() {
        LOGGER.info("Testing StarshipDAO.get50TransportedMost");

        var actual = DAO.get50TransportedMost().entrySet().stream()
            .map(entry -> Map.entry(entry.getKey().plateNumber(), entry.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<String, Integer> expected = Map.of(
            "CR900004", 10,
            "MFALC001", 3,
            "STARD003", 3
        );
        assertEquals(expected, actual);

    }

}
