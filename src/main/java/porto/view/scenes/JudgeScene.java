package porto.view.scenes;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import porto.view.View;
import porto.view.utils.JComponentsFactory;

public class JudgeScene extends JPanel{

    private final View view;
    private static final String FONT = "Roboto";
    private static JButton buttonAccept ;
    private static JButton buttonReject ;
    private static final JComponentsFactory cf = new JComponentsFactory();

    public JudgeScene(View view) {
        this.view = view;
        this.setLayout(new BorderLayout());
        var boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, ABORT));
        this.add(boxLayout, BorderLayout.CENTER);

        // Area selection
        final JLabel ideologyLabel = cf.createFieldLabel("Area*");
        boxLayout.add(ideologyLabel);
        final String[] ideologies = {"Neutrale", "Imperiale", "Ribelle"};
        final JComboBox<String> ideologyInput = cf.createSelectionBox(ideologies);
        boxLayout.add(ideologyInput);
        boxLayout.add(Box.createVerticalStrut(20));

        // Set the title label
        JLabel titleLabel = new JLabel("Giudica Richiesta di Porto", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font(FONT, java.awt.Font.BOLD, 24));
        this.add(titleLabel , BorderLayout.NORTH);

        // Add content to the scene
        var


        // TODO: Replace with actual content
        this.add(new JLabel("Captain Scene"));
        this.add(new JLabel("Nave: " + this.view.getController().getSelectedStarship().plateNumber()));
    }
}
