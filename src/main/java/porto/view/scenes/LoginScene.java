package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import porto.view.View;

public class LoginScene extends JPanel {

    private static final String FONT = "Roboto";
    private static final int FONT_SIZE_TITLE = 50;

    private final View view;

    public LoginScene(View view) {
        this.view = view;        

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));
        this.add(mainPanel);

        final JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_TITLE));
        mainPanel.add(title);

        mainPanel.add(Box.createVerticalStrut(20));

        final JLabel usernameLabel = new JLabel("CUI / Username", JLabel.CENTER);
        usernameLabel.setAlignmentX(CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font(FONT, Font.PLAIN, 20));
        mainPanel.add(usernameLabel);

        final JTextField usernameInput = new JTextField();
        usernameInput.setColumns(20);
        usernameInput.setFont(new Font(FONT, Font.PLAIN, 20));
        usernameInput.setMaximumSize(new Dimension(300, 40));
        mainPanel.add(usernameInput);

        mainPanel.add(Box.createVerticalStrut(20));

        final JLabel passwordLabel = new JLabel("Password", JLabel.CENTER);
        passwordLabel.setAlignmentX(CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font(FONT, Font.PLAIN, 20));
        mainPanel.add(passwordLabel);

        final JTextField passwordInput = new JTextField();
        passwordInput.setColumns(20);
        passwordInput.setFont(new Font(FONT, Font.PLAIN, 20));
        passwordInput.setMaximumSize(new Dimension(300, 40));
        mainPanel.add(passwordInput);

    }

    public void onLoginButtonClick(String username, String password) {
        this.view.getController().userClickedLogin(username, password);
    }

    /**
     * Displays an error message on the login panel.
     * This method should be called when the login fails.
     * @param message The error message to display.
     */
    public void displayError(String message) {
        // TODO: Implement error display logic
    }

}
