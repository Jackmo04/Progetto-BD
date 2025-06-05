package porto.view.scenes;

import javax.swing.JLabel;
import javax.swing.JPanel;

import porto.view.View;

public class CrewScene extends JPanel {

    private static final String FONT = "Roboto";

    private final View view;

    public CrewScene(View view) {
        this.view = view;
        
         // TODO: Replace with actual content
        this.add(new JLabel("Crew Scene"));
        this.add(new JLabel("Nave: " + this.view.getController().getSelectedStarship().plateNumber()));
    }
}
