package porto.data.dao;

import java.sql.Connection;

import porto.data.PersonImpl;
import porto.data.PlanetImpl;
import porto.data.api.Person;
import porto.data.api.dao.PersonDAO;
import porto.data.utils.DAOException;

public class PersonDAOImpl implements PersonDAO{

    @Override
    public Person getFromCUI(Connection connection, String CUIPerson) throws DAOException {
        // TODO Placholder implementation
        return  new PersonImpl("STRMTR0000001", "Trooper1", 
        "pippo", "Stormtrooper", 
        "00001", "Clone", 
        "2000-01-01", false, "Imperiale", 
        "Astronauta", null,
        new PlanetImpl("DTHSTR0" ,"Morte Nera" ));
    }

}
