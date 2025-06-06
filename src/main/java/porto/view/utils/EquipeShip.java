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
import porto.data.api.Person;
import porto.view.View;

public class EquipeShip extends JPanel {

    private static final String FONT = "Roboto";
    private final View view;

    public EquipeShip(View view, String plate) {
        this.view = view;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Perone appartenentei alla nave: " + plate));
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setSize(500, 300);
        final List<Person> people = this.view.getController().getStarshipeEquipe(plate);

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
        this.add(new JScrollPane(personTable));
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
