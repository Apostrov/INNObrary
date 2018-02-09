import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDocTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Booking> bookings;

    public UserDocTableModel(List<Booking> bookings) {
        this.bookings = bookings;
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
                return "Days left";
            case 2:
                return "Price";
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
                return booking.doc.getTitle();
            case 1:
                return booking.delay;
            case 2:
                return booking.doc.getPrice();
        }
        return "";
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
