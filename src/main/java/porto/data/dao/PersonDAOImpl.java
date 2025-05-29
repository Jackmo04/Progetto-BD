package porto.data.dao;

import java.sql.Connection;

import porto.data.PersonImpl;
import porto.data.api.Person;
import porto.data.api.dao.PersonDAO;
import porto.data.utils.DAOException;

public class PersonDAOImpl implements PersonDAO{

    @Override
    public Person getFromCUI(Connection connection, String CUIPerson) throws DAOException {
        // TODO Placholder implementation
        return new PersonImpl(
            CUIPerson,
            "Pippo",
            "P.pluto"
        );
    }

}
