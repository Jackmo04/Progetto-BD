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

import porto.data.api.Starship;
import porto.view.View;

public class ShipSelectionPanel extends JPanel {

    private static final String FONT = "Roboto";
    private final View view;

    public ShipSelectionPanel(View view) {
        this.view = view;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Seleziona la nave da gestire"));
        this.setAlignmentX(CENTER_ALIGNMENT);

        final List<Starship> ships = this.view.getController().getAvailableShips();
        final JTable shipTable = new JTable(
            ships.stream()
                .map(ship -> new Object[]{ship.plateNumber(), ship.name(), ship.model().name()})
                .toArray(Object[][]::new),
            new String[]{"Targa", "Nome", "Modello"}
        );
        shipTable.setFont(new Font(FONT, Font.PLAIN, 16));
        shipTable.setRowHeight(30);
        shipTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        this.add(new JScrollPane(shipTable));
        this.add(Box.createVerticalStrut(20));
        
        final JButton manageShipButton = new JButton("Gestisci nave selzionata");
        manageShipButton.setEnabled(false);
        manageShipButton.setFont(new Font(FONT, Font.BOLD, 16));
        manageShipButton.setToolTipText("Seleziona una nave per gestirla");
        manageShipButton.setAlignmentX(CENTER_ALIGNMENT);
        manageShipButton.addActionListener(e -> {
            int selectedRow = shipTable.getSelectedRow();
            if (selectedRow >= 0) {
                String plateNumber = (String) shipTable.getValueAt(selectedRow, 0);
                this.view.getController().manageShip(plateNumber);
            } else {
                throw new IllegalStateException("No ship selected for management");
            }
        });
        shipTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                manageShipButton.setEnabled(shipTable.getSelectedRow() >= 0);
            }
        });
        manageShipButton.setAlignmentX(CENTER_ALIGNMENT);
        this.add(manageShipButton);
        this.add(Box.createVerticalStrut(20));
    }

}
