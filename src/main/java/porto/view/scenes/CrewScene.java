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
import porto.view.utils.RequestViewerDialog;

public class CrewScene extends JPanel {

    private static final String FONT = "Roboto";

    private final View view;

    public CrewScene(View view) {
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

        final JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        requestsPanel.setBorder(BorderFactory.createTitledBorder("Gestione richieste"));
        requestsPanel.setAlignmentX(CENTER_ALIGNMENT);
        requestsPanel.setMaximumSize(new Dimension(400, 100));
        mainPanel.add(requestsPanel);

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
        mainPanel.add(Box.createVerticalStrut(20));

        final JButton backButton = new JButton("Torna indietro");
        backButton.setFont(new Font(FONT, Font.BOLD, 16));
        backButton.addActionListener(e -> {
            this.view.goToWelcomeScene();
        });
        this.add(backButton, BorderLayout.SOUTH);
    }
}
