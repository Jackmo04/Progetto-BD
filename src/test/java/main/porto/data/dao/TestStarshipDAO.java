package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import porto.data.ParkingAreaImpl;
import porto.data.ParkingSpaceImpl;
import porto.data.PersonImpl;
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
        var actual = new StarshipDAOImpl().ofPerson(connection, "STRMTR0000001");
        var expected = Set.of(
            new StarshipImpl(
                "CR900004",
                "Tantive IV",
                new ParkingSpaceImpl(new ParkingAreaImpl(4, "Rifornimento"), 1),
                new ShipModelImpl("CR9005", "Corvette CR90", 200, 350.0),
                new PersonImpl("STRMTR0000001", "Pippo", "Pluto")
            ),
            new StarshipImpl(
                "STARD003",
                "Executor",
                new ParkingSpaceImpl(new ParkingAreaImpl(3, "Officina"), 5),
                new ShipModelImpl("SD0003", "Star Destroyer", 1000, 1500.0),
                new PersonImpl("STRMTR0000001", "Pippo", "Pluto")
            )
        );
        assertEquals(expected, actual);
    }

}
