package porto.data.api.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;

import porto.data.api.FlightPurpose;
import porto.data.api.Payload;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestState;
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
     * @param codRequest the unique code of the request
     * @return an Optional containing the Request if found, or empty if not found
     * @throws DAOException if an error occurs while accessing the database
     */
    Optional<Request> getRequestByCodRequest(Integer codRequest) throws DAOException;

    /**
     * Retrieves a request state.
     * @param codRequest the unique code of the request
     * @return an Optional containing the RequestState if the request exists, or empty if not
     */
    Optional<RequestState> state(int codRequest);

    /**
     * Retrieves a request state.
     * @param request the request object
     * @return an Optional containing the RequestState if the request exists, or empty if not
     */
    Optional<RequestState> state(Request request);

    /**
     * Returns the date and time when the request was managed.
     * @param codRequest the unique code of the request
     * @return an Optional containing the Timestamp if available, or empty if not
     */
    Optional<Timestamp> dateTimeManaged(int codRequest);

    /**
     * Returns the date and time when the request was managed.
     * @param request the request object
     * @return an Optional containing the Timestamp if available, or empty if not
     */
    Optional<Timestamp> dateTimeManaged(Request request);

    /**
     * Retrieves the person who managed the request by its unique code.
     * @param codRequest the unique code of the request
     * @return an Optional containing the Person if found, or empty if not found
     */
    Optional<Person> managedBy(int codRequest);

    /**
     * Retrieves the person who managed the request.
     * @param request the request object
     * @return an Optional containing the Person if found, or empty if not found
     */
    Optional<Person> managedBy(Request request);

    /**
     * Adds a new exit request to the database.
     * @param description the description of the request
     * @param starship the starship associated with the request
     * @param purpose the purpose of the flight
     * @param destinationPlanet the destination planet for the request
     * @param payloads the set of payloads associated with the request
     * @return the newly created Request object
     * @throws DAOException if an error occurs while accessing the database
     */
    public Request addExitRequest(
        String description, 
        FlightPurpose purpose, 
        Starship starship, 
        Planet destinationPlanet, 
        Set<Payload> payloads
    ) throws DAOException;

    /**
     * Adds a new entry request to the database.
     * @param description the description of the request
     * @param starship the starship associated with the request
     * @param purpose the purpose of the flight
     * @param originPlanet the origin planet for the request
     * @param payloads the set of payloads associated with the request
     * @return the newly created Request object
     * @throws DAOException if an error occurs while accessing the database
     */
    public Request addEntryRequest(
        String description, 
        FlightPurpose purpose, 
        Starship starship, 
        Planet originPlanet,
        Set<Payload> payloads
    ) throws DAOException;


    /**
     * Retrieves the last request made for a specific plate.
     * @param plate the plate of the starship for which to retrieve the last request
     * @return the last request made for the specified plate, or null if no request exists
     * @throws DAOException if an error occurs while accessing the database
     */    
    public Optional<Request> getLastRequest(String plate) throws DAOException;

    /**
     * Retrieves the history of requests made for a specific plate.
     * @param plate the plate of the starship for which to retrieve the request history
     * @return a list of requests associated with the specified plate
     * @throws DAOException if an error occurs while accessing the database
     */
    public List<Request> requestHistory(String plate) throws DAOException;


    /**
     * Retrieves a list of all requests in the database.
     * @return a list of all requests
     * @throws DAOException if an error occurs while accessing the database
     */
    public List<Request> pendingRequests() throws DAOException;

    /**
     * Accepts a request by its unique code and the CUI of the admin processing it.
     * @param codRequest the unique code of the request
     * @param CUIAdmin the CUI of the admin processing the request
     * @throws DAOException
     */
    public void acceptEnterRequest(int codRequest, String CUIAdmin) throws DAOException;

    /**
     * Accepts a request by its unique code and the CUI of the admin processing it.
     * @param codRequest  the unique code of the request
     * @param CUIAdmin the CUI of the admin processing the request
     * @throws DAOException
     */
    public void acceptExitRequest(int codRequest, String CUIAdmin) throws DAOException;

    /**
     * Rejects a request by its unique code and the CUI of the admin processing it.
     * @param codRequest
     * @param CUIAdmin
     * @throws DAOException
     */
    public void rejectRequest(int codRequest, String CUIAdmin) throws DAOException;

    /**
     * Retrieves the percentage of accepted and rejected requests within a specified date range.
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return an ImmutablePair containing the percentage of accepted requests and rejected requests
     * @throws DAOException if an error occurs while accessing the database
     */
    ImmutablePair<Double, Double> acceptedAndRejectedPercentages(Timestamp startDate, Timestamp endDate) throws DAOException;

}
