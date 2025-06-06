package porto.view.utils;

import java.awt.Font;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import porto.data.api.Person;
import porto.data.api.Request;
import porto.view.View;

public class CrewSelectorDialog extends JDialog {

    private static final String FONT = "Roboto";

    private final View view;

    public CrewSelectorDialog(View view, String title) {
        super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
        this.view = view;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 500);
        this.setMaximumSize(this.getSize());
        this.setLocationRelativeTo(view.getMainFrame());
        this.setResizable(true);
        
        final JPanel crewPanel = new JPanel();
        crewPanel.setLayout(new BoxLayout(crewPanel, BoxLayout.Y_AXIS));
        crewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        final List<Person> crew = view.getController().getCrewMembersOfSelectedShip();
        final JTable crewTable = new SelectionTable(
            crew.stream()
                .map(member -> new Object[]{
                    member.CUI(),
                    member.name(),
                    member.surname(),
                    member.dateOfBirth().toString(),
                    member.race(),
                    member.birthPlanet().name(),
                    member.ideology().toString(),
                    member.isWanted() ? "Sì" : "No",
                    view.getController().isArrested(member.CUI()) ? "Sì" : "No"
                })
                .toArray(Object[][]::new),
            new String[]{
                "CUI", "Nome", "Cognome", "Data di Nascita", "Razza", 
                "Pianeta di Nascita", "Ideologia", "Ricercato", "Arrestato"
            }
        );
        // crewTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        // crewTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        // crewTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        // crewTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        // crewTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        // crewTable.getColumnModel().getColumn(5).setPreferredWidth(60);
        // crewTable.getColumnModel().getColumn(6).setPreferredWidth(130);
        // crewTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        // crewTable.getColumnModel().getColumn(8).setPreferredWidth(100);

        this.add(new JScrollPane(crewTable));
        crewPanel.add(crewTable.getTableHeader());
        crewPanel.add(crewTable);

        final JButton removeButton = new JButton("Rimuovi membro");
        removeButton.setEnabled(false);
        removeButton.setFont(new Font(FONT, Font.BOLD, 16));
        removeButton.setToolTipText("Seleziona un membro dell'equipaggio per rimuoverlo");
        removeButton.setAlignmentX(CENTER_ALIGNMENT);
        removeButton.addActionListener(e -> {
            int selectedRow = crewTable.getSelectedRow();
            if (selectedRow >= 0) {
                String cui = (String) crewTable.getValueAt(selectedRow, 0);
                this.view.getController().removeCrewMemberFromSelectedShip(cui);
            } else {
                throw new IllegalStateException("No ship selected for management");
            }
        });
        crewTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                removeButton.setEnabled(crewTable.getSelectedRow() >= 0);
            }
        });
        removeButton.setAlignmentX(CENTER_ALIGNMENT);

        this.add(crewPanel);

        this.pack();
    }

}
