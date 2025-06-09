package porto.model;

import java.sql.Timestamp;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import porto.data.api.FlightPurpose;
import porto.data.api.Ideology;
import porto.data.api.ParkingArea;
import porto.data.api.ParkingSpace;
import porto.data.api.Payload;
import porto.data.api.PayloadType;
import porto.data.api.Person;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestState;
import porto.data.api.Starship;
import porto.data.api.PersonRole;
import porto.data.api.RequestType;
import porto.data.api.ShipModel;
import porto.data.api.dao.CellDAO;
import porto.data.api.dao.FlightPurposeDAO;
import porto.data.api.dao.ParkingSpaceDAO;
import porto.data.api.dao.PayloadDAO;
import porto.data.api.dao.PayloadTypeDAO;
import porto.data.api.dao.PersonDAO;
import porto.data.api.dao.PlanetDAO;
import porto.data.api.dao.RequestDAO;
import porto.data.api.dao.ShipModelDAO;
import porto.data.api.dao.StarshipDAO;
import porto.data.dao.CellDAOImpl;
import porto.data.dao.FlightPurposeDAOImpl;
import porto.data.dao.ParkingSpaceDAOImpl;
import porto.data.dao.PayloadDAOImpl;
import porto.data.dao.PayloadTypeDAOImpl;
import porto.data.dao.PersonDAOImpl;
import porto.data.dao.PlanetDAOImpl;
import porto.data.dao.RequestDAOImpl;
import porto.data.dao.ShipModelDAOImpl;
import porto.data.dao.StarshipDAOImpl;
import porto.data.utils.DAOException;

public final class Model {

    private final PersonDAO personDAO;
    private final StarshipDAO starshipDAO;
    private final RequestDAO requestDAO;
    private final PlanetDAO planetDAO;
    private final ParkingSpaceDAO parkingSpaceDAO;
    private final ShipModelDAO shipModelDAO;
    private final CellDAO cellDAO;
    private final FlightPurposeDAO flightPurposeDAO;
    private final PayloadDAO payloadDAO;
    private final PayloadTypeDAO payloadTypeDAO;
    private Optional<Person> loggedUser = Optional.empty();
    private Optional<Starship> selectedStarship = Optional.empty();

    public Model(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.personDAO = new PersonDAOImpl(connection);
        this.starshipDAO = new StarshipDAOImpl(connection);
        this.requestDAO = new RequestDAOImpl(connection);
        this.planetDAO = new PlanetDAOImpl(connection);
        this.parkingSpaceDAO = new ParkingSpaceDAOImpl(connection);
        this.shipModelDAO = new ShipModelDAOImpl(connection);
        this.cellDAO = new CellDAOImpl(connection);
        this.flightPurposeDAO = new FlightPurposeDAOImpl(connection);
        this.payloadDAO = new PayloadDAOImpl(connection);
        this.payloadTypeDAO = new PayloadTypeDAOImpl(connection);
    }

    public boolean login(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        var user = personDAO.loginAndGetUser(username, password);
        if (user.isPresent() && !isArrested(user.get().CUI())) {
            this.loggedUser = user;
        } else {
            this.loggedUser = Optional.empty();
        }
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
            throw new RuntimeException("Error retrieving last request for starship with plate number: " + plateNumber,
                    e);
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
        return "Dal:" + start.toString() + " Al:" + to.toString() + "\n Accettate:" + percetage.getLeft() + " Rifiutate:"
                + percetage.getRight();
    }

    public Map<Starship, Integer> best50Starships() {
        return this.starshipDAO.get50TransportedMost();
    }

    public List<ParkingArea> getAllFreeArea() {
        try {
            return parkingSpaceDAO.getAllFree().stream().map(t -> t.parkingArea())
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving all free parking areas", e);
        }
    }

    public List<Person> getPeopleOnStation() {
        return parkingSpaceDAO.getAllPeopleOnStation();
    }

    public Set<ShipModel> getAllShipModels() {
        try {
            return shipModelDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving all ship models", e);
        }
    }

    public void registerStarship(String plateNumber, String shipName, String shipModelCode, String captainCUI) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        Objects.requireNonNull(shipName, "Ship name cannot be null");
        Objects.requireNonNull(shipModelCode, "Ship model code cannot be null");
        Objects.requireNonNull(captainCUI, "Captain CUI cannot be null");

        if (plateNumber.isBlank() || shipName.isBlank() || shipModelCode.isBlank() || captainCUI.isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        try {
            starshipDAO.addStarship(plateNumber, shipName, shipModelCode, captainCUI);
        } catch (DAOException e) {
            throw new RuntimeException("Error registering starship", e);
        }
    }

    public boolean isStarshipRegistered(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        try {
            return starshipDAO.fromPlate(plateNumber).isPresent();
        } catch (DAOException e) {
            throw new RuntimeException("Error checking if starship is registered", e);
        }
    }

