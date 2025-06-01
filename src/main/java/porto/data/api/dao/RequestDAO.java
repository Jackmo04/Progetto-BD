package porto.data.api.dao;

import java.util.Optional;

import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.Starship;
import porto.data.utils.DAOException;

public interface RequestDAO {

    public Optional<Request> getRequestByCodRequest(Integer codRequest) throws DAOException;

    public void addExitRequest(String description, Starship starship, String scope, Planet destinationPlanet)
            throws DAOException;

    public void addEntryRequest(String description, Starship starship, String scope, Planet originPlanet)
            throws DAOException;

    public String getDettaliedRequest (Integer codRequest) throws DAOException;

}
