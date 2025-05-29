package porto.data.api.dao;

import java.sql.Connection;

import porto.data.api.Person;
import porto.data.utils.DAOException;

public interface PlanetDAO {


    Person getFromCodPlanet(Connection connection, String codPlanet) throws DAOException;
}