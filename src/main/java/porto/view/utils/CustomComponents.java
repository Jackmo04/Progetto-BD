package porto.view.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomComponents {

    private static final String FONT = "Roboto";
    private static final int FONT_SIZE = 16;
    private static final Dimension MAXIMUM_SIZE = new Dimension(300, 40);

    public static class FieldLabel extends JLabel {
        public FieldLabel(String text) {
            super(text, JLabel.CENTER);
            this.setAlignmentX(CENTER_ALIGNMENT);
            this.setFont(new Font(FONT, Font.PLAIN, 16));
        }
    }

    public static class FieldInput extends JTextField {
        
        public FieldInput(int columns, FocusAdapter focusListener) {
            super(columns);
            this.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
            this.setMaximumSize(MAXIMUM_SIZE);
            this.addFocusListener(focusListener);
        }
    }

    public static class SelectionBox extends JComboBox<String> {
        public SelectionBox(String[] items) {
            super(items);
            this.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
            this.setMaximumSize(MAXIMUM_SIZE);
            this.setSelectedIndex(0);
            this.setAlignmentX(CENTER_ALIGNMENT);
        }
    }

    public static class CheckBoxPanel extends JPanel {

        private final JCheckBox checkBox;

        public CheckBoxPanel(String labelText) {
            this.setLayout(new BorderLayout());
            this.setAlignmentX(CENTER_ALIGNMENT);
            this.setMaximumSize(MAXIMUM_SIZE);
            final JLabel label = new FieldLabel(labelText);
            this.add(label, BorderLayout.WEST);
            this.checkBox = new JCheckBox();
            this.checkBox.setAlignmentX(CENTER_ALIGNMENT);
            this.checkBox.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
            this.checkBox.setMaximumSize(MAXIMUM_SIZE);
            this.add(this.checkBox, BorderLayout.EAST);
        }

        public JCheckBox checkBox() {
            return this.checkBox;
        }
    }
}
