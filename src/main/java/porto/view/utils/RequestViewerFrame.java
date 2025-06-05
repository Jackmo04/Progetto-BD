package porto.view.utils;

import java.awt.Font;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import porto.data.api.Person;
import porto.data.api.Request;
import porto.view.View;

public class RequestViewerFrame extends JFrame {

    private static final String FONT = "Roboto";

    public RequestViewerFrame(View view, String title, List<Request> requests) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setMaximumSize(this.getSize());
        // this.setLocationRelativeTo(this.getParent());
        this.setLocationByPlatform(true);
        this.setResizable(true);

        final JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        final JTable requestsTable = new JTable(
            requests.stream()
                .map(req -> new Object[]{
                    req.codRichiesta(),
                    req.type().toString(),
                    req.dateTime().toString(),
                    req.description(),
                    req.totalPrice(),
                    view.getController().getRequestState(req).toString(),
                    view.getController().getRequestDateTimeManaged(req).map(Timestamp::toString).orElse("N/A"),
                    req.starship().plateNumber(),
                    req.purpose().name(),
                    req.departurePlanet().name(),
                    req.destinationPlanet().name(),
                    view.getController().getRequestManagedBy(req).map(Person::fullName).orElse("N/A")
                })
                .toArray(Object[][]::new),
            new String[]{
                "Codice", "Tipo", "Data/Ora", "Descrizione", "Costo", "Esito",
                "Data/Ora Gestione", "Astronave", "Scopo", "Provenienza", 
                "Destinazione", "Gestita da"
            }
        );
        requestsTable.setFont(new Font(FONT, Font.PLAIN, 12));
        requestsTable.setRowHeight(30);
        requestsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        requestsTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        requestsTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        requestsTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        requestsTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        requestsTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        requestsTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        requestsTable.getColumnModel().getColumn(7).setPreferredWidth(75);
        requestsTable.getColumnModel().getColumn(8).setPreferredWidth(150);
        requestsTable.getColumnModel().getColumn(9).setPreferredWidth(100);
        requestsTable.getColumnModel().getColumn(10).setPreferredWidth(100);
        requestsTable.getColumnModel().getColumn(11).setPreferredWidth(150);
        requestsTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(requestsTable));
        requestsPanel.add(requestsTable.getTableHeader());
        requestsPanel.add(requestsTable);
        requestsPanel.setBorder(BorderFactory.createTitledBorder("Richieste"));
        this.add(requestsPanel);

        this.pack();
        this.setVisible(true);

    }


}
