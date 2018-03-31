import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** This class is used only for building the user documents table. */
public class ReqTableModel extends AbstractTableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Booking> bookings;

    public ReqTableModel(List<Booking> bookings) {
        this.bookings = new LinkedList<>();
        for (int i = 0; i < bookings.size(); ++i)
            if (!bookings.get(i).hasReceived())
                this.bookings.add(bookings.get(i));
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Title";
            case 1:
                return "Date";
            case 2:
                return "Duration";
        }
        return "";
    }

    public int getRowCount() {
        return bookings.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Booking booking = bookings.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return booking.getDoc().getTitle();
            case 1:
                return booking.getDate().toString();
            case 2:
                return booking.getDuration() + " days";
        }
        return "";
    }

    /** Special method that is used to real-time update of the table. */
    void replace (List<Booking> bookings) {
        this.bookings.clear();
        fireTableRowsDeleted(0, getRowCount());
        for (int i = 0; i < bookings.size(); ++i)
            if (!bookings.get(i).hasReceived())
                this.bookings.add(bookings.get(i));
        fireTableRowsInserted(0, bookings.size());
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

}
