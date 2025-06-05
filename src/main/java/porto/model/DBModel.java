package porto.model;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import porto.data.api.Ideology;
import porto.data.api.ParkingSpace;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestType;
import porto.data.api.PersonRole;
import porto.data.api.dao.ParkingAreaDAO;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.api.dao.PersonDAO;
import porto.data.api.dao.PlanetDAO;
import porto.data.api.dao.RequestDAO;
import porto.data.api.dao.StarshipDAO;
import porto.data.dao.ParkingAreaDAOImpl;
import porto.data.dao.ParkingSpaceDAOImpl;
import porto.data.dao.PersonDAOImpl;
import porto.data.dao.PlanetDAOImpl;
import porto.data.dao.RequestDAOImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOException;

public final class DBModel implements Model {

    private final PersonDAO personDAO;
    private final StarshipDAO starshipDAO;
    private final RequestDAO requestDAO;
    private final PlanetDAO planetDAO;
    private final ParkingSpaceDAO parkingSpaceDAO;
    private Optional<Person> loggedUser = Optional.empty();

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.personDAO = new PersonDAOImpl(connection);
        this.starshipDAO = new StarshipDAOImpl(connection);
        this.requestDAO = new RequestDAOImpl(connection);
        this.planetDAO = new PlanetDAOImpl(connection);
        this.parkingSpaceDAO = new ParkingSpaceDAOImpl(connection);
    }

    @Override
    public boolean login(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        this.loggedUser = personDAO.loginAndGetUser(username, password);
        return this.loggedUser.isPresent();
    }

    @Override
    public Person getLoggedUser() {
        return this.loggedUser.orElseThrow(() -> new IllegalStateException("No user is logged in"));
    }

    @Override
    public Set<Planet> getAllPlanets() {
        try {
            return planetDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving planets", e);
        }
    }

    @Override
    public boolean isUserRegistered(String cui, String username) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        try {
            return personDAO.getFromCUI(cui).isPresent() || personDAO.getFromUsername(username).isPresent();
        } catch (DAOException e) {
            throw new RuntimeException("Error checking if user is already registered", e);
        }
    }

    @Override
    public void registerUser(String cui, String username, String password, String name, String surname, String race,
            String dob, boolean wanted, String ideology, boolean isCaptain, String planetName) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(surname, "Surname cannot be null");
        Objects.requireNonNull(race, "Race cannot be null");
        Objects.requireNonNull(dob, "Date of birth cannot be null");
        Objects.requireNonNull(ideology, "Ideology cannot be null");
        Objects.requireNonNull(planetName, "Planet cannot be null");
        var ideologyObj = Ideology.fromString(ideology);
        var role = isCaptain ? PersonRole.CAPTAIN : PersonRole.CREW_MEMBER;
        var planet = planetDAO.getFromName(planetName).orElseThrow();
        try {
            if (personDAO.getFromCUI(cui).isPresent()) {
                throw new IllegalArgumentException("A user with this CUI already exists");
            }
            personDAO.addPerson(cui, username, password, name, surname, race, dob, wanted, ideologyObj, role, planet);
        } catch (DAOException e) {
            throw new RuntimeException("Error registering user", e);
        }
    }

    @Override
    public List<Request> getAllRequestsPendent() {
        return requestDAO.pendingRequests();
    }

    @Override
    public void judgeRequest(int requestCod, boolean judgment) {
        var request = requestDAO.getRequestByCodRequest(requestCod).get();

        if (loggedUser.isPresent()) {
            var adminCUI = loggedUser.get().CUI();
            if (judgment && request.type().equals(RequestType.ENTRY)) {
                requestDAO.acceptEnterRequest(request.codRichiesta(), adminCUI);
            } else if (judgment && request.type().equals(RequestType.EXIT)) {
                requestDAO.acceptExitRequest(request.codRichiesta(), adminCUI);
            } else {
                requestDAO.rejectRequest(request.codRichiesta(), adminCUI);
            }
        }
    }

    @Override
    public void arrestPerson(String CUI) {
        personDAO.arrestPerson(CUI);
    }

    @Override
    public Integer numberOfPeople() {
        parkingSpaceDAO.getNumberOfPeopleOnStation()
    }

}
