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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import porto.data.ParkingAreaImpl;
import porto.data.ParkingSpaceImpl;
import porto.data.PersonImpl;
import porto.data.PlanetImpl;
import porto.data.ShipModelImpl;
import porto.data.StarshipImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOUtils;

class TestStarshipDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestStarshipDAO.class);
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
    public void fromPersonCUI() {
        final String CUI = "STRMTR0000001";
        LOGGER.info("Testing StarshipDAO.ofPerson with CUI: {}", CUI);

        var actual = new StarshipDAOImpl(connection).ofPerson(CUI);
        var expected = Set.of(
            new StarshipImpl(
                "CR900004",
                "Tantive IV",
                Optional.of(new ParkingSpaceImpl(new ParkingAreaImpl(4, "Rifornimento"), 1)),
                new ShipModelImpl("CR9005", "Corvette CR90", 200, 350.0),
                new PersonImpl("STRMTR0000001", "Trooper1", "pippo", "Stormtrooper", "00001", "Clone", "2000-01-01", false, "Imperiale", "Astronauta", null, new PlanetImpl("DTHSTR0" ,"Morte Nera" ))
            ),
            new StarshipImpl(
                "STARD003",
                "Executor",
                Optional.of(new ParkingSpaceImpl(new ParkingAreaImpl(3, "Officina"), 5)),
                new ShipModelImpl("SD0003", "Star Destroyer", 1000, 1500.0),
                 new PersonImpl("STRMTR0000001", "Trooper1", "pippo", "Stormtrooper", "00001", "Clone", "2000-01-01", false, "Imperiale", "Astronauta", null, new PlanetImpl("DTHSTR0" ,"Morte Nera" ))
            )
        );
        assertEquals(expected, actual);
    }

}
