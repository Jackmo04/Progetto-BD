package porto.view.utils;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ViewingTable extends JTable {

    public ViewingTable(Object[][] data, Object[] columnNames) {
        super(
            new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            }
        );
        this.setFont(new Font("Roboto", Font.PLAIN, 12));
        this.setRowHeight(30);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setRowSelectionAllowed(false);
        this.setColumnSelectionAllowed(false);
        this.setEnabled(false);
        this.setVisible(true);
    }
}
