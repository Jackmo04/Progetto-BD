package main.porto.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import porto.data.api.Ideology;
import porto.data.api.Role;
import porto.data.dao.PersonDAOImpl;
import porto.data.utils.DAOUtils;

class TestPersonDAO {

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
    public void fromCUI() {
        var personDAO = new PersonDAOImpl(connection);
        var actual = Set.of(personDAO.getFromCUI("SKWLKE510925T").get().CUI(),
                personDAO.getFromCUI("CHWBCC000101K").get().CUI());
        var expected = Set.of("SKWLKE510925T", "CHWBCC000101K");
        assertEquals(expected, actual);
    }

    @Test
    public void addPerson() {
        var personDAO = new PersonDAOImpl(connection);
        personDAO.addPerson("STRMTR0000004", "Trooper4", "password", "Stormtrooper",
                "00004", "Clone", "2000-01-01", Ideology.IMPERIAL,
                Role.CREW_MEMBER, "DTHSTR0");

        var actual = personDAO.getFromCUI("STRMTR0000004").get().CUI();
        var expected = "STRMTR0000004";
        assertEquals(expected, actual);
    }

    @Test
    public void userLogin() {
        var personDAO = new PersonDAOImpl(connection);
        final String cui = "SKWLKE510925T";
        var actual = personDAO.loginAndGetUser(cui, "12345");
        var expected = personDAO.getFromCUI(cui);
        assertEquals(expected, actual);

        var actual2 = personDAO.loginAndGetUser("SKWLKE510925T", "wrongPassword");
        var expected2 = Optional.empty();
        assertEquals(expected2, actual2);
    }

    @Test
    public void arrestPerson() {
        var personDAO = new PersonDAOImpl(connection);
        personDAO.arrestPerson("STRMTR0000001");

        var actual = personDAO.getFromCUI("STRMTR0000001").get().cell().get().numCell();
        assertNotNull(actual);
    }

    @Test
    public void getEquipeOfStarship() {
        var personDao = new PersonDAOImpl(connection);
        var actual = personDao.getEquipeOfStarship("MFALC001");
        var expected = List.of(personDao.getFromCUI("CHWBCC000101K").get(),
                personDao.getFromCUI("SKWLKE510925T").get());
        assertEquals(expected, actual);
    }

}
