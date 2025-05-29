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

import porto.data.ParkingAreaImpl;
import porto.data.ParkingSpaceImpl;
import porto.data.PersonImplTemp;
import porto.data.ShipModelImpl;
import porto.data.StarshipImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOUtils;

class TestStarshipDAO {

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

        var actual = new StarshipDAOImpl().ofPerson(connection, CUI);
        var expected = Set.of(
            new StarshipImpl(
                "CR900004",
                "Tantive IV",
                Optional.of(new ParkingSpaceImpl(new ParkingAreaImpl(4, "Rifornimento"), 1)),
                new ShipModelImpl("CR9005", "Corvette CR90", 200, 350.0),
                new PersonImplTemp("MULDRT600322D", "Pippo", "P.pluto") // TODO: replace with real person data
            ),
            new StarshipImpl(
                "STARD003",
                "Executor",
                Optional.of(new ParkingSpaceImpl(new ParkingAreaImpl(3, "Officina"), 5)),
                new ShipModelImpl("SD0003", "Star Destroyer", 1000, 1500.0),
                new PersonImplTemp("MULDRT600322D", "Pippo", "P.pluto") // TODO: replace with real person data
            )
        );
        assertEquals(expected, actual);
    }

}
