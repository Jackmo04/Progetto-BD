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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import porto.view.View;

public class CrewAddDialog extends JDialog {

    private static final String FONT = "Roboto";

    private final View view;
    private final JLabel infoLabel;
    private final FocusAdapter focusListener;
    private final JComponentsFactory cf = new JComponentsFactory();

    public CrewAddDialog(View view, String title) {
        super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
        this.view = view;
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

        final JLabel titleLabel = new JLabel("Aggiungi membri", JLabel.CENTER);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(Box.createVerticalStrut(30));

        // CUI input
        final JLabel cuiLabel = cf.createFieldLabel("CUI (persona da aggiungere)");
        contentPanel.add(cuiLabel);
        final JTextField cuiInput = cf.createFieldInput(13, focusListener);
        contentPanel.add(cuiInput);
        contentPanel.add(Box.createVerticalStrut(10));

        // Add button
        final JButton addButton = new JButton("Aggiungi");
        addButton.setAlignmentX(CENTER_ALIGNMENT);
        addButton.setFont(new Font(FONT, Font.BOLD, 20));
        addButton.setMaximumSize(new Dimension(300, 40));
        addButton.addActionListener(e -> {
            String cui = cuiInput.getText().trim();
            if (cui.isBlank()) {
                displayError("Il CUI non può essere vuoto");
                return;
            }
            try {
                if (this.view.getController().isCrewMemberOfSelectedShip(cui)) {
                    displayError("Membro già presente!");
                    return;
                } 
                if (!this.view.getController().isValidCrewMemberCUI(cui)) {
                    displayError("Il CUI inserito non è valido!");
                    return;
                }
                if (this.view.getController().addCrewMemberToSelectedShip(cui)) {
                    clearInput(cuiInput);
                    displaySuccess("Membro dell'equipaggio aggiunto con successo!");
                    return;
                }
                displayError("Errore durante l'aggiunta del membro dell'equipaggio");
            } catch (Exception ex) {
                displayError("Errore durante l'aggiunta del membro dell'equipaggio");
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
        this.infoLabel.setForeground(new Color(0, 128, 0)); // Green color
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
