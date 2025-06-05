package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import porto.view.View;
import porto.view.utils.RequestSelectionPannel;

public class AdminScene extends JPanel {

    private final View view;
    private static final String FONT = "Roboto";

    public AdminScene(View view) {
     this.view = view;
        this.setLayout(new BorderLayout());

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        this.add(mainPanel, BorderLayout.CENTER);

        final String fullName = this.view.getController().getLoggedUser().fullName();
        final JLabel title = new JLabel("Benvenuto mio signore " + fullName, JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        final JPanel requestSelectionPanel = new RequestSelectionPannel(view);
        mainPanel.add(requestSelectionPanel);
        
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        

        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.view.getController().logout();
        });
        this.add(logoutButton, BorderLayout.SOUTH);

    }
       

}
