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
import porto.data.api.Starship;
import porto.view.View;

public class RequestSelectionPannel extends JPanel {

    private static final String FONT = "Roboto";
    private final View view;

    public RequestSelectionPannel(View view) {
        this.view = view;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Seleziona la nave da gestire"));
        this.setAlignmentX(CENTER_ALIGNMENT);

        final List<Request> request = this.view.getController().getAllPendentRequest();
        final JTable requestTable = new JTable(
                request.stream()
                        .map(req -> new Object[] {
                                req.codRichiesta(),
                                req.description(),
                                req.dateTime(),
                                req.starship().name(),
                                req.departurePlanet().name(),
                                req.destinationPlanet()
                        })
                        .toArray(Object[][]::new),
                new String[] { "CodRichiesta", "Descrizione", "Data Richiesta", "Astronave", "Pianeta Provenienza",
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
                String requestNumber = (String) requestTable.getValueAt(selectedRow, 0);
                this.view.getController().manageShip(requestNumber);
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
        this.add(manageShipButton);
        this.add(Box.createVerticalStrut(20));
    }

}
