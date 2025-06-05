package porto.view.scenes;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import porto.view.View;

public class AdminScene extends JPanel {

    private final View view;

    public AdminScene(View view) {
        this.view = view;
        
        this.setLayout(new BorderLayout());
        JPanel boxButtonPannel = new JPanel();
        boxButtonPannel.setLayout(new BoxLayout(boxButtonPannel, BoxLayout.Y_AXIS));

    }

    // TODO Implement the Admin scene

}
