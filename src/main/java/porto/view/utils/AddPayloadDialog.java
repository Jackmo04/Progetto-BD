package porto.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
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

public class AddPayloadDialog extends JDialog {

    private static final String FONT = "Roboto";
    private static final JComponentsFactory cf = new JComponentsFactory();

    private final JLabel infoLabel;
    private final Map<String, Integer> payloads;

    public AddPayloadDialog(View view, String title, String[] payloadTypes) {
        super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
        this.payloads = new HashMap<>();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 500);
        this.setMaximumSize(this.getSize());
        this.setLocationRelativeTo(view.getMainFrame());
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        this.infoLabel = new JLabel("", SwingConstants.CENTER);
        this.infoLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        this.infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.infoLabel.setVisible(false);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));
        this.add(mainPanel, BorderLayout.CENTER);

        final JLabel titleLabel = new JLabel("Aggiungi carico", JLabel.CENTER);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(Box.createVerticalStrut(30));

        // Type selection
        final JLabel typeLabel = cf.createFieldLabel("Tipo di carico");
        contentPanel.add(typeLabel);
        final JComboBox<String> typeComboBox = cf.createSelectionBox(payloadTypes);
        contentPanel.add(typeComboBox);
        contentPanel.add(Box.createVerticalStrut(20));

        // Quantity input
        final JLabel quantityLabel = cf.createFieldLabel("Quantità*");
        contentPanel.add(quantityLabel);
        final JTextField quantityInput = cf.createFieldInput(10, null);
        contentPanel.add(quantityInput);
        contentPanel.add(Box.createVerticalStrut(20));

        // Add button
        final JButton addButton = new JButton("Aggiungi carico");
        addButton.setAlignmentX(CENTER_ALIGNMENT);
        addButton.setFont(new Font(FONT, Font.BOLD, 20));
        addButton.setMaximumSize(new Dimension(300, 40));
        addButton.addActionListener(e -> {
            String type = (String) typeComboBox.getSelectedItem();
            String quantityText = quantityInput.getText().trim();
            if (type != null && !quantityText.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityText);
                    if (quantity > 0) {
                        payloads.put(type, quantity);
                        typeComboBox.removeItem(type);
                        displaySuccess("Carico aggiunto: " + type + " - Quantità: " + quantity);
                        clearInput(quantityInput);
                    } else {
                        displayError("La quantità deve essere maggiore di zero.");
                    }
                } catch (NumberFormatException ex) {
                    displayError("Quantità non valida. Inserisci un numero intero.");
                }
            } else {
                displayError("Inserisci una quantità.");
            }
            SwingUtilities.invokeLater(() -> infoLabel.repaint());
            if (typeComboBox.getItemCount() == 0) {
                addButton.setEnabled(false);
                typeComboBox.setEnabled(false);
                typeComboBox.addItem("Nessun tipo disponibile");
                quantityInput.setEnabled(false);
            }
        });
        contentPanel.add(addButton);
        contentPanel.add(Box.createVerticalStrut(20));

        contentPanel.add(this.infoLabel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void displaySuccess(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Success message cannot be null or empty");
        }
        this.infoLabel.setText(string);
        this.infoLabel.setForeground(new Color(0, 128, 0));
        this.infoLabel.setVisible(true);
        SwingUtilities.invokeLater(() -> this.infoLabel.scrollRectToVisible(this.infoLabel.getBounds()));
    }

    private void clearInput(JTextField quantityInput) {
        quantityInput.setText("");
    }

    public void displayError(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Error message cannot be null or empty");
        }
        this.infoLabel.setText(string);
        this.infoLabel.setForeground(Color.RED);
        this.infoLabel.setVisible(true);
        SwingUtilities.invokeLater(() -> this.infoLabel.scrollRectToVisible(this.infoLabel.getBounds()));
    }

    public Map<String, Integer> getPayloads() {
        return new HashMap<>(this.payloads);
    }

}
