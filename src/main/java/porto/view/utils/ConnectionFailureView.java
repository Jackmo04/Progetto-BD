package porto.view.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConnectionFailureView extends JFrame {

    public ConnectionFailureView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(this, 
            "Impossibile connettersi al database.\n" +
            "Assicurarsi che il server MySQL sia in esecuzione e che esista un database chiamato 'PortoMorteNera'.", 
            "Errore di connessione al database", 
            JOptionPane.ERROR_MESSAGE
        );
        this.dispose();
    }
    
}
