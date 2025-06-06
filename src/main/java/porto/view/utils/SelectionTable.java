package porto.view.utils;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class SelectionTable extends JTable {

    public SelectionTable(Object[][] data, Object[] columnNames) {
        super(
            new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            }
        );
        this.setFont(new Font("Roboto", Font.PLAIN, 16));
        this.setRowHeight(30);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setRowSelectionAllowed(true);
        this.setColumnSelectionAllowed(false);
        this.setEnabled(true);
    }
}