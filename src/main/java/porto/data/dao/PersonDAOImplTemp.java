package porto.data.dao;

import java.sql.Connection;

import porto.data.api.Person;
import porto.data.api.dao.PersonDAO;
import porto.data.utils.DAOException;

public class PersonDAOImplTemp implements PersonDAO {

    private final Connection connection;

    /**
     * Constructor for PersonDAOImpl.
     * @param connection the database connection
     */
    public PersonDAOImplTemp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person getFromCUI(String CUIPerson) throws DAOException {
        // TODO Placholder implementation
        return new PersonImplTemp(
            CUIPerson,
            "Pippo",
            "P.pluto"
        );
    }

}
