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
import javax.swing.table.DefaultTableModel;

import porto.data.api.PersonRole;
import porto.data.api.Starship;
import porto.view.View;

public class ShipSelectionPanel extends JPanel {

    private static final String FONT = "Roboto";
    private final View view;
    private List<Starship> ships;

    public ShipSelectionPanel(View view) {
        this.view = view;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Seleziona la nave da gestire"));
        this.setAlignmentX(CENTER_ALIGNMENT);

        this.ships = this.view.getController().getAvailableShips();
        final JTable shipTable = new SelectionTable(
            ships.stream()
                .map(ship -> new Object[]{
                    ship.plateNumber(), 
                    ship.name(), 
                    ship.model().name(),
                    ship.capitan().name() + " " + ship.capitan().surname()
                })
                .toArray(Object[][]::new),
            new String[]{"Targa", "Nome", "Modello", "Capitano"}
        );
        this.add(new JScrollPane(shipTable));
        this.add(Box.createVerticalStrut(20));

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        this.add(buttonPanel);
        buttonPanel.add(Box.createHorizontalGlue());
        final JButton createShipButton = new JButton("Crea nuova nave");
        createShipButton.setFont(new Font(FONT, Font.BOLD, 16));
        createShipButton.setToolTipText("Crea una nuova nave");
        createShipButton.setAlignmentX(CENTER_ALIGNMENT);
        createShipButton.addActionListener(e -> {
            ShipCreationDialog dialog = new ShipCreationDialog(this.view, "Crea nuova nave");
            dialog.setVisible(true);
        });
        buttonPanel.add(createShipButton);
        buttonPanel.add(Box.createHorizontalStrut(20));

        final JButton refreshButton = new JButton("Aggiorna elenco navi");
        refreshButton.setFont(new Font(FONT, Font.BOLD, 16));
        refreshButton.setToolTipText("Aggiorna l'elenco delle navi disponibili");
        refreshButton.setAlignmentX(CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> {
            this.refreshShips();
            shipTable.setModel(
                new DefaultTableModel(
                    ships.stream()
                        .map(ship -> new Object[]{
                            ship.plateNumber(), 
                            ship.name(), 
                            ship.model().name(),
                            ship.capitan().name() + " " + ship.capitan().surname()
                        })
                        .toArray(Object[][]::new),
                    new String[]{"Targa", "Nome", "Modello", "Capitano"}
                )
            );
        });
        
        final JButton manageShipButton = new JButton("Gestisci nave selezionata");
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
        buttonPanel.add(refreshButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(manageShipButton);
        buttonPanel.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalStrut(20));

        createShipButton.setVisible(!(this.view.getController().getLoggedUser().role() == PersonRole.CREW_MEMBER));
    }

    public void refreshShips() {
        this.ships = this.view.getController().getAvailableShips();
    }

}
