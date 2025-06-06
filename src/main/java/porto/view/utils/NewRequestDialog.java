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
        this.setSize(800, 500);
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));
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
        final JLabel purposeInput = cf.createFieldLabel("Scopo della richiesta");
        contentPanel.add(purposeInput);
        final String[] purposes = this.view.getController().getPurposeChoices();
        final JComboBox<String> purposeComboBox = cf.createSelectionBox(purposes);
        contentPanel.add(purposeComboBox);
        contentPanel.add(Box.createVerticalStrut(20));

        // Planet selection
        final JLabel planetInput = cf.createFieldLabel("Pianeta di " + (isAccessRequest ? "provenienze" : "destinazione"));
        contentPanel.add(planetInput);
        final String[] planets = this.view.getController().getPlanetChoices();
        final JComboBox<String> planetComboBox = cf.createSelectionBox(planets);
        contentPanel.add(planetComboBox);
        contentPanel.add(Box.createVerticalStrut(20));

        // Payload input
        // TODO: implement payload input logic
        

        // Send button
        final JButton sendButton = new JButton("Invia richiesta");
        sendButton.setAlignmentX(CENTER_ALIGNMENT);
        sendButton.setFont(new Font(FONT, Font.BOLD, 20));
        sendButton.setMaximumSize(new Dimension(300, 40));
        sendButton.addActionListener(e -> {
            // TODO: implement request sending logic
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

    private void clearInput(JTextField cuiInput) {
        cuiInput.setText("");
        this.infoLabel.setVisible(false);
        this.infoLabel.setText("");
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