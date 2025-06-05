package porto.view.scenes;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import porto.data.api.Starship;
import porto.view.View;
import porto.view.utils.ShipSelectionPanel;

public class CrewScene extends JPanel {

    private static final String FONT = "Roboto";

    private final View view;

    public CrewScene(View view) {
        this.view = view;
        this.setLayout(new BorderLayout());

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        this.add(mainPanel, BorderLayout.CENTER);

        final String userName = this.view.getController().getLoggedUser().username();
        final JLabel title = new JLabel("Benvenuto, " + userName, JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        final JPanel shipSelectionPanel = new ShipSelectionPanel(view);
        mainPanel.add(shipSelectionPanel);
        
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.view.getController().logout();
        });
        this.add(logoutButton, BorderLayout.SOUTH);
        
    }

    // TODO Implement the Crew scene
}
