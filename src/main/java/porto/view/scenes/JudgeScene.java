package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import porto.view.View;
import porto.view.utils.JComponentsFactory;

public class JudgeScene extends JPanel {

    private final View view;
    private static final String FONT = "Roboto";
    private static JButton buttonAccept;
    private static JButton buttonReject;
    private static JButton returButtom ;
    private static final JComponentsFactory cf = new JComponentsFactory();

    public JudgeScene(View view, int numberRequest) {
        this.view = view;
        this.setLayout(new BorderLayout());
        var boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout , BoxLayout.Y_AXIS));
        this.add(boxLayout, BorderLayout.CENTER);

        returButtom = new JButton("Indietro");
        returButtom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.goToAdminScene();
            }
            
        });
        this.add(returButtom , BorderLayout.SOUTH);

        // Area selection
        final JLabel ideologyLabel = cf.createFieldLabel("Scegli Area Attracco");
        
        final String[] freeArea = this.view.getController().getAllFreeArea().stream().map(t -> t.name()).distinct()
                .toArray(String[]::new);
        final JComboBox<String> ideologyInput = cf.createSelectionBox(freeArea);
        ideologyInput.addItem("Nessuna");
        
        boxLayout.add(Box.createVerticalStrut(20));

        buttonAccept = cf.createButton("Accetta", new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                Optional<Integer> selectedArea;
                if (ideologyInput.getToolTipText() == "Nessuna") {
                    selectedArea = Optional.empty();
                } else {
                    selectedArea = Optional.of(ideologyInput.getSelectedIndex());
                }
                view.getController().judgePendentRequest(numberRequest, true,
                        selectedArea);
                view.goToAdminScene();

            }
        });

        buttonReject = cf.createButton("Rifiuta", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.getController().judgePendentRequest(numberRequest, false, Optional.empty());
                view.goToAdminScene();
            }

        });
        boxLayout.add(buttonAccept);
        boxLayout.add(buttonReject);
        boxLayout.add(Box.createVerticalStrut(20));
        boxLayout.add(ideologyLabel);
        boxLayout.add(ideologyInput);

        // Set the title label
        JLabel titleLabel = new JLabel("Giudica Richiesta di Porto", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font(FONT, java.awt.Font.BOLD, 24));
        this.add(titleLabel, BorderLayout.NORTH);
    }
}
