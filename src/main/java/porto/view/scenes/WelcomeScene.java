package porto.view.scenes;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import porto.view.View;
import porto.view.utils.ShipSelectionPanel;

public class WelcomeScene extends JPanel {

    private static final String FONT = "Roboto";

    private final View view;

    public WelcomeScene(View view) {
        this.view = view;
        this.setLayout(new BorderLayout());

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        this.add(mainPanel, BorderLayout.CENTER);

        final String fullName = this.view.getController().getLoggedUser().fullName();
        final JLabel title = new JLabel("Benvenuto, " + fullName, JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        final JPanel shipSelectionPanel = new ShipSelectionPanel(view);
        mainPanel.add(shipSelectionPanel);
        
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font(FONT, Font.BOLD, 16));
        logoutButton.addActionListener(e -> {
            this.view.getController().logout();
        });
        this.add(logoutButton, BorderLayout.SOUTH);
        
    }

}
