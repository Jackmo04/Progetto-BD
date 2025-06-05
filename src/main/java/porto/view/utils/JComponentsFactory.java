package porto.view.utils;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JComponentsFactory {

    private static final String FONT = "Roboto";
    private static final int FONT_SIZE = 16;
    private static final Dimension MAXIMUM_SIZE = new Dimension(300, 40);

    private class FieldLabel extends JLabel {
        public FieldLabel(String text) {
            super(text, JLabel.CENTER);
            this.setAlignmentX(CENTER_ALIGNMENT);
            this.setFont(new Font(FONT, Font.PLAIN, 16));
        }
    }

    private class FieldInput extends JTextField {
        
        public FieldInput(int columns, FocusAdapter focusListener) {
            super(columns);
            this.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
            this.setMaximumSize(MAXIMUM_SIZE);
            this.addFocusListener(focusListener);
        }
    }

    private class SelectionBox extends JComboBox<String> {
        public SelectionBox(String[] items) {
            super(items);
            this.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
            this.setMaximumSize(MAXIMUM_SIZE);
            this.setSelectedIndex(0);
            this.setAlignmentX(CENTER_ALIGNMENT);
        }
    }

    private class CheckBoxPanel extends JPanel {

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

    public FieldLabel createFieldLabel(String text) {
        return new FieldLabel(text);
    }

    public FieldInput createFieldInput(int columns, FocusAdapter focusListener) {
        return new FieldInput(columns, focusListener);
    }

    public SelectionBox createSelectionBox(String[] items) {
        return new SelectionBox(items);
    }

    public CheckBoxPanel createCheckBoxPanel(String labelText) {
        return new CheckBoxPanel(labelText);
    }

}
