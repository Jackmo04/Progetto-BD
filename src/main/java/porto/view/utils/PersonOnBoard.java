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
import javax.swing.JTextField;

import porto.data.api.Person;
import porto.view.View;

public class PersonOnBoard extends JPanel {

    private static final String FONT = "Roboto";
    private final View view;

    public PersonOnBoard(View view) {
        this.view = view;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Seleziona la persona da gestire"));
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setSize(500, 300);
        final List<Person> people = this.view.getController().getPeopleOnStation();

        final JTextField totalPeople = new JTextField("Numero di perone totali:" + view.getController().seeNumberPeople());
        totalPeople.setEditable(false);


        final JTable personTable = new JTable(
                people.stream()
                        .map(pers -> new Object[] {
                                pers.CUI(),
                                pers.fullName(),
                                pers.ideology().toString(),
                                pers.isWanted().toString(),
                                pers.birthPlanet().name()
                        })
                        .toArray(Object[][]::new),
                new String[] { "CUI", "Nome", "Ideologia", "Ricercato", "Pianeta Nascita",
                });
        personTable.setFont(new Font(FONT, Font.PLAIN, 16));
        personTable.setRowHeight(30);
        personTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        this.add(totalPeople);
        this.add(new JScrollPane(personTable));
        this.add(Box.createVerticalStrut(20));

        final JButton arrestPeopleButtom = new JButton("Arresta");
        arrestPeopleButtom.setEnabled(false);
        arrestPeopleButtom.setFont(new Font(FONT, Font.BOLD, 16));
        arrestPeopleButtom.setToolTipText("Seleziona una persona per gestirla");
        arrestPeopleButtom.setAlignmentX(CENTER_ALIGNMENT);
        arrestPeopleButtom.addActionListener(e -> {
            int selectedRow = personTable.getSelectedRow();
            if (selectedRow >= 0) {
                String CUINumber = (String) personTable.getValueAt(selectedRow, 0);
                this.view.getController().arrestPerson(CUINumber);
            } else {
                throw new IllegalStateException("No request selected for management");
            }
            view.goToAdminScene();
        });
        
        personTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                arrestPeopleButtom.setEnabled(personTable.getSelectedRow() >= 0);
            }
        });
        
        arrestPeopleButtom.setAlignmentX(CENTER_ALIGNMENT);
        this.add(arrestPeopleButtom);
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
