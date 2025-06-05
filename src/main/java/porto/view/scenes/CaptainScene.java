package porto.view.scenes;

import javax.swing.JLabel;
import javax.swing.JPanel;

import porto.view.View;

public class CaptainScene extends JPanel {

    private final View view;

    public CaptainScene(View view) {
        this.view = view;

        // TODO: Replace with actual content
        this.add(new JLabel("Captain Scene")); 
        this.add(new JLabel("Nave: " + this.view.getController().getSelectedStarship().plateNumber()));
    }

    // TODO Implement the Captain scene

}
