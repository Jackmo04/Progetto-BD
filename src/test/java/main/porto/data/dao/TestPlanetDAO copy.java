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
import porto.data.PlanetImpl;
import porto.data.ShipModelImpl;
import porto.data.StarshipImpl;
import porto.data.dao.PlanetDAOImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOUtils;

class TestPlanetDAO {

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
    public void fromCodPlanet() {
        var actual = new PlanetDAOImpl().getFromCodPlanet(connection, "DTHSTR0");
        var expected = Set.of(
            new PlanetImpl("DTHSTR0" , "Morte Nera" ),
            new PlanetImpl("CORU001" , "Coruscant" )
            
        );
        assertEquals(expected, actual);
    }

}
