package porto.data.dao;

import java.sql.Connection;

import porto.data.PersonImpl;
import porto.data.PlanetImpl;
import porto.data.api.Person;
import porto.data.api.dao.PersonDAO;
import porto.data.api.dao.PlanetDAO;
import porto.data.utils.DAOException;

public class PlanetDAOImpl implements PlanetDAO{

    @Override
    public Person getFromCodPlanet(Connection connection, String CUIPerson) throws DAOException {
        // TODO Placholder implementation
        return new PlanetImpl(
            "DTHSTR0",
            "Morte Nera"
        );
    }

}
