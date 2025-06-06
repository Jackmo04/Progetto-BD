package porto.view.utils;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import porto.data.api.Starship;
import porto.view.View;

public class StarshipOnBoard extends JPanel {

    private static final String FONT = "Roboto";
    private final View view;

    public StarshipOnBoard(View view) {
        this.view = view;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Seleziona la nave da gestire"));
        this.setAlignmentX(CENTER_ALIGNMENT);

        final List<Starship> ships = this.view.getController().getStarshipOnBoard();
        DefaultTableModel tableModel = new DefaultTableModel(
                ships.stream()
                        .map(ship -> new Object[] {
                                ship.plateNumber(),
                                ship.name(),
                                ship.model().name(),
                                ship.capitan().name() + " " + ship.capitan().surname()
                        })
                        .toArray(Object[][]::new),
                new String[] { "Targa", "Nome", "Modello", "Capitano" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        final JTable shipTable = new JTable(tableModel);
        shipTable.setFont(new Font(FONT, Font.PLAIN, 16));
        shipTable.setRowHeight(30);
        shipTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shipTable.setRowSelectionAllowed(true);
        shipTable.setColumnSelectionAllowed(false);
        this.add(new JScrollPane(shipTable));
        this.add(Box.createVerticalStrut(20));

        final JButton manageShipButton = new JButton("Vedi Equipaggio nave");
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

        JButton returnButtom = new JButton("Indietro");
        returnButtom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.goToAdminScene();
            }

        });
        returnButtom.setAlignmentX(CENTER_ALIGNMENT);
        this.add(returnButtom);
    }

}
