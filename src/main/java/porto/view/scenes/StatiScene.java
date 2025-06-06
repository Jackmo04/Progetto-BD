package porto.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import porto.view.View;
import porto.view.utils.JComponentsFactory;

public class StatiScene extends JPanel {

    private static final String FONT = "Roboto";
    private static final JComponentsFactory cf = new JComponentsFactory();

    private final View view;
    private final JLabel infoLabel;
    private final FocusAdapter focusListener;

    public StatiScene(View view) {
        this.view = view;
        this.setLayout(new BorderLayout());

        // Info label
        this.infoLabel = new JLabel("", SwingConstants.CENTER);
        this.infoLabel.setFont(new Font(FONT, Font.PLAIN, 16));
        this.infoLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.infoLabel.setForeground(Color.RED);
        this.infoLabel.setVisible(false);

        // Focus listener to hide error label when input fields gain focus
        this.focusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> infoLabel.setVisible(false));
            }
        };

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        final JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
        this.add(scrollPane, BorderLayout.CENTER);

        final JLabel title = new JLabel("Registrazione", JLabel.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font(FONT, Font.BOLD, 30));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        // CUI input
        final JLabel cuiLabel = cf.createFieldLabel("Migliori 50 navi che hanno trasportato di piu");
        mainPanel.add(cuiLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        final JButton bestNavi = new JButton("50 Navi Che Hanno Trasportato di piu");
        bestNavi.setAlignmentX(CENTER_ALIGNMENT);
        bestNavi.setFont(new Font(FONT, Font.BOLD, 20));
        bestNavi.setMaximumSize(new Dimension(300, 40));
        bestNavi.addActionListener(e -> {
            this.view.goToBest50ShipScene();
        });
        mainPanel.add(bestNavi);

                final JLabel titleRequest = cf
                .createFieldLabel("Analisi Richieste Accetate e rifiutate in un intervallo di tempo");
        mainPanel.add(titleRequest);
        mainPanel.add(Box.createVerticalStrut(10));


        // Best 50 Starship
        final JLabel dobStartLabel = cf.createFieldLabel("Data Inizio Analisi");
        mainPanel.add(dobStartLabel);
        final JTextField dobInput = cf.createFieldInput(10, focusListener);
        dobInput.setToolTipText("Formato: gg/mm/aaaa");
        mainPanel.add(dobInput);
        mainPanel.add(Box.createVerticalStrut(20));


        final JLabel dobFinalLabel = cf.createFieldLabel("Data Inizio Analisi");
        mainPanel.add(dobFinalLabel);
        final JTextField dobFinalInput = cf.createFieldInput(10, focusListener);
        dobFinalInput.setToolTipText("Formato: gg/mm/aaaa");
        mainPanel.add(dobFinalInput);
        mainPanel.add(Box.createVerticalStrut(20));

        // Result label
        final JLabel result = cf.createFieldLabel("");

        // Register button
        final JButton registerButton = new JButton("Calcola Percentuale Accettate e Rifiutate");
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        registerButton.setFont(new Font(FONT, Font.BOLD, 20));
        registerButton.setMaximumSize(new Dimension(300, 40));
        registerButton.addActionListener(e -> {
            String dobStart = dobInput.getText().trim();
            String dobFinal = dobFinalInput.getText().trim();
            Timestamp timestampStart;
            Timestamp timestampTo;
            if (dobStart.isEmpty() || dobFinal.isEmpty()) {
                displayRegisterError("Inserisci le date di inizio e fine analisi!");
                return;
            }
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                java.util.Date parsedDateStart = dateFormat.parse(dobStart);
                timestampStart = new Timestamp(parsedDateStart.getTime());

                java.util.Date parsedDateTo = dateFormat.parse(dobStart);
                timestampTo = new Timestamp(parsedDateTo.getTime());

            } catch (Exception ex) {
                displayRegisterError("Formato data di nascita non valido! Usa gg/mm/aaaa");
                return;
            }
            String success = this.view.getController().acceptedRejectedPercentage(timestampStart, timestampTo);
            System.out.println(success);
            if (success == null || success.isBlank()) {
                result.setText("Errore durante il calcolo delle percentuali!");
                result.setVisible(true);
                displayRegisterError("Errore durante il calcolo delle percentuali!");
                return;
            }
            result.setText(success);
            result.setVisible(true);
        });
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(registerButton);

        
        mainPanel.add(result);
        result.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));

        final JButton backButton = new JButton("Torna indietro");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setFont(new Font(FONT, Font.BOLD, 20));
        backButton.setMaximumSize(new Dimension(300, 40));
        backButton.addActionListener(e -> {
            this.view.goToAdminScene();
        });
        this.add(backButton, BorderLayout.SOUTH);

    }

    public void displayRegisterError(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("Error message cannot be null or empty");
        }
        this.infoLabel.setText(string);
        this.infoLabel.setForeground(Color.RED);
        this.infoLabel.setVisible(true);
        SwingUtilities.invokeLater(() -> this.infoLabel.scrollRectToVisible(this.infoLabel.getBounds()));
    }

}
