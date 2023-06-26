package org.bs;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class RunTable extends AbstractTableModel {
    private String[] columnNames;
    private ArrayList<ArrayList<Run>> data;

    public RunTable(ArrayList<ArrayList<Run>> data) {
        columnNames = new String[] {"Start", "Ziel", "Zeit", "Datum"};
        this.data = data;
    }


    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
