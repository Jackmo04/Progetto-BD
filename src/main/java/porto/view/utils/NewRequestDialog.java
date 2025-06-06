package porto.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
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

import porto.data.PayloadImpl;
import porto.data.api.Payload;
import porto.view.View;

public class NewRequestDialog extends JDialog {

    private static final String FONT = "Roboto";

    private final View view;
    private final JLabel infoLabel;
    private final FocusAdapter focusListener;
    private final JComponentsFactory cf = new JComponentsFactory();

    public NewRequestDialog(View view, String title, boolean isAccessRequest) {
        super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
        this.view = view;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setMaximumSize(this.getSize());
        this.setLocationRelativeTo(view.getMainFrame());
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        this.infoLabel = new JLabel("", SwingConstants.CENTER);
        this.infoLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        this.infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.infoLabel.setVisible(false);

        this.focusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> infoLabel.setVisible(false));
            }
        };

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        this.add(mainPanel, BorderLayout.CENTER);

        final JLabel titleLabel = new JLabel(
            "Nuova richiesta di " + (isAccessRequest ? "atterraggio" : "decollo"),
            JLabel.CENTER
        );
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(Box.createVerticalStrut(30));

        // Purpose selection
        final JLabel purposeLabel = cf.createFieldLabel("Scopo*");
        contentPanel.add(purposeLabel);
        final String[] purposes = this.view.getController().getPurposeChoices();
        final JComboBox<String> purposeInput = cf.createSelectionBox(purposes);
        contentPanel.add(purposeInput);
        contentPanel.add(Box.createVerticalStrut(20));

        // Planet selection
        final JLabel planetField = cf.createFieldLabel("Pianeta di " + (isAccessRequest ? "provenienza" : "destinazione") + "*");
        contentPanel.add(planetField);
        final var planets = this.view.getController().getPlanetChoices();
        planets.remove("Morte Nera");
        final JComboBox<String> planetInput = cf.createSelectionBox(planets.toArray(new String[0]));
        contentPanel.add(planetInput);
        contentPanel.add(Box.createVerticalStrut(20));

        // Description input
        final JLabel descriptionLabel = cf.createFieldLabel("Descrizione* (max 50 car.)");
        contentPanel.add(descriptionLabel);
        final JTextField descriptionInput = cf.createFieldInput(50, focusListener);
        descriptionInput.setMaximumSize(new Dimension(300, 40));
        contentPanel.add(descriptionInput);
        contentPanel.add(Box.createVerticalStrut(20));

        // Payload input
        final List<String> payloadTypes = this.view.getController().getPayloadTypeChoices();
        final JButton addPayloadButton = new JButton("Aggiungi carichi");
        addPayloadButton.setAlignmentX(CENTER_ALIGNMENT);
        addPayloadButton.setFont(new Font(FONT, Font.BOLD, 20));
        addPayloadButton.setMaximumSize(new Dimension(300, 40));
        List<Payload> payloads = new ArrayList<>();
        addPayloadButton.addActionListener(e -> {
            var dialog = new AddPayloadDialog(this.view, "Aggiungi carichi", payloadTypes.toArray(new String[0]));
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (!dialog.getPayloads().isEmpty()) {
                        dialog.getPayloads().forEach((type, quantity) -> {
                            payloadTypes.remove(type);
                            payloads.add(new PayloadImpl(view.getController().getpayloadTypeFromName(type), quantity));
                        });
                    }
                }
            });
            dialog.setVisible(true);
        });
        contentPanel.add(addPayloadButton);
        contentPanel.add(Box.createVerticalStrut(30));

        // Send button
        final JButton sendButton = new JButton("Invia richiesta");
        sendButton.setAlignmentX(CENTER_ALIGNMENT);
        sendButton.setFont(new Font(FONT, Font.BOLD, 20));
        sendButton.setMaximumSize(new Dimension(300, 40));
        sendButton.addActionListener(e -> {
            String purpose = (String) purposeInput.getSelectedItem();
            String planet = (String) planetInput.getSelectedItem();
            String description = descriptionInput.getText().trim();
            if (description.length() > descriptionInput.getColumns()) {
                this.displayError("Descrizione troppo lunga!");
                return;
            }
            if (purpose != null && planet != null && description != null && !description.isEmpty()) {
                try {
                    this.view.getController().sendRequest(description, purpose, planet, payloads, isAccessRequest);
                    this.displaySuccess("Richiesta inviata con successo!");
                    this.dispose();
                } catch (Exception ex) {
                    this.displayError("Errore durante l'invio della richiesta: " + ex.getMessage());
                }
            } else {
                this.displayError("Compila tutti i campi prima di inviare la richiesta.");
            }
        });
        contentPanel.add(sendButton);
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

    public void displayError(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Error message cannot be null or empty");
        }
        this.infoLabel.setText(string);
        this.infoLabel.setForeground(Color.RED);
        this.infoLabel.setVisible(true);
        SwingUtilities.invokeLater(() -> this.infoLabel.scrollRectToVisible(this.infoLabel.getBounds()));
    }
}