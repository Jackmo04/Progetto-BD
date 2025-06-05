package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import porto.view.View;
import porto.view.utils.CustomComponents;
import porto.view.utils.JComponentsFactory;

public class RegisterScene extends JPanel {

    private static final String FONT = "Roboto";
    private static final JComponentsFactory cf = new JComponentsFactory();
    
    private final View view;
    private final JLabel errorLabel;
    private final FocusAdapter focusListener;

    public RegisterScene(View view) {
        this.view = view;
        this.setLayout(new BorderLayout());

        // Error label
        this.errorLabel = new JLabel("", SwingConstants.CENTER);
        this.errorLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        this.errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.errorLabel.setForeground(Color.RED);
        this.errorLabel.setVisible(false);

        // Focus listener to hide error label when input fields gain focus
        this.focusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> errorLabel.setVisible(false));
            }
        };
        
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
        final JLabel cuiLabel = cf.createFieldLabel("CUI* (13 car.)");
        mainPanel.add(cuiLabel);
        final JTextField cuiInput = cf.createFieldInput(13, focusListener);
        mainPanel.add(cuiInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Username input
        final JLabel usernameLabel = cf.createFieldLabel("Username* (max 20 car.)");
        mainPanel.add(usernameLabel);
        final JTextField usernameInput = cf.createFieldInput(20, focusListener);
        mainPanel.add(usernameInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Password input
        final JLabel passwordLabel = cf.createFieldLabel("Password* (max 20 car.)");
        mainPanel.add(passwordLabel);
        final JPasswordField passwordInput = new JPasswordField(20);
        passwordInput.setEchoChar('*');
        passwordInput.setFont(new Font(FONT, Font.PLAIN, 16));
        passwordInput.setMaximumSize(new Dimension(300, 40));
        mainPanel.add(passwordInput);
        passwordInput.addFocusListener(focusListener);
        mainPanel.add(Box.createVerticalStrut(10));

        // Name input
        final JLabel nameLabel = cf.createFieldLabel("Nome* (max 25 car.)");
        mainPanel.add(nameLabel);
        final JTextField nameInput = cf.createFieldInput(25, focusListener);
        mainPanel.add(nameInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Surname input
        final JLabel surnameLabel = cf.createFieldLabel("Cognome* (max 25 car.)");
        mainPanel.add(surnameLabel);
        final JTextField surnameInput = cf.createFieldInput(25, focusListener);
        mainPanel.add(surnameInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Race input
        final JLabel raceLabel = cf.createFieldLabel("Razza* (max 20 car.)");
        mainPanel.add(raceLabel);
        final JTextField raceInput = cf.createFieldInput(20, focusListener);
        mainPanel.add(raceInput);
        mainPanel.add(Box.createVerticalStrut(10));

        // Date of birth input
        final JLabel dobLabel = cf.createFieldLabel("Data di nascita* (gg/mm/aaaa)");
        mainPanel.add(dobLabel);
        final JTextField dobInput = cf.createFieldInput(10, focusListener);
        dobInput.setToolTipText("Formato: gg/mm/aaaa");
        mainPanel.add(dobInput);
        mainPanel.add(Box.createVerticalStrut(20));

        // Wanted checkbox
        final CustomComponents.CheckBoxPanel wantedCBPanel = cf.createCheckBoxPanel("Ricercato*");
        mainPanel.add(wantedCBPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Ideology selection
        final JLabel ideologyLabel = cf.createFieldLabel("Ideologia*");
        mainPanel.add(ideologyLabel);
        final String[] ideologies = {"Neutrale", "Imperiale", "Ribelle"};
        final JComboBox<String> ideologyInput = cf.createSelectionBox(ideologies);
        mainPanel.add(ideologyInput);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Role selection
        final CustomComponents.CheckBoxPanel captainCBPanel = cf.createCheckBoxPanel("Capitano*");
        mainPanel.add(captainCBPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Planet of birth
        final JLabel planetLabel = cf.createFieldLabel("Pianeta di nascita*");
        mainPanel.add(planetLabel);
        final String[] planets = this.view.getController().getPlanetChoices();
        final JComboBox<String> planetInput = cf.createSelectionBox(planets);
        mainPanel.add(planetInput);
        mainPanel.add(Box.createVerticalStrut(10));        

        // Register button
        final JButton registerButton = new JButton("Registrati");
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        registerButton.setFont(new Font(FONT, Font.BOLD, 20));
        registerButton.setMaximumSize(new Dimension(300, 40));
        registerButton.addActionListener(e -> {
            this.errorLabel.setVisible(false);
            String cui = cuiInput.getText().trim();
            String username = usernameInput.getText().trim();
            String password = new String(passwordInput.getPassword()).trim();
            String name = nameInput.getText().trim();
            String surname = surnameInput.getText().trim();
            String race = raceInput.getText().trim();
            String dob = dobInput.getText().trim();
            boolean wanted = wantedCBPanel.checkBox().isSelected();
            String ideology = (String) ideologyInput.getSelectedItem();
            boolean isCaptain = captainCBPanel.checkBox().isSelected();
            String planet = (String) planetInput.getSelectedItem();
            if (
                cui.isEmpty() || username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() 
                || race.isEmpty() || dob.isEmpty() || ideology == null || planet == null
            ) {
                this.errorLabel.setText("Tutti i campi sono obbligatori!");
                this.errorLabel.setVisible(true);
                return;
            }
            if (cui.length() != cuiInput.getColumns()) {
                this.errorLabel.setText("CUI deve essere di " + cuiInput.getColumns() + " caratteri!");
                this.errorLabel.setVisible(true);
                return;
            }
            if (username.length() > usernameInput.getColumns()) {
                this.errorLabel.setText("Username non può superare i " + usernameInput.getColumns() + " caratteri!");
                this.errorLabel.setVisible(true);
                return;
            }
            if (password.length() > passwordInput.getColumns()) {
                this.errorLabel.setText("Password non può superare i " + passwordInput.getColumns() + " caratteri!");
                this.errorLabel.setVisible(true);
                return;
            }
            if (name.length() > nameInput.getColumns()) {
                this.errorLabel.setText("Nome non può superare i " + nameInput.getColumns() + " caratteri!");
                this.errorLabel.setVisible(true);
                return;
            }
            if (surname.length() > surnameInput.getColumns()) {
                this.errorLabel.setText("Cognome non può superare i " + surnameInput.getColumns() + " caratteri!");
                this.errorLabel.setVisible(true);
                return;
            }
            if (race.length() > raceInput.getColumns()) {
                this.errorLabel.setText("Razza non può superare i " + raceInput.getColumns() + " caratteri!");
                this.errorLabel.setVisible(true);
                return;
            }
            try {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate formattedDob = LocalDate.parse(dob, df);
                dob = Date.valueOf(formattedDob).toString();
                if (formattedDob.isAfter(LocalDate.now())) {
                    this.errorLabel.setText("La data di nascita non può essere futura!");
                    this.errorLabel.setVisible(true);
                    return;
                }
            } catch (Exception ex) {
                this.errorLabel.setText("Formato data di nascita non valido!");
                this.errorLabel.setVisible(true);
                return;
            }
            this.view.getController().userClickedRegister(
                cui, username, password, name, surname, race, dob, wanted, ideology, isCaptain, planet
            );
        });
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(registerButton);

        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(this.errorLabel);

        final JButton backButton = new JButton("Torna al login");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setFont(new Font(FONT, Font.BOLD, 20));
        backButton.setMaximumSize(new Dimension(300, 40));
        backButton.addActionListener(e -> {
            this.errorLabel.setVisible(false);
            this.view.goToLoginScene();
        });
        this.add(backButton, BorderLayout.SOUTH);

    }

    public void displayRegisterError(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Error message cannot be null or empty");
        }
        this.errorLabel.setText(string);
        this.errorLabel.setVisible(true);
    }

    // TODO Implement the Register scene

}
