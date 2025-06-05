package porto.view.utils;

import java.awt.event.FocusAdapter;

public class JComponentsFactory {

    public CustomComponents.FieldLabel createFieldLabel(String text) {
        return new CustomComponents.FieldLabel(text);
    }

    public CustomComponents.FieldInput createFieldInput(int columns, FocusAdapter focusListener) {
        return new CustomComponents.FieldInput(columns, focusListener);
    }

    public CustomComponents.SelectionBox createSelectionBox(String[] items) {
        return new CustomComponents.SelectionBox(items);
    }

    public CustomComponents.CheckBoxPanel createCheckBoxPanel(String labelText) {
        return new CustomComponents.CheckBoxPanel(labelText);
    }

}
