package porto.view.scenes;

import javax.swing.JButton;
import javax.swing.JPanel;

import porto.view.Scene;
import porto.view.View;

public class LoginScene implements Scene {

    private final JPanel panel;
    private final String sceneName = "login";
    private final View view;

    public LoginScene(View view) {
        this.view = view;
        this.panel = new JPanel();
        this.panel.add(new JButton("Login")); // Placeholder for login button
    }

    @Override
    public JPanel getPanel() {
        return this.panel;
    }

    @Override
    public String getSceneName() {
        return this.sceneName;
    }

}
