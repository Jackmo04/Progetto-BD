package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import porto.data.api.ParkingSpace;
import porto.view.View;
import porto.view.utils.CrewAddDialog;
import porto.view.utils.CrewRemoverDialog;
import porto.view.utils.RequestViewerDialog;

public class CaptainScene extends JPanel {

    private static final String FONT = "Roboto";

    private final View view;

    public CaptainScene(View view) {
        this.view = view;

        this.setLayout(new BorderLayout());

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        final JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
        this.add(scrollPane, BorderLayout.CENTER);

        final JLabel title = new JLabel("", JLabel.CENTER);
        title.setText("Operazioni su " + this.view.getController().getSelectedStarship().plateNumber() + ":");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        // S4 - Show parking space of the starship
        final JButton parkingButton = new JButton("Visualizza posteggio");
        parkingButton.setFont(new Font(FONT, Font.BOLD, 16));
        parkingButton.setAlignmentX(CENTER_ALIGNMENT);
        parkingButton.addActionListener(e -> {
            var parkingSpace = this.view.getController().getParkingOfSelectedStarship();
            JOptionPane.showMessageDialog(this, 
                parkingSpace.map(ParkingSpace::toString).orElse("La nave non è sulla stazione"),
                "Posteggio di " + this.view.getController().getSelectedStarship().plateNumber(),
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        mainPanel.add(parkingButton);
        mainPanel.add(Box.createVerticalStrut(20));

        // S5 - Show last request of the starship
        final JButton lastRequestButton = new JButton("Visualizza ultima richiesta effettuata");
        lastRequestButton.setFont(new Font(FONT, Font.BOLD, 16));
        lastRequestButton.setAlignmentX(CENTER_ALIGNMENT);
        lastRequestButton.addActionListener(e -> {
            var lastRequest = this.view.getController().getLastRequestOfSelectedStarship();
            if (lastRequest.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "La nave non ha effettuato richieste",
                    "Ultima richiesta di " + this.view.getController().getSelectedStarship().plateNumber(),
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                new RequestViewerDialog(this.view, 
                    "Ultima richiesta di " + this.view.getController().getSelectedStarship().plateNumber(),
                    lastRequest.stream().toList(),
                    false
                ).setVisible(true);
            }
        });
        mainPanel.add(lastRequestButton);
        mainPanel.add(Box.createVerticalStrut(20));

        // C2 - Add a crew member
        final JButton crewAddButton = new JButton("Aggiungi membri all'equipaggio");
        crewAddButton.setFont(new Font(FONT, Font.BOLD, 16));
        crewAddButton.setAlignmentX(CENTER_ALIGNMENT);
        crewAddButton.addActionListener(e -> {
            new CrewAddDialog(view, "Aggiunta membri equipaggio").setVisible(true);
        });
        mainPanel.add(crewAddButton);
        mainPanel.add(Box.createVerticalStrut(20));

        // C3 - Remove a crew member
        final JButton crewRemoveButton = new JButton("Rimuovi membri dall'equipaggio");
        crewRemoveButton.setFont(new Font(FONT, Font.BOLD, 16));
        crewRemoveButton.setAlignmentX(CENTER_ALIGNMENT);
        crewRemoveButton.addActionListener(e -> {
            if (this.view.getController().getCrewMembersOfSelectedShip().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "L'equipaggio della nave è vuoto",
                    "Rimozione membri equipaggio",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                new CrewRemoverDialog(view, "Rimozione membri equipaggio").setVisible(true);
            }
        });
        mainPanel.add(crewRemoveButton);
        mainPanel.add(Box.createVerticalStrut(20));

        // C4 - View crew members
        // TODO

        // C5 - Request station access
        // TODO

        // C6 - Request station departure
        // TODO

        // C7 - View all requests
        // TODO

        final JButton backButton = new JButton("Torna indietro");
        backButton.setFont(new Font(FONT, Font.BOLD, 16));
        backButton.addActionListener(e -> {
            this.view.goToWelcomeScene();
        });
        this.add(backButton, BorderLayout.SOUTH);
    }

}
