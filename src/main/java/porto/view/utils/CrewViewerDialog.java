package porto.view.utils;

import java.awt.Font;
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
import javax.swing.table.DefaultTableModel;

import porto.data.api.Person;
import porto.view.View;

public class CrewViewerDialog extends JDialog {

    private static final String FONT = "Roboto";

    private final View view;

    public CrewViewerDialog(View view, String title, boolean removeMode) {
        super(view.getMainFrame(), title, ModalityType.APPLICATION_MODAL);
        this.view = view;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 500);
        this.setMaximumSize(this.getSize());
        this.setLocationRelativeTo(view.getMainFrame());
        this.setResizable(true);
        
        final JPanel crewPanel = new JPanel();
        crewPanel.setLayout(new BoxLayout(crewPanel, BoxLayout.Y_AXIS));
        crewPanel.setBorder(BorderFactory.createTitledBorder(removeMode 
            ? "Seleziona un membro dell'equipaggio da rimuovere" 
            : "Equipaggio della nave"));

        final List<Person> crew = view.getController().getCrewMembersOfSelectedShip();
        final JTable crewTable = new SelectionTable(
            crew.stream()
                .map(member -> new Object[]{
                    member.CUI(),
                    member.name(),
                    member.surname(),
                    member.dateOfBirth(),
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
        crewTable.getColumnModel().getColumn(0).setPreferredWidth(170);
        crewTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        crewTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        crewTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        crewTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        crewTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        crewTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        crewTable.getColumnModel().getColumn(7).setPreferredWidth(60);
        crewTable.getColumnModel().getColumn(8).setPreferredWidth(60);

        this.add(new JScrollPane(crewTable));
        crewPanel.add(crewTable.getTableHeader());
        crewPanel.add(crewTable);
        crewPanel.add(Box.createVerticalStrut(20));

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

        final JButton refreshButton = new JButton("Aggiorna");
        refreshButton.setFont(new Font(FONT, Font.BOLD, 16));
        refreshButton.setToolTipText("Aggiorna la lista dei membri dell'equipaggio");
        refreshButton.setAlignmentX(CENTER_ALIGNMENT);
        refreshButton.addActionListener(e -> this.refreshCrew(crewTable));
        buttonPanel.add(refreshButton);

        if (removeMode) {
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
                    this.refreshCrew(crewTable);
                } else {
                    throw new IllegalStateException("No crew member selected for management");
                }
            });
            crewTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    removeButton.setEnabled(crewTable.getSelectedRow() >= 0);
                }
            });
            removeButton.setAlignmentX(CENTER_ALIGNMENT);
            buttonPanel.add(Box.createHorizontalGlue());
            buttonPanel.add(removeButton);
        } else {
            crewTable.setRowSelectionAllowed(false);
            crewTable.setColumnSelectionAllowed(false);
            crewTable.setEnabled(false);
        }

        crewPanel.add(buttonPanel);
        crewPanel.add(Box.createVerticalStrut(20));

        this.add(crewPanel);

        this.pack();
    }

    public void refreshCrew(JTable table) {
        List<Person> crew = view.getController().getCrewMembersOfSelectedShip();
        table.setModel(
            new DefaultTableModel(
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
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            }
        );
        table.getColumnModel().getColumn(0).setPreferredWidth(170);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(60);
        table.getColumnModel().getColumn(8).setPreferredWidth(60);
    }

}
