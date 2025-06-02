package porto.data.api.dao;

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
         * Retrieves detailed information about a request by its unique code.
         * @param codRequest the unique code of the request
         * @return a String containing detailed information about the request
         * @throws DAOException if an error occurs while accessing the database
         */
    public String getDettaliedRequest (Integer codRequest) throws DAOException;

}
