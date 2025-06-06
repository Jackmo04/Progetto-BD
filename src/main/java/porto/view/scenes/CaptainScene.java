package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import porto.view.utils.CrewViewerDialog;
import porto.view.utils.NewRequestDialog;
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

        final JPanel parkingPanel = new JPanel();
        parkingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        parkingPanel.setBorder(BorderFactory.createTitledBorder("Gestione posteggio"));
        parkingPanel.setAlignmentX(CENTER_ALIGNMENT);
        parkingPanel.setMaximumSize(new Dimension(400, 100));
        mainPanel.add(parkingPanel);

        // S4 - Show parking space of the starship
        final JButton parkingButton = new JButton("Visualizza");
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
        parkingPanel.add(parkingButton);
        mainPanel.add(Box.createVerticalStrut(20));

        final JPanel crewPanel = new JPanel();
        crewPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        crewPanel.setBorder(BorderFactory.createTitledBorder("Gestione equipaggio"));
        crewPanel.setAlignmentX(CENTER_ALIGNMENT);
        crewPanel.setMaximumSize(new Dimension(400, 100));
        mainPanel.add(crewPanel);

        // C4 - View crew members
        final JButton crewViewButton = new JButton("Visualizza");
        crewViewButton.setFont(new Font(FONT, Font.BOLD, 16));
        crewViewButton.setAlignmentX(CENTER_ALIGNMENT);
        crewViewButton.addActionListener(e -> {
            if (this.view.getController().getCrewMembersOfSelectedShip().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "L'equipaggio della nave è vuoto",
                    "Visualizzazione membri equipaggio",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                new CrewViewerDialog(view, "Visualizzazione membri equipaggio", false).setVisible(true);
            }
        });
        crewPanel.add(crewViewButton);

        // C2 - Add a crew member
        final JButton crewAddButton = new JButton("Aggiungi");
        crewAddButton.setFont(new Font(FONT, Font.BOLD, 16));
        crewAddButton.setAlignmentX(CENTER_ALIGNMENT);
        crewAddButton.addActionListener(e -> {
            new CrewAddDialog(view, "Aggiunta membri equipaggio").setVisible(true);
        });
        crewPanel.add(crewAddButton);

        // C3 - Remove a crew member
        final JButton crewRemoveButton = new JButton("Rimuovi");
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
                new CrewViewerDialog(view, "Rimozione membri equipaggio", true).setVisible(true);
            }
        });
        crewPanel.add(crewRemoveButton);
        mainPanel.add(Box.createVerticalStrut(20));

        final JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        requestsPanel.setBorder(BorderFactory.createTitledBorder("Gestione richieste"));
        requestsPanel.setAlignmentX(CENTER_ALIGNMENT);
        requestsPanel.setMaximumSize(new Dimension(400, 150));
        mainPanel.add(requestsPanel);

        // C5 - Request station access
        final JButton requestAccessButton = new JButton("Richiedi atterraggio");
        requestAccessButton.setFont(new Font(FONT, Font.BOLD, 16));
        requestAccessButton.setAlignmentX(CENTER_ALIGNMENT);
        requestAccessButton.addActionListener(e -> {
            if (this.view.getController().getParkingOfSelectedStarship().isPresent()) {
                JOptionPane.showMessageDialog(this, 
                    "La nave è già sulla stazione!",
                    "Richiesta di atterraggio sulla stazione",
                    JOptionPane.ERROR_MESSAGE
                );
            } else if (this.view.getController().hasPendingRequest()) {
                JOptionPane.showMessageDialog(this, 
                    "La nave ha già effettuato una richiesta. Attendere la risposta della stazione.",
                    "Richiesta di accesso alla stazione",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                new NewRequestDialog(this.view, 
                    "Richiesta di atterraggio sulla stazione", 
                    true
                ).setVisible(true);
            }
        });
        requestsPanel.add(requestAccessButton);

        // C6 - Request station departure
        final JButton requestDepartureButton = new JButton("Richiedi decollo");
        requestDepartureButton.setFont(new Font(FONT, Font.BOLD, 16));
        requestDepartureButton.setAlignmentX(CENTER_ALIGNMENT);
        requestDepartureButton.addActionListener(e -> {
            if (this.view.getController().getParkingOfSelectedStarship().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "La nave non è sulla stazione!",
                    "Richiesta di decoolo dalla stazione",
                    JOptionPane.ERROR_MESSAGE
                );
            } else if (this.view.getController().hasPendingRequest()) {
                JOptionPane.showMessageDialog(this, 
                    "La nave ha già effettuato una richiesta. Attendere la risposta della stazione.",
                    "Richiesta di partenza dalla stazione",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                new NewRequestDialog(this.view, 
                    "Richiesta di decollo dalla stazione", 
                    false
                ).setVisible(true);
            }
        });
        requestsPanel.add(requestDepartureButton);

        // S5 - Show last request of the starship
        final JButton lastRequestButton = new JButton("Visualizza ultima");
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
                    lastRequest.stream().toList()
                ).setVisible(true);
            }
        });
        requestsPanel.add(lastRequestButton);

        // C7 - View all requests
        final JButton requestsViewButton = new JButton("Visualizza tutte");
        requestsViewButton.setFont(new Font(FONT, Font.BOLD, 16));
        requestsViewButton.setAlignmentX(CENTER_ALIGNMENT);
        requestsViewButton.addActionListener(e -> {
            var requests = this.view.getController().getRequestsOfSelectedStarship();
            if (requests.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "La nave non ha effettuato richieste",
                    "Visualizzazione richieste di " + this.view.getController().getSelectedStarship().plateNumber(),
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                new RequestViewerDialog(this.view, 
                    "Visualizzazione richieste di " + this.view.getController().getSelectedStarship().plateNumber(),
                    requests
                ).setVisible(true);
            }
        });
        requestsPanel.add(requestsViewButton);
        mainPanel.add(Box.createVerticalStrut(20));


        final JButton backButton = new JButton("Torna indietro");
        backButton.setFont(new Font(FONT, Font.BOLD, 16));
        backButton.addActionListener(e -> {
            this.view.goToWelcomeScene();
        });
        this.add(backButton, BorderLayout.SOUTH);
    }

}
