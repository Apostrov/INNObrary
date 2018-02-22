import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserViewScreen extends JFrame {
		
	private String userDoc = null;	

    UserViewScreen(User userForView) {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
                createGUI(userForView);
        });
    }

    private void createGUI(User userForView) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // List of user books
        Box userBookBox = Box.createHorizontalBox();
        userBookBox.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> userBookTitles = new ArrayList<>();
        if (userForView != null) 
            for (int i = 0; i < userForView.getBookings().size(); ++i)
                userBookTitles.add(userForView.getBookings().get(i).doc.title);
        JList<String> userBookList = new JList<>(userBookTitles.toArray(new String[userBookTitles.size()]));
        userBookList.setLayoutOrientation(JList.VERTICAL);
        userBookList.setVisibleRowCount(0);

        TableModel docModel = new UserDocTableModel(Main.users.get(0).getBookings());
        if (userForView != null) docModel = new UserDocTableModel(userForView.getBookings());
        JTable userDocTable = new JTable(docModel);
        ListSelectionModel userCellSelectionModel = userDocTable.getSelectionModel();
        userCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = userDocTable.getSelectedRows();
            int[] selectedColumns = userDocTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    userDoc = userDocTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane userBookScroll = new JScrollPane(userDocTable);
        userBookScroll.setPreferredSize(new Dimension(50, 50));
        userBookScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userBookBox.add(userBookScroll);
        userBookBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Back button
        Box backBox = Box.createHorizontalBox();
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        backBtn.addActionListener(e -> { // What to do when back button was pressed
            Main.cabinet.setVisible(true);
            Main.userView.setVisible(false);
        });
        backBox.add(backBtn);
        backBox.add(Box.createRigidArea(new Dimension(215, 0)));

        // Info about order button
        JButton orderInfoBtn = new JButton(" Order info ");
        orderInfoBtn.addActionListener(e -> { // What to do when order info button was pressed
            String orderTitle = userDoc; 
            if (orderTitle != null) {
                Booking booking = userForView.getBookings().get(0);
                for (int i = 0; i < userForView.getBookings().size(); ++i) {
                    if (userForView.getBookings().get(i).doc.title.equals(orderTitle))
                        booking = userForView.getBookings().get(i);
                }
                String info = "";
                info += "Information about user's order:\n";
                info += "Title:  " + booking.doc.title + "\n";
                info += "Authors:  ";
                for (int i = 0; i < booking.doc.authors.size(); ++i)
                    info += booking.doc.authors.get(i) + (booking.doc.authors.size() > 1 ? "," : "") + " ";
                info += "\n";
                info += "Price:  " + booking.doc.getPrice() + " rubles.\n";
                info += "Time left:  " + booking.getDaysLeft() + " days.\n";
                if (booking.doc.isReference()) info += "This is a reference document.\n";
                if (booking.doc instanceof Book) if(((Book) booking.doc).isBS()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select user's order!");
            }
        });
        orderInfoBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of library books
        JLabel ubLabel = new JLabel();
        String username = "";
        if (userForView != null) username += userForView.getUsername();
        ubLabel.setText("Documents of " + username);
        ubLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(ubLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userBookBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(orderInfoBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

}
