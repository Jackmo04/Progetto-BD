package porto;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;

import porto.data.api.Person;
import porto.data.api.PersonRole;
import porto.data.api.Planet;
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
            this.model.registerUser(cui, username, password, name, surname, race, dob, wanted, ideology, isCaptain, planet);
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
                this.view.goToAdminScene();
                break;

            default:
                throw new IllegalStateException("Unknown role for logged user: " + this.model.getLoggedUser().role());
        }

    }
}


