package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import porto.view.View;
import porto.view.utils.BGJPanel;

public class LoginScene extends JPanel {

    private static final String FONT = "Roboto";
    private static final Color FONT_COLOR = Color.WHITE;

    private final View view;
    private final JLabel errorLabel;

    public LoginScene(View view) {
        this.view = view;
        this.setLayout(new BorderLayout());

        final JPanel backgroundPanel = new BGJPanel(ClassLoader.getSystemResource("images/ds.jpg"));
        backgroundPanel.setLayout(new BorderLayout());
        this.add(backgroundPanel, BorderLayout.CENTER);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 50));
        mainPanel.setOpaque(false);
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);

        final JLabel title = new JLabel("Stazione spaziale Morte Nera", JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 50));
        title.setForeground(FONT_COLOR);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));

        final JPanel intermediate = new JPanel();
        intermediate.setOpaque(false);
        intermediate.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(intermediate);

        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(true);
        formPanel.setBackground(new Color(14, 14, 14));
        intermediate.add(formPanel);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setMaximumSize(new Dimension(400, 500));

        final JLabel subtitle = new JLabel("Login", JLabel.CENTER);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setFont(new Font(FONT, Font.BOLD, 32));
        subtitle.setForeground(FONT_COLOR);
        formPanel.add(subtitle);
        formPanel.add(Box.createVerticalStrut(20));

        final JLabel usernameLabel = new JLabel("CUI / Username", JLabel.CENTER);
        usernameLabel.setAlignmentX(CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font(FONT, Font.PLAIN, 20));
        usernameLabel.setForeground(FONT_COLOR);
        formPanel.add(usernameLabel);

        final JTextField usernameInput = new JTextField(20);
        usernameInput.setFont(new Font(FONT, Font.PLAIN, 20));
        usernameInput.setMaximumSize(new Dimension(300, 40));
        formPanel.add(usernameInput);
        usernameInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> errorLabel.setVisible(false));
            }
        });

        formPanel.add(Box.createVerticalStrut(20));

        final JLabel passwordLabel = new JLabel("Password", JLabel.CENTER);
        passwordLabel.setAlignmentX(CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font(FONT, Font.PLAIN, 20));
        passwordLabel.setForeground(FONT_COLOR);
        formPanel.add(passwordLabel);

        final JPasswordField passwordInput = new JPasswordField(20);
        passwordInput.setEchoChar('*');
        passwordInput.setFont(new Font(FONT, Font.PLAIN, 20));
        passwordInput.setMaximumSize(new Dimension(300, 40));
        formPanel.add(passwordInput);
        passwordInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> errorLabel.setVisible(false));
            }
        });

        formPanel.add(Box.createVerticalStrut(30));

        this.errorLabel = new JLabel("", SwingConstants.CENTER);
        this.errorLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        this.errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.errorLabel.setForeground(Color.RED);
        this.errorLabel.setVisible(false);

        final JButton loginButton = new JButton("Accedi");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.setFont(new Font(FONT, Font.BOLD, 20));
        loginButton.setMaximumSize(new Dimension(300, 40));
        loginButton.addActionListener(e -> {
            this.errorLabel.setVisible(false);
            String username = usernameInput.getText().trim();
            if (username.length() > 20) {
                username = username.substring(0, 20);
            }
            String password = new String(passwordInput.getPassword()).trim();
            if (password.length() > 20) {
                password = password.substring(0, 20);
            }
            if (username.isEmpty() || password.isEmpty()) {
                this.errorLabel.setText("Inserisci CUI/Username e password!");
                this.errorLabel.setVisible(true);
                return;
            }
            var isSuccessful = onLoginButtonClick(username, password);
            if (isSuccessful) {
                this.errorLabel.setVisible(false);
                usernameInput.setText("");
                passwordInput.setText("");
            }
        });
        formPanel.add(loginButton);

        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(this.errorLabel);

        formPanel.add(Box.createVerticalStrut(30));

        final JLabel registerLabel = new JLabel("Non hai un account?", SwingConstants.CENTER);
        registerLabel.setAlignmentX(CENTER_ALIGNMENT);
        registerLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        registerLabel.setForeground(FONT_COLOR);
        formPanel.add(registerLabel);
        formPanel.add(Box.createVerticalStrut(10));

        final JButton registerButton = new JButton("Registrati");
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        registerButton.setFont(new Font(FONT, Font.BOLD, 20));
        registerButton.setMaximumSize(new Dimension(300, 40));
        registerButton.addActionListener(e -> {
            this.errorLabel.setVisible(false);
            this.view.getController().userClickedRegisterOnLogin();
        });
        formPanel.add(registerButton);
        formPanel.add(Box.createVerticalStrut(20));
        
    }

    public boolean onLoginButtonClick(String username, String password) {
        return this.view.getController().userClickedLogin(username, password);
    }

    /**
     * Displays an error message on the login panel.
     * This method should be called when the login fails.
     * @param message The error message to display.
     */
    public void displayLoginFail(String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        this.errorLabel.setText(message);
        this.errorLabel.setVisible(true);
    }

}
