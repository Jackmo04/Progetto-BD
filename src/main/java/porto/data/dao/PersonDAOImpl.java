package porto.data.dao;

import java.sql.Connection;

import porto.data.api.Person;
import porto.data.api.dao.PersonDAO;
import porto.data.utils.DAOException;

public class PersonDAOImpl implements PersonDAO{

    @Override
    public Person getFromCUI(Connection connection, String CUIPerson) throws DAOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromCUI'");
    }

}
