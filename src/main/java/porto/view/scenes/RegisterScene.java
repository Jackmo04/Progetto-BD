package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import porto.view.View;

public class RegisterScene extends JPanel {

    private static final String FONT = "Roboto";

    private final View view;
    private final JLabel errorLabel;

    public RegisterScene(View view) {
        this.view = view;
        this.setLayout(new BorderLayout());
        
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        final JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
        this.add(scrollPane, BorderLayout.CENTER);

        final JLabel title = new JLabel("Registrazione", JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        // CUI input
        final JLabel cuiLabel = new FieldLabel("CUI (13 car.)");
        mainPanel.add(cuiLabel);
        final JTextField cuiInput = new FieldInput(13);
        mainPanel.add(cuiInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Username input
        final JLabel usernameLabel = new FieldLabel("Username (1-20 car.)");
        mainPanel.add(usernameLabel);
        final JTextField usernameInput = new FieldInput(20);
        mainPanel.add(usernameInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Password input
        final JLabel passwordLabel = new FieldLabel("Password (1-20 car.)");
        mainPanel.add(passwordLabel);
        final JPasswordField passwordInput = new JPasswordField(20);
        passwordInput.setEchoChar('*');
        passwordInput.setFont(new Font(FONT, Font.PLAIN, 16));
        passwordInput.setMaximumSize(new Dimension(300, 40));
        mainPanel.add(passwordInput);
        passwordInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> errorLabel.setVisible(false));
            }
        });
        mainPanel.add(Box.createVerticalStrut(10));

        // Name input
        final JLabel nameLabel = new FieldLabel("Nome (1-25 car.)");
        mainPanel.add(nameLabel);
        final JTextField nameInput = new FieldInput(25);
        mainPanel.add(nameInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Surname input
        final JLabel surnameLabel = new FieldLabel("Cognome (1-25 car.)");
        mainPanel.add(surnameLabel);
        final JTextField surnameInput = new FieldInput(25);
        mainPanel.add(surnameInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Race input
        final JLabel raceLabel = new FieldLabel("Razza (max 20 car.)");
        mainPanel.add(raceLabel);
        final JTextField raceInput = new FieldInput(20);
        mainPanel.add(raceInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Date of birth input
        final JLabel dobLabel = new FieldLabel("Data di nascita (gg/mm/aaaa)");
        mainPanel.add(dobLabel);
        final JTextField dobInput = new FieldInput(10);
        dobInput.setToolTipText("Formato: gg/mm/aaaa");
        mainPanel.add(dobInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Wanted checkbox
        final JPanel wantedPanel = new CheckBoxPanel("Ricercato");
        mainPanel.add(wantedPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Ideology selection
        final JLabel ideologyLabel = new FieldLabel("Ideologia");
        mainPanel.add(ideologyLabel);
        final String[] ideologies = {"Neutrale", "Imperiale", "Ribelle"};
        final JComboBox<String> ideologyInput = new SelectionBox(ideologies);
        mainPanel.add(ideologyInput);
        mainPanel.add(Box.createVerticalStrut(10));
        

        // TODO Continuare con gli altri campi




        // Error label
        this.errorLabel = new JLabel("", SwingConstants.CENTER);
        this.errorLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        this.errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.errorLabel.setForeground(Color.RED);
        this.errorLabel.setVisible(false);

        // Register button
        final JButton registerButton = new JButton("Registrati");
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        registerButton.setFont(new Font(FONT, Font.BOLD, 20));
        registerButton.setMaximumSize(new Dimension(300, 40));
        registerButton.addActionListener(e -> {
            this.errorLabel.setVisible(false);
            String username = cuiInput.getText().trim();
            String password = new String(passwordInput.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                this.errorLabel.setText("Inserisci CUI/Username e password!");
                this.errorLabel.setVisible(true);
                return;
            }
            // onLoginButtonClick(username, password);
        });
        mainPanel.add(registerButton);

        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(this.errorLabel);

        mainPanel.add(Box.createVerticalStrut(30));

    }

    // TODO Implement the Register scene

    // TODO move to a factory class or similar
    private class FieldLabel extends JLabel {
        public FieldLabel(String text) {
            super(text, JLabel.CENTER);
            this.setAlignmentX(CENTER_ALIGNMENT);
            this.setFont(new Font(FONT, Font.PLAIN, 16));
        }
    }

    private class FieldInput extends JTextField {
        public FieldInput(int columns) {
            super(13);
            this.setFont(new Font(FONT, Font.PLAIN, 16));
            this.setMaximumSize(new Dimension(300, 40));
            this.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    SwingUtilities.invokeLater(() -> errorLabel.setVisible(false));
                }
            });
        }
    }

    private class SelectionBox extends JComboBox<String> {
        public SelectionBox(String[] items) {
            super(items);
            this.setFont(new Font(FONT, Font.PLAIN, 16));
            this.setMaximumSize(new Dimension(300, 40));
            this.setSelectedIndex(0);
            this.setAlignmentX(CENTER_ALIGNMENT);
        }
    }

    private class CheckBoxPanel extends JPanel {

        private final JCheckBox checkBox;

        public CheckBoxPanel(String labelText) {
            this.setLayout(new BorderLayout());
            this.setAlignmentX(CENTER_ALIGNMENT);
            this.setMaximumSize(new Dimension(300, 40));
            final JLabel label = new FieldLabel(labelText);
            this.add(label, BorderLayout.WEST);
            this.checkBox = new JCheckBox();
            this.checkBox.setAlignmentX(CENTER_ALIGNMENT);
            this.checkBox.setFont(new Font(FONT, Font.PLAIN, 16));
            this.checkBox.setMaximumSize(new Dimension(300, 40));
            this.add(this.checkBox, BorderLayout.EAST);
        }

        public JCheckBox getCheckBox() {
            return this.checkBox;
        }
    }

}
