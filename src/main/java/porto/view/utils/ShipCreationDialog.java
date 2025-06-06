package porto.view.utils;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import porto.view.View;

public class ShipCreationDialog extends JDialog {

    private static final String FONT = "Roboto";
    private static final JComponentsFactory cf = new JComponentsFactory();

    private final View view;
    private final JLabel errorLabel;
    private final FocusAdapter focusListener;

    public ShipCreationDialog(View view, String title) {
        super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
        this.view = view;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 500);
        this.setMaximumSize(this.getSize());
        this.setLocationRelativeTo(view.getMainFrame());
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        this.errorLabel = new JLabel("", SwingConstants.CENTER);
        this.errorLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        this.errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.errorLabel.setForeground(Color.RED);
        this.errorLabel.setVisible(false);

        this.focusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> errorLabel.setVisible(false));
            }
        };

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(mainPanel, BorderLayout.CENTER);

        final JLabel titleLabel = new JLabel("Nuova astronave", JLabel.CENTER);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Plate number field
        final JLabel plate = cf.createFieldLabel("Targa* (8 car.)");
        contentPanel.add(plate);
        final JTextField plateInput = cf.createFieldInput(8, focusListener);
        contentPanel.add(plateInput);
        contentPanel.add(Box.createVerticalStrut(10));

        // Name field
        final JLabel name = cf.createFieldLabel("Nome* (max 20 car.)");
        contentPanel.add(name);
        final JTextField nameInput = cf.createFieldInput(20, focusListener);
        contentPanel.add(nameInput);
        contentPanel.add(Box.createVerticalStrut(10));

        // Model field
        final JLabel model = cf.createFieldLabel("Modello*");
        contentPanel.add(model);
        final String[] models = this.view.getController().getModelChoices();
        final JComboBox<String> modelInput = cf.createSelectionBox(models);
        contentPanel.add(modelInput);
        contentPanel.add(Box.createVerticalStrut(10));

        // Register button
        final JButton registerButton = new JButton("Registra astronave");
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        registerButton.setFont(new Font(FONT, Font.BOLD, 20));
        registerButton.setMaximumSize(new Dimension(300, 40));
        registerButton.addActionListener(e -> {
            String plateNumber = plateInput.getText().trim();
            String shipName = nameInput.getText().trim();
            String shipModelCode = (String) modelInput.getSelectedItem();

            if (plateNumber.isEmpty() || shipName.isEmpty() || shipModelCode == null) {
                errorLabel.setText("Tutti i campi contrassegnati con * sono obbligatori.");
                errorLabel.setVisible(true);
                return;
            }
            if (plateNumber.length() != plateInput.getColumns()) {
                displayShipRegisterError("La targa deve essere lunga esattamente " + plateInput.getColumns() + " caratteri.");
                return;
            }
            if (shipName.length() > nameInput.getColumns()) {
                displayShipRegisterError("Il nome non può superare i " + nameInput.getColumns() + " caratteri.");
                return;
            }
            if (!shipName.matches("[a-zA-Z0-9 ]+")) {
                displayShipRegisterError("Il nome può contenere solo lettere, numeri e spazi.");
                return;
            }
            if (!plateNumber.matches("[a-zA-Z0-9]+")) {
                displayShipRegisterError("La targa può contenere solo lettere e numeri.");
                return;
            }

            if(view.getController().isStarshipRegistered(plateNumber)) {
                displayShipRegisterError("Una nave con questa targa è già registrata.");
                return;
            }
            if (view.getController().registerStarshipToCurrentUser(plateNumber, shipName, shipModelCode)) {
                clearForm(plateInput, nameInput, modelInput);
                this.dispose();
            } else {
                displayShipRegisterError("Errore durante la registrazione della nave.");
            }
        });
        contentPanel.add(registerButton);
        contentPanel.add(Box.createVerticalStrut(20));

        contentPanel.add(this.errorLabel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

    }

    private void clearForm(JTextField plateInput, JTextField nameInput, JComboBox<String> modelInput) {
        plateInput.setText("");
        nameInput.setText("");
        modelInput.setSelectedIndex(0);
        this.errorLabel.setVisible(false);
    }

    public void displayShipRegisterError(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Error message cannot be null or empty");
        }
        this.errorLabel.setText(string);
        this.errorLabel.setVisible(true);
        SwingUtilities.invokeLater(() -> this.errorLabel.scrollRectToVisible(this.errorLabel.getBounds()));
    }
}
