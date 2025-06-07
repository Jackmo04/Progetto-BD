package porto.view.utils;

import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import porto.data.api.Request;
import porto.view.View;

public class RequestSelectionPanel extends JPanel {

    private static final String FONT = "Roboto";
    private final View view;

    public RequestSelectionPanel(View view) {
        this.view = view;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Seleziona la richiesta da gestire"));
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setSize(500, 300);

        final List<Request> request = this.view.getController().getAllPendentRequest();
        final JTable requestTable = new JTable(
                request.stream()
                        .map(req -> new Object[] {
                                req.codRichiesta(),
                                req.type().toString(),
                                req.description(),
                                req.dateTime(),
                                req.starship().name(),
                                req.starship().plateNumber(),
                                req.departurePlanet().name(),
                                req.destinationPlanet().name()
                        })
                        .toArray(Object[][]::new),
                new String[] { "CodRichiesta", "Tipo", "Descrizione", "Data Richiesta", "Astronave","Targa Astronave",
                        "Pianeta Provenienza",
                        "Pianeta Destinazione" });
        requestTable.setFont(new Font(FONT, Font.PLAIN, 16));
        requestTable.setRowHeight(30);
        requestTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        this.add(new JScrollPane(requestTable));
        this.add(Box.createVerticalStrut(20));

        final JButton manageShipButton = new JButton("Gestisci richiesta");
        manageShipButton.setEnabled(false);
        manageShipButton.setFont(new Font(FONT, Font.BOLD, 16));
        manageShipButton.setToolTipText("Seleziona una richiesta per gestirla");
        manageShipButton.setAlignmentX(CENTER_ALIGNMENT);
        manageShipButton.addActionListener(e -> {
            int selectedRow = requestTable.getSelectedRow();
            if (selectedRow >= 0) {
                Integer requestNumber = (Integer) requestTable.getValueAt(selectedRow, 0);
                this.view.getController().manageRequest(requestNumber);
            } else {
                throw new IllegalStateException("No request selected for management");
            }
        });
        requestTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                manageShipButton.setEnabled(requestTable.getSelectedRow() >= 0);
            }
        });
        manageShipButton.setAlignmentX(CENTER_ALIGNMENT);

        final JButton peopleOfShip = new JButton("Persone In Porto");
        peopleOfShip.setFont(new Font(FONT, Font.BOLD, 16));
        peopleOfShip.setToolTipText("Seleziona una richiesta per gestirla");
        peopleOfShip.setAlignmentX(CENTER_ALIGNMENT);
        peopleOfShip.addActionListener(e -> {
            this.view.goStarshipEquipe();
        });

        final JButton shipOnBoard = new JButton("Navi In Porto");
        shipOnBoard.setFont(new Font(FONT, Font.BOLD, 16));
        shipOnBoard.setToolTipText("Seleziona una richiesta per gestirla");
        shipOnBoard.setAlignmentX(CENTER_ALIGNMENT);
        shipOnBoard.addActionListener(e -> {
            this.view.goToShipScene();
        });
        
        
        final JButton statistic = new JButton("Statistiche");
        statistic.setFont(new Font(FONT, Font.BOLD, 16));
        //statistic.setToolTipText("Seleziona una richiesta per gestirla");
        statistic.setAlignmentX(CENTER_ALIGNMENT);
        statistic.addActionListener(e -> {
            this.view.goToStatisticScene();
        });

        manageShipButton.setAlignmentX(CENTER_ALIGNMENT);
        this.add(manageShipButton);

        peopleOfShip.setAlignmentX(CENTER_ALIGNMENT);
        this.add(peopleOfShip);

        shipOnBoard.setAlignmentX(CENTER_ALIGNMENT);
        this.add(shipOnBoard);

        statistic.setAlignmentX(CENTER_ALIGNMENT);
        this.add(statistic);

        this.add(Box.createVerticalStrut(20));
    }

}
