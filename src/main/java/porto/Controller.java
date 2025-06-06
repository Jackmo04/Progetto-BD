package porto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;

import porto.data.api.ParkingArea;
import porto.data.api.ParkingSpace;
import porto.data.api.Person;
import porto.data.api.PersonRole;
import porto.data.api.Planet;
import porto.data.api.Request;
import porto.data.api.RequestState;
import porto.data.api.Starship;
import porto.data.utils.DAOException;
import porto.model.Model;
import porto.view.View;

public final class Controller {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Controller.class);

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
    }

    public void initialScene() {
        this.view.initialScene();
    }

    public boolean userClickedLogin(String CuiUsername, String password) {
        try {
            if (this.model.login(CuiUsername, password)) {
                var loggedUser = this.model.getLoggedUser();
                LOGGER.info("User {} logged in successfully", loggedUser.username());
                loginSuccess(loggedUser);
                return true;
            } else {
                LOGGER.warn("Login failed for user {}", CuiUsername);
                loginFailed("CUI/Username o password errati!");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userClickedRegister(String cui, String username, String password, String name, String surname,
            String race, String dob, boolean wanted, String ideology, boolean isCaptain, String planet) {
        if (this.model.isUserRegistered(cui, username)) {
            LOGGER.warn("User {} with CUI {} is already registered", username, cui);
            this.view.displayRegisterError("CUI o Username già registrati!");
            return false;
        }
        try {
            this.model.registerUser(cui, username, password, name, surname, race, dob, wanted, ideology, isCaptain,
                    planet);
            LOGGER.info("User {} registered successfully", username);
            return true;
        } catch (DAOException e) {
            LOGGER.error("Error during user registration", e);
            this.view.displayRegisterError("Errore durante la registrazione!");
        }
        return false;
    }

    public void userClickedRegisterOnLogin() {
        this.view.goToRegisterScene();
    }

    public Person getLoggedUser() {
        return this.model.getLoggedUser();
    }

    public Starship getSelectedStarship() {
        return this.model.getSelectedStarship();
    }

    public String[] getPlanetChoices() {
        try {
            return this.model.getAllPlanets()
                    .stream()
                    .map(Planet::name)
                    .sorted(Comparator.naturalOrder())
                    .toArray(String[]::new);
        } catch (DAOException e) {
            LOGGER.error("Error retrieving planets names", e);
            return new String[0];
        }
    }

    private void loginSuccess(Person loggedUser) {
        this.view.userLoggedIn();
        switch (loggedUser.role()) {
            case ADMIN:
                this.view.goToAdminScene();
                break;

            case CAPTAIN:
            case CREW_MEMBER:
                this.view.goToWelcomeScene();
                break;

            default:
                throw new IllegalStateException("Unknown role for logged user: " + loggedUser.role());
        }
    }

    private void loginFailed(String message) {
        this.view.displayLoginError("CUI/Username o password errati!");
    }

    public List<Starship> getAvailableShips() {
        try {
            return this.model.getAvailableShips().stream()
                    .sorted(Comparator.comparing(Starship::plateNumber))
                    .toList();
        } catch (DAOException e) {
            LOGGER.error("Error retrieving available ships", e);
            return List.of();
        }
    }

    public void logout() {
        this.model.logout();
        this.view.goToLoginScene();
        LOGGER.info("User logged out successfully");
    }

    public void manageShip(String plateNumber) {
        LOGGER.info("Managing ship with plate number: {}", plateNumber);
        this.model.selectStarship(plateNumber);

        switch (this.model.getLoggedUser().role()) {
            case CAPTAIN:
                this.view.goToCaptainScene();
                break;

            case CREW_MEMBER:
                this.view.goToCrewScene();
                break;

            case ADMIN:
                this.view.goToEquipeScene(plateNumber);
                break;

            default:
                throw new IllegalStateException("Unknown role for logged user: " + this.model.getLoggedUser().role());
        }

    }

    public Optional<ParkingSpace> getParkingSpace(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        try {
            return this.model.getParkingSpace(plateNumber);
        } catch (DAOException e) {
            LOGGER.error("Error retrieving parking space for plate number: {}", plateNumber, e);
            throw new RuntimeException("Error retrieving parking space", e);
        }
    }

    public Optional<ParkingSpace> getParkingOfSelectedStarship() {
        if (this.model.getSelectedStarship() == null) {
            throw new IllegalStateException("No starship is selected");
        }
        String plateNumber = this.model.getSelectedStarship().plateNumber();
        return getParkingSpace(plateNumber);
    }

    public Optional<Request> getLastRequestOfSelectedStarship() {
        if (this.model.getSelectedStarship() == null) {
            throw new IllegalStateException("No starship is selected");
        }
        String plateNumber = this.model.getSelectedStarship().plateNumber();
        try {
            return this.model.getLastRequestOfStarship(plateNumber);
        } catch (DAOException e) {
            LOGGER.error("Error retrieving last request for starship with plate number: {}", plateNumber, e);
            throw new RuntimeException("Error retrieving last request", e);
        }
    }

    public RequestState getRequestState(Request request) {
        Objects.requireNonNull(request, "Request cannot be null");
        try {
            return this.model.getRequestState(request.codRichiesta()).orElseThrow();
        } catch (DAOException e) {
            LOGGER.error("Error retrieving request state for request: {}", request, e);
            throw new RuntimeException("Error retrieving request state", e);
        }
    }

    public Optional<Timestamp> getRequestDateTimeManaged(Request request) {
        Objects.requireNonNull(request, "Request cannot be null");
        try {
            return this.model.getRequestDateTimeManaged(request.codRichiesta());
        } catch (DAOException e) {
            LOGGER.error("Error retrieving request date time managed for request: {}", request, e);
            throw new RuntimeException("Error retrieving request date time managed", e);
        }
    }

    public Optional<Person> getRequestManagedBy(Request request) {
        Objects.requireNonNull(request, "Request cannot be null");
        try {
            return this.model.getRequestManagedBy(request.codRichiesta());
        } catch (DAOException e) {
            LOGGER.error("Error retrieving request managed by for request: {}", request, e);
            throw new RuntimeException("Error retrieving request managed by", e);
        }
    }

    public List<String> viewAllPendentRequest() {
        var pendentRequest = this.model.getAllRequestsPendent();
        try {
            return pendentRequest.stream().map(
                    t -> "Numero:" + t.codRichiesta() + "Descrizione:" + t.description() + "Price:" + t.totalPrice())
                    .toList();
        } catch (DAOException e) {
            LOGGER.error("Error retrieving pendent requests", e);
            return new ArrayList<>();
        }
    }

    public void judgePendentRequest(int requestCod, boolean judge, Optional<Integer> parking) {
        this.model.judgeRequest(requestCod, judge, parking);
    }

    public void arrestPerson(String CUI) {
        this.model.arrestPerson(CUI);
    }

    public Integer seeNumberPeople() {
        return this.model.numberOfPeople();
    };

    public String acceptedRejectedPercentage(Timestamp start, Timestamp to) {
        return this.model.acceptedRejectedPercentage(start, to);
    }

    public Map<Starship, Integer> best50TrasporteStarships() {
        return this.model.best50Starships();
    }

    public void manageRequest(int codRequest) {
        this.view.goToJudgeScene(codRequest);
    }

    public List<ParkingArea> getAllFreeArea() {
        return this.model.getAllFreeArea();
    }

    public List<Request> getAllPendentRequest() {
        return this.model.getAllRequestsPendent();
    }

    public List<Person> getPeopleOnStation() {
        return this.model.getPeopleOnStation();
    }

    public String[] getModelChoices() {
        try {
            return this.model.getAllShipModels()
                .stream()
                .map(model -> model.codModel())
                .sorted(Comparator.naturalOrder())
                .toArray(String[]::new);
        } catch (DAOException e) {
            LOGGER.error("Error retrieving starship models codes", e);
            return new String[0];
        }
    }

    public boolean isStarshipRegistered(String plateNumber) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        return this.model.isStarshipRegistered(plateNumber);
    }

    public boolean registerStarshipToCurrentUser(String plateNumber, String shipName, String shipModelCode) {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        Objects.requireNonNull(shipName, "Ship name cannot be null");
        Objects.requireNonNull(shipModelCode, "Ship model code cannot be null");
        if (this.model.getLoggedUser().role() != PersonRole.CAPTAIN) {
            throw new IllegalStateException("Only captains can register starships");
        }
        if (this.model.isStarshipRegistered(plateNumber)) {
            LOGGER.warn("Starship with plate number {} is already registered", plateNumber);
            return false;
        }

        try {
            this.model.registerStarship(plateNumber, shipName, shipModelCode, this.model.getLoggedUser().CUI());
            LOGGER.info("Starship {} registered successfully", plateNumber);
            return true;
        } catch (DAOException e) {
            LOGGER.error("Error registering starship with plate number: {}", plateNumber, e);
            return false;
        }
    }

    public List<Person> getCrewMembersOfSelectedShip() {
        if (this.model.getSelectedStarship() == null) {
            throw new IllegalStateException("No starship is selected");
        }
        try {
            return this.model.getCrewMembersOfShip(this.model.getSelectedStarship().plateNumber())
                .stream()
                .sorted(Comparator.comparing(Person::CUI))
                .toList();
        } catch (DAOException e) {
            throw new RuntimeException("Error retrieving crew members of selected ship", e);
        }
    }

    public boolean isCrewMemberOfSelectedShip(String cui) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        if (this.model.getSelectedStarship() == null) {
            throw new IllegalStateException("No starship is selected");
        }
        try {
            return getCrewMembersOfSelectedShip()
                .stream()
                .anyMatch(person -> person.CUI().equals(cui));
        } catch (DAOException e) {
            LOGGER.error("Error checking if crew member with CUI {} is part of selected ship", cui, e);
            return false;
        }
    }

    public boolean isValidCrewMemberCUI(String cui) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        if (cui.isBlank()) {
            return false;
        }
        try {
            return this.model.isValidCrewMemberCUI(cui);
        } catch (DAOException e) {
            LOGGER.error("Error validating crew member CUI: {}", cui, e);
            return false;
        }
    }

    public boolean addCrewMemberToSelectedShip(String cui) {
        Objects.requireNonNull(cui, "CUI cannot be null");
        if (this.model.getSelectedStarship() == null) {
            throw new IllegalStateException("No starship is selected");
        }
        if (isCrewMemberOfSelectedShip(cui)) {
            LOGGER.warn("Crew member with CUI {} is already part of selected ship", cui);
            return false;
        }
        if (!isValidCrewMemberCUI(cui)) {
            LOGGER.warn("Invalid CUI for crew member: {}", cui);
            return false;
        }
        try {
            this.model.addCrewMemberToShip(this.model.getSelectedStarship().plateNumber(), cui);
            LOGGER.info("Crew member with CUI {} added to ship {}", cui, this.model.getSelectedStarship().plateNumber());
            return true;
        } catch (DAOException e) {
            LOGGER.error("Error adding crew member with CUI {} to selected ship", cui, e);
            throw new RuntimeException("Error adding crew member", e);
        }
    }

    public boolean isArrested(String cui) {
    Objects.requireNonNull(cui, "CUI cannot be null");
        try {
            return this.model.isArrested(cui);
        } catch (DAOException e) {
            LOGGER.error("Error checking if person with CUI {} is arrested", cui, e);
            throw new RuntimeException("Error checking arrest status", e);
        }
    }

    public void removeCrewMemberFromSelectedShip(String cui) {
    Objects.requireNonNull(cui, "CUI cannot be null");
        if (this.model.getSelectedStarship() == null) {
            throw new IllegalStateException("No starship is selected");
        }
        if (!isCrewMemberOfSelectedShip(cui)) {
            throw new IllegalStateException("Crew member with CUI " + cui + " is not part of the selected ship");
        }
        try {
            this.model.removeCrewMemberFromShip(this.model.getSelectedStarship().plateNumber(), cui);
            LOGGER.info("Crew member with CUI {} removed from ship {}", cui, this.model.getSelectedStarship().plateNumber());
        } catch (DAOException e) {
            LOGGER.error("Error removing crew member with CUI {} from selected ship", cui, e);
            throw new RuntimeException("Error removing crew member", e);
        }
    }

    public List<Request> getRequestsOfSelectedStarship() {
        if (this.model.getSelectedStarship() == null) {
            throw new IllegalStateException("No starship is selected");
        }
        try {
            return this.model.getRequestsOfStarship(this.model.getSelectedStarship().plateNumber())
                .stream()
                .sorted(Comparator.comparing(Request::codRichiesta))
                .toList();
        } catch (DAOException e) {
            LOGGER.error("Error retrieving requests of selected starship", e);
            throw new RuntimeException("Error retrieving requests", e);
        }
    }

    public boolean hasPendingRequest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasPendingRequest'");
    }

    public List<Starship> getStarshipOnBoard() {
        return this.model.getStarshipOnBoard();
    }

    public List<Person> getStarshipeEquipe(String plate) {
        return this.model.getStarshipEquipe(plate);
    }

}
