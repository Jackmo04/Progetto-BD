package porto.data.api.dao;

import java.util.List;
import java.util.Optional;

import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.Starship;
import porto.data.utils.DAOException;

/**
 * Interface representing a Data Access Object (DAO) for requests.
 * Provides methods to interact with requests in the database, including retrieving,
 * adding, and getting detailed information about requests.
 */
public interface RequestDAO {

        /**
         * Retrieves a request by its unique code.
         *
         * @param codRequest the unique code of the request
         * @return an Optional containing the Request if found, or empty if not found
         * @throws DAOException if an error occurs while accessing the database
         */
    public Optional<Request> getRequestByCodRequest(Integer codRequest) throws DAOException;

        /**
         * Adds a new exit request to the database.
         *
         * @param description the description of the request
         * @param starship the starship associated with the request
         * @param scope the scope of the request
         * @param destinationPlanet the destination planet for the request
         * @throws DAOException if an error occurs while accessing the database
         */
    public void addExitRequest(String description, Starship starship, String scope, Planet destinationPlanet)
            throws DAOException;

        /**
         * Adds a new entry request to the database.
         * @param description the description of the request
         * @param starship the starship associated with the request
         * @param scope the scope of the request
         * @param originPlanet the origin planet for the request
         * @throws DAOException if an error occurs while accessing the database
         */
    public void addEntryRequest(String description, Starship starship, String scope, Planet originPlanet)
            throws DAOException;


        /**
         * Retrieves the last request made for a specific plate.
         * @param plate the plate of the starship for which to retrieve the last request
         * @return the last request made for the specified plate, or null if no request exists
         * @throws DAOException if an error occurs while accessing the database
         */    
    public Optional<Request> getLastRequest (String  plate) throws DAOException;

        /**
         * Retrieves the history of requests made for a specific plate.
         * @param plate the plate of the starship for which to retrieve the request history
         * @return a list of requests associated with the specified plate
         * @throws DAOException if an error occurs while accessing the database
         */
    public List<Request> requestHistory (String  plate) throws DAOException;


        /**
         * Retrieves a list of all requests in the database.
         *
         * @return a list of all requests
         * @throws DAOException if an error occurs while accessing the database
         */
    public List<Request> pendingRequests() throws DAOException;

        /**
         * Accepts a request by its unique code and the CUI of the admin processing it.
         * @param codRequest
         * @param CUIAdmin
         * @throws DAOException
         */
    public void acceptEnterRequest(Integer codRequest , String CUIAdmin) throws DAOException;

        /**
         * Rejects a request by its unique code and the CUI of the admin processing it.
         * @param codRequest
         * @param CUIAdmin
         * @throws DAOException
         */
    public void rejectRequest(Integer codRequest , String CUIAdmin) throws DAOException;

}
