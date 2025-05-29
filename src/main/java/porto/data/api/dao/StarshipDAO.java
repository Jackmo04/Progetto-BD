package porto.data.api.dao;

import java.util.Set;

import porto.data.api.Starship;
import porto.data.utils.DAOException;

public interface StarshipDAO {

    /**
     * Returns all the starships of which the specified person is the capitan or a crew member.
     * @param CUIPerson the CUI of the person whose starships are to be retrieved
     * @return a set of Starship objects owned by the person
     * @throws DAOException if an error occurs while accessing the database
     */
    Set<Starship> ofPerson(String CUIPerson) throws DAOException;
}
