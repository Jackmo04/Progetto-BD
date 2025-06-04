package porto;

import java.util.Objects;

import org.slf4j.Logger;

import porto.data.api.Person;
import porto.data.api.PersonRole;
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
        this.view.goToLoginScene();
    }

    public void userClickedLogin(String CuiUsername, String password) {
        try {
            if (this.model.login(CuiUsername, password)) {
                var loggedUser = this.model.getLoggedUser();
                LOGGER.info("User {} logged in successfully", loggedUser.username());
                loginSuccess(loggedUser);
            } else {
                LOGGER.warn("Login failed for user {}", CuiUsername);
                loginFailed("CUI/Username o password errati!");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userClickedRegisterOnLogin() {
        this.view.goToRegisterScene();
    }

    public Person getLoggedUser() {
        return this.model.getLoggedUser();
    }

    private void loginSuccess(Person loggedUser) {
        switch (loggedUser.role()) {
            case PersonRole.ADMIN:
                this.view.goToAdminScene();
                break;

            case PersonRole.CAPTAIN:
                this.view.goToCaptainScene();
                break;

            case PersonRole.CREW_MEMBER:
                this.view.goToCrewScene();
                break;

            default:
                throw new IllegalStateException("Unknown role for logged user: " + loggedUser.role());
        }
    }

    private void loginFailed(String message) {
        this.view.displayLoginError("CUI/Username o password errati!");
    }

}
