package porto.data.dao;

import java.sql.Connection;

import porto.data.api.Person;
import porto.data.api.dao.CellDAO;
import porto.data.utils.DAOException;

public class CellDAOImpl implements CellDAO{

    @Override
    public Person getFromNumCell(Connection connection, Integer numCell) throws DAOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFromNumCell'");
    }
    
}
