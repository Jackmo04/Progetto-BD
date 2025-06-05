package porto.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;
import javax.swing.JFrame;
import javax.swing.JPanel;
import porto.Controller;
import porto.view.scenes.AdminScene;
import porto.view.scenes.CaptainScene;
import porto.view.scenes.CrewScene;
import porto.view.scenes.LoginScene;
import porto.view.scenes.RegisterScene;

public final class View {

    private static final String SN_REGISTER = "register";
    private static final String SN_ADMIN = "admin";
    private static final String SN_CAPTAIN = "capitan";
    private static final String SN_CREW = "crew";
    private static final String SN_LOGIN = "login";

    private static final double FRAME_SIZE_FACTOR = 0.8;

    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    public View(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = new JFrame("Porto Morte Nera");
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final var initWidth = (int) (screenSize.width * FRAME_SIZE_FACTOR);
        final var initHeight = (int) (screenSize.height * FRAME_SIZE_FACTOR);
        this.mainFrame.setSize(new Dimension(initWidth, initHeight));
        this.mainFrame.setMinimumSize(new Dimension(800, 600));
        
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.mainFrame.setContentPane(this.mainPanel);

        this.mainPanel.add(new LoginScene(this), SN_LOGIN);

        this.mainFrame.setLocationByPlatform(true);
        this.mainFrame.setVisible(true);

        this.mainFrame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
    }

    public Controller getController() {
        if (this.controller.isPresent()) {
            return this.controller.get();
        } else {
            throw new IllegalStateException(
                """
                The View's Controller is undefined, did you remember to call
                `setController` before starting the application?
                Remeber that `View` needs a reference to the controller in order
                to notify it of button clicks and other changes.
                """
            );
        }
    }

    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = Optional.of(controller);
    }

    public void initialScene() {
        this.mainPanel.add(new RegisterScene(this), SN_REGISTER);
        goToLoginScene();
    }

    public void userLoggedIn() {
        this.mainPanel.add(new CrewScene(this), SN_CREW);
        this.mainPanel.add(new CaptainScene(this), SN_CAPTAIN);
        this.mainPanel.add(new AdminScene(this), SN_ADMIN);
    }

    public void goToLoginScene() {
        this.cardLayout.show(this.mainPanel, SN_LOGIN);
    }

    public void goToRegisterScene() {
        this.cardLayout.show(this.mainPanel, SN_REGISTER);
    }

    public void goToCrewScene() {
        this.cardLayout.show(this.mainPanel, SN_CREW);
    }

    public void goToCaptainScene() {
        this.cardLayout.show(this.mainPanel, SN_CAPTAIN);
    }

    public void goToAdminScene() {
        this.cardLayout.show(this.mainPanel, SN_ADMIN);
    }

    public void displayLoginError(String message) {
        Objects.requireNonNull(message, "Message cannot be null");
        if (this.mainPanel.getComponent(0) instanceof LoginScene loginScene) {
            loginScene.displayLoginFail(message);
        } else {
            throw new IllegalStateException(
                "Cannot display login error, current scene is not a login scene."
            );
        }
    }

    public void displayRegisterError(String string) {
        Objects.requireNonNull(string, "Message cannot be null");
        if (this.mainPanel.getComponent(1) instanceof RegisterScene registerScene) {
            registerScene.displayRegisterError(string);
        } else {
            throw new IllegalStateException(
                "Cannot display register error, current scene is not a register scene."
            );
        }
    }

}
