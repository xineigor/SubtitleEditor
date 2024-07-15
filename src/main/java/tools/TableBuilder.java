package tools;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder {

    private final List<String[]> rows = new ArrayList<>();
    private int[] columnWidths;

    public TableBuilder(String... headers) {
        addRow(headers);
    }

    public void addRow(String... cells) {
        if (columnWidths == null) {
            columnWidths = new int[cells.length];
        }
        for (int i = 0; i < cells.length; i++) {
            columnWidths[i] = Math.max(columnWidths[i], cells[i].length());
        }
        rows.add(cells);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                sb.append(String.format("%-" + (columnWidths[i] + 2) + "s", row[i]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}