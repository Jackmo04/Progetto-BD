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

public final class View {

    private static final double FRAME_SIZE_FACTOR = 0.8;

    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    public View(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = new JFrame("Porto Morte Nera");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final var initWidth = (int) (screenSize.width * FRAME_SIZE_FACTOR);
        final var initHeight = (int) (screenSize.height * FRAME_SIZE_FACTOR);
        this.mainFrame.setSize(new Dimension(initWidth, initHeight));
        this.mainFrame.setResizable(false);

        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.mainFrame.setContentPane(this.mainPanel);

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

    private Controller getController() {
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

    public final void changeScene(final Scene scene) {
        Objects.requireNonNull(scene, "Scene cannot be null");
        if (this.controller.isEmpty()) {
            throw new IllegalStateException(
                "Cannot change scene, the controller is not set. " +
                "Did you forget to call `setController`?"
            );
        }
        this.mainPanel.add(scene.getPanel(), scene.getSceneName());
        this.cardLayout.show(this.mainPanel, scene.getSceneName());
    }

}
