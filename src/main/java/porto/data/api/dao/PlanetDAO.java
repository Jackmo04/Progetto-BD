package porto.data.api.dao;

import java.sql.Connection;
import java.util.Optional;

import porto.data.api.Planet;
import porto.data.utils.DAOException;

public interface PlanetDAO {


    Optional<Planet> getFromCodPlanet(Connection connection, String codPlanet) throws DAOException;
}