package porto.view.scenes;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import porto.view.View;

public class LoginScene extends JPanel {

    private static final String FONT = "Roboto";
    private static final int FONT_SIZE_TITLE = 50;
    private static final String NAME_MESSAGE = "Inserisci CUI o Username";

    private final View view;

    public LoginScene(View view) {
        this.view = view;
        
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);

        final JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_TITLE));
        mainPanel.add(title);

        final JTextField input = new JTextField(NAME_MESSAGE);
        input.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                if (NAME_MESSAGE.equals(input.getText())) {
                    input.setText(""); 
                }
            }
            @Override
            public void focusLost(final FocusEvent e) {
                if (input.getText().isEmpty()) {
                    input.setText(NAME_MESSAGE);
                }
            }
        });
        input.setColumns(20);
        mainPanel.add(input);

    }

    public void onLoginButtonClick(String username, String password) {
        this.view.getController().userClickedLogin(username, password);
    }

    /**
     * Displays an error message on the login panel.
     * This method should be called when the login fails.
     * @param message The error message to display.
     */
    public void displayError(String message) {
        // TODO: Implement error display logic
    }

}
