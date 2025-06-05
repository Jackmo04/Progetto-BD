package porto.view.scenes;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import porto.data.api.Starship;
import porto.view.View;

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

        final String userName = this.view.getController().getLoggedUser().username();
        final JLabel title = new JLabel("Benvenuto, " + userName, JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        final JPanel shipSelectionPanel = new JPanel();
        shipSelectionPanel.setLayout(new BoxLayout(shipSelectionPanel, BoxLayout.Y_AXIS));
        shipSelectionPanel.setBorder(BorderFactory.createTitledBorder("Seleziona la nave da gestire"));
        shipSelectionPanel.setAlignmentX(CENTER_ALIGNMENT);

        final List<Starship> ships = this.view.getController().getAvailableShips();
        final JTable shipTable = new JTable(
            ships.stream()
                .map(ship -> new Object[]{ship.plateNumber(), ship.name(), ship.model().name()})
                .toArray(Object[][]::new),
            new String[]{"Targa", "Nome", "Modello"}
        );
        shipTable.setFont(new Font(FONT, Font.PLAIN, 16));
        shipTable.setRowHeight(30);
        shipSelectionPanel.add(new JScrollPane(shipTable));


        // for (Starship ship : ships) {
        //     JRadioButton shipEntry = new JRadioButton(ship.toString());
        //     shipEntry.setActionCommand(ship.plateNumber());
        //     shipEntry.setAlignmentX(CENTER_ALIGNMENT);
        //     shipEntry.setFont(new Font(FONT, Font.PLAIN, 20));
        //     shipSelectionPanel.add(shipEntry);
        // }
        shipSelectionPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(shipSelectionPanel);
        mainPanel.add(Box.createVerticalGlue());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.view.getController().logout();
        });
        this.add(logoutButton, BorderLayout.SOUTH);
        
    }

    // TODO Implement the Crew scene
}
