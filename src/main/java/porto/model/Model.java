package porto.model;

import java.sql.Timestamp;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import porto.data.api.Ideology;
import porto.data.api.ParkingSpace;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestState;
import porto.data.api.Starship;
import porto.data.api.PersonRole;
import porto.data.api.RequestType;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.api.dao.PersonDAO;
import porto.data.api.dao.PlanetDAO;
import porto.data.api.dao.RequestDAO;
import porto.data.api.dao.StarshipDAO;
import porto.data.dao.ParkingSpaceDAOImpl;
import porto.data.dao.PersonDAOImpl;
import porto.data.dao.PlanetDAOImpl;
import porto.data.dao.RequestDAOImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOException;

public final class Model {

    private final PersonDAO personDAO;
    private final StarshipDAO starshipDAO;
    private final RequestDAO requestDAO;
    private final PlanetDAO planetDAO;
    private final ParkingSpaceDAO parkingSpaceDAO;
    private Optional<Person> loggedUser = Optional.empty();
    private Optional<Starship> selectedStarship = Optional.empty();

    public Model(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.personDAO = new PersonDAOImpl(connection);
        this.starshipDAO = new StarshipDAOImpl(connection);
        this.requestDAO = new RequestDAOImpl(connection);
        this.planetDAO = new PlanetDAOImpl(connection);
        this.parkingSpaceDAO = new ParkingSpaceDAOImpl(connection);
    }

    public boolean login(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        this.loggedUser = personDAO.loginAndGetUser(username, password);
        return this.loggedUser.isPresent();
    }

    public Person getLoggedUser() {
        return this.loggedUser.orElseThrow(() -> new IllegalStateException("No user is logged in"));
    }

    public Set<Planet> getAllPlanets() {
        try {
            return planetDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving planets", e);
        }
    }

    public boolean isUserRegistered(String cui, String username) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        try {
            return personDAO.getFromCUI(cui).isPresent() || personDAO.getFromUsername(username).isPresent();
        } catch (DAOException e) {
            throw new RuntimeException("Error checking if user is already registered", e);
        }
    }

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

    public Set<Starship> getAvailableShips() {
        if (this.loggedUser.isEmpty()) {
            throw new IllegalStateException("No user is logged in");
        }
        var user = this.loggedUser.get();
        switch (user.role()) {
            case CREW_MEMBER:
            case CAPTAIN:
                try {
                    return starshipDAO.ofPerson(user.CUI());
                } catch (DAOException e) {
                    throw new RuntimeException("Error retrieving ships for crew/captain", e);
                }
            case ADMIN:
                try {
                    return starshipDAO.getAll();
                } catch (DAOException e) {
                    throw new RuntimeException("Error retrieving all ships for admin", e);
                }
            default:
                throw new IllegalStateException("Unknown role for logged user: " + loggedUser.get().role());
        }
    }

    public void logout() {
        if (this.loggedUser.isEmpty()) {
            throw new IllegalStateException("No user is logged in to logout");
        }
        this.loggedUser = Optional.empty();
    }

    public void selectStarship(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        try {
            this.selectedStarship = starshipDAO.fromPlate(plateNumber);
            if (this.selectedStarship.isEmpty()) {
                throw new IllegalArgumentException("No starship found with plate number: " + plateNumber);
            }
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving starship by plate number", e);
        }
    }

    public Starship getSelectedStarship() {
        return this.selectedStarship.orElseThrow(() -> new IllegalStateException("No starship is selected"));
    }

    public Optional<ParkingSpace> getParkingSpace(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        try {
            return parkingSpaceDAO.ofStarship(plateNumber);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving parking space for plate number: " + plateNumber, e);
        }
    }

    public Optional<Request> getLastRequestOfStarship(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        try {
            return requestDAO.getLastRequestOfStarship(plateNumber);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving last request for starship with plate number: " + plateNumber, e);
        }
    }

    public Optional<RequestState> getRequestState(int codrequest) {
        Objects.requireNonNull(codrequest, "Request cannot be null");
        try {
            return requestDAO.getRequestState(codrequest);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving request state for request: " + codrequest, e);
        }
    }

    public Optional<Timestamp> getRequestDateTimeManaged(int codRichiesta) {
        Objects.requireNonNull(codRichiesta, "Request cannot be null");
        try {
            return requestDAO.getRequestDateTimeManaged(codRichiesta);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving request date time managed for request: " + codRichiesta, e);
        }
    }

    public Optional<Person> getRequestManagedBy(int codRichiesta) {
        Objects.requireNonNull(codRichiesta, "Request cannot be null");
        try {
            return requestDAO.getRequestManagedBy(codRichiesta);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving request managed by for request: " + codRichiesta, e);
        }
    }

    // ADMIN OPERATION TO USE

    public List<Request> getAllRequestsPendent() {
        return requestDAO.pendingRequests();
    }

    public void judgeRequest(int requestCod, boolean judgment, Optional<Integer> parking) {
        var request = requestDAO.getRequestByCodRequest(requestCod).get();

        try {

            if (loggedUser.isPresent()) {
                var adminCUI = loggedUser.get().CUI();
                if (judgment && request.type().equals(RequestType.ENTRY)) {
                    requestDAO.acceptEnterRequest(request.codRichiesta(), adminCUI, parking.get());
                } else if (judgment && request.type().equals(RequestType.EXIT)) {
                    requestDAO.acceptExitRequest(request.codRichiesta(), adminCUI);
                } else {
                    requestDAO.rejectRequest(request.codRichiesta(), adminCUI);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Not all Argumente are Passed for operation", e);
        }
    }

    public void arrestPerson(String CUI) {
        personDAO.arrestPerson(CUI);
    }

    public int numberOfPeople() {
        return parkingSpaceDAO.getNumberOfPeopleOnStation();
    }

    public String acceptedRejectedPercentage(Timestamp start, Timestamp to) {
        var percetage = requestDAO.acceptedAndRejectedPercentages(start, to);
        return "Dal:" + start.toString() + " Al:" + to.toString() + " Accettate:" + percetage.getLeft() + " Rifiutate"
                + percetage.getRight();
    }

    public Map<Starship, Integer> best50Starships() {
        return this.starshipDAO.get50TransportedMost();
    }
}
