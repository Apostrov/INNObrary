import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is used only for building the user documents table. */
public class UserReqTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<User> users;

    UserReqTableModel(List<User> users) {
        this.users = new ArrayList<>();
        this.users.addAll(users);
        // There is no need to contain the librarian in the table
        for (int i = 0; i < this.users.size(); ++i) if (this.users.get(i) instanceof Librarian) this.users.remove(i);
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
                return "Username";
            case 1:
                return "Priority";
            case 2:
                return "Requests number";
        }
        return "";
    }

    public int getRowCount() {
        return users.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getUsername();
            case 1:
                return user.getPriority();
            case 2:
                return user.getRequestNum();
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
