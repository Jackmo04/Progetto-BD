package porto.view;

import javax.swing.JPanel;

public interface Scene {

    /**
     * Returns the panel of the scene.
     * @return the panel of the scene
     */
    JPanel getPanel();

    /**
     * Returns the unique name of the scene.
     * @return the unique name of the scene
     */
    String getSceneName();
}