    public List<Person> getCrewMembersOfShip(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        try {
            return personDAO.getEquipeOfStarship(plateNumber);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving crew members of ship with plate number: " + plateNumber, e);
        }
    }

    public boolean isValidCrewMemberCUI(String cui) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        try {
            return personDAO.getFromCUI(cui)
                    .filter(p -> p.role() == PersonRole.CREW_MEMBER)
                    .isPresent();
        } catch (DAOException e) {
            throw new RuntimeException("Error checking if crew member CUI is valid: " + cui, e);
        }
    }

    public void addCrewMemberToShip(String plateNumber, String cui) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        Objects.requireNonNull(cui, "CUI cannot be null");
        try {
            if (!isValidCrewMemberCUI(cui)) {
                throw new IllegalArgumentException("Invalid crew member CUI: " + cui);
            }
            starshipDAO.addCrewMember(plateNumber, cui);
        } catch (DAOException e) {
            throw new RuntimeException("Error adding crew member to ship", e);
        }
    }

    public boolean isArrested(String cui) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        try {
            return cellDAO.getOfPerson(cui).isPresent();
        } catch (DAOException e) {
            throw new RuntimeException("Error checking if person is arrested with CUI: " + cui, e);
        }
    }

    public void removeCrewMemberFromShip(String plateNumber, String cui) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        Objects.requireNonNull(cui, "CUI cannot be null");
        try {
            if (!isValidCrewMemberCUI(cui)) {
                throw new IllegalArgumentException("Invalid crew member CUI: " + cui);
            }
            starshipDAO.removeCrewMember(plateNumber, cui);
        } catch (DAOException e) {
            throw new RuntimeException("Error removing crew member from ship", e);
        }
    }

    public List<Request> getRequestsOfStarship(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        try {
            return requestDAO.starshipRequestHistory(plateNumber);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving requests of starship with plate number: " + plateNumber, e);
        }
    }

    public List<Starship> getStarshipOnBoard() {
        return parkingSpaceDAO.getAllStarshipIn();
    }

    public Map<Starship, Integer> getBestStarshipeTrasport(String plate) {
        return starshipDAO.get50TransportedMost();
    }

    public ImmutablePair<Double, Double> rejectAcceptedPertage(Timestamp start, Timestamp to) {
        Objects.requireNonNull(start, "Start timestamp cannot be null");
        return requestDAO.acceptedAndRejectedPercentages(start, to);
    }

    public List<Person> getStarshipEquipe(String plate) {
        return personDAO.getEquipeOfStarship(plate);
    }

    public Set<FlightPurpose> getAllFlightPurposes() {
        try {
            return flightPurposeDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving all flight purposes", e);
        }
    }

    public Set<Payload> getPayloadsOfRequest(int codRichiesta) {
        Objects.requireNonNull(codRichiesta, "Request code cannot be null");
        try {
            return payloadDAO.getOfRequest(codRichiesta);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving payloads of request with code: " + codRichiesta, e);
        }
    }

    public Set<PayloadType> getAllPayloadTypes() {
        try {
            return payloadTypeDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving all payload types", e);
        }
    }

    public Optional<PayloadType> getPayloadTypeFromName(String typeName) {
        Objects.requireNonNull(typeName, "Payload type name cannot be null");
        try {
            return payloadTypeDAO.getFromNameInCache(typeName);
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving payload type from name: " + typeName, e);
        }
    }

    public void addEntryRequest(String plateNumber, String description, String purpose, String planet,
            Set<Payload> payloads) {
        var ship = starshipDAO.fromPlate(plateNumber)
                .orElseThrow(() -> new IllegalArgumentException("No starship found with plate number: " + plateNumber));
        var purposeObj = flightPurposeDAO.getFromNameInCache(purpose)
                .orElseThrow(() -> new IllegalArgumentException("No flight purpose found with name: " + purpose));
        var planetObj = planetDAO.getFromName(planet)
                .orElseThrow(() -> new IllegalArgumentException("No planet found with name: " + planet));
        requestDAO.addEntryRequest(description, purposeObj, ship, planetObj, payloads);
    }

    public void addExitRequest(String plateNumber, String description, String purpose, String planet,
            Set<Payload> payloads) {
        var ship = starshipDAO.fromPlate(plateNumber)
                .orElseThrow(() -> new IllegalArgumentException("No starship found with plate number: " + plateNumber));
        var purposeObj = flightPurposeDAO.getFromNameInCache(purpose)
                .orElseThrow(() -> new IllegalArgumentException("No flight purpose found with name: " + purpose));
        var planetObj = planetDAO.getFromName(planet)
                .orElseThrow(() -> new IllegalArgumentException("No planet found with name: " + planet));
        requestDAO.addExitRequest(description, purposeObj, ship, planetObj, payloads);
    }
}
