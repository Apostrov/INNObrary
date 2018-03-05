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
                userBookTitles.add(userForView.getBookings().get(i).getDoc().getTitle());
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
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
            Main.userView.setVisible(false);
        });
        // Request button
        JButton requestBtn = new JButton("Request");
        requestBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        requestBtn.addActionListener(e -> { // What to do when libRequest button was pressed
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select user's order!");
            } else {
                Booking booking = new Booking(null, 0);
                for (int i = 0; i < userForView.getBookings().size(); ++i) {
                    if (userForView.getBookings().get(i).getDoc().getTitle().equals(userDoc))
                        booking = userForView.getBookings().get(i);
                }
                if (booking.hasRequestedByLib()) {
                    JOptionPane.showMessageDialog(mainPanel, "Already requested!");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully requested!");
                    booking.libRequest();
                    DataBase.doOrder(userForView, booking.getDoc(), booking.getTimeLeft(), booking.hasRequestedByLib(), booking.hasRequestedByUser());
                    Main.userView.setVisible(false);
                    Main.userView = new UserViewScreen(userForView);
                    Main.userView.setLocationRelativeTo(null);
                    Main.userView.setVisible(true);
                }
            }
        });
        // Return button
        JButton returnBtn = new JButton("Return");
        returnBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        returnBtn.addActionListener(e -> { // What to do when libRequest button was pressed
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select user's order!");
            } else{
                Booking booking = new Booking(null, 0);
                for (int i = 0; i < userForView.getBookings().size(); ++i) {
                    if (userForView.getBookings().get(i).getDoc().getTitle().equals(userDoc))
                        booking = userForView.getBookings().get(i);
                }
                if (booking.hasRequestedByUser()) {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully returned!");
                    boolean hasFound = false;
                    for (int i = 0; i < Main.documents.size(); ++i) {
                        if (Main.documents.get(i).getTitle().equals(booking.getDoc().getTitle())) {
                            hasFound = true;
                            Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                            DataBase.deleteOrder(userForView, booking.getDoc());
                            boolean found = userForView.getBookings().remove(booking);
                            System.out.println(found);
                        }
                    }
                    if (!hasFound) {
                        booking.getDoc().setCopies(1);
                        Main.documents.add(booking.getDoc());
                        DataBase.addDoc(booking.getDoc());
                    }
                    Main.userView.setVisible(false);
                    Main.userView = new UserViewScreen(userForView);
                    Main.userView.setLocationRelativeTo(null);
                    Main.userView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "User has not requested yet!");
                }
            }
        });
        backBox.add(backBtn);
        backBox.add(Box.createRigidArea(new Dimension(75, 0)));
        backBox.add(requestBtn);
        backBox.add(Box.createRigidArea(new Dimension(5, 0)));
        backBox.add(returnBtn);

        // Info about order button
        JButton orderInfoBtn = new JButton(" Order info ");
        orderInfoBtn.addActionListener(e -> { // What to do when order info button was pressed
            String orderTitle = userDoc; 
            if (orderTitle != null) {
                Booking booking = userForView.getBookings().get(0);
                for (int i = 0; i < userForView.getBookings().size(); ++i) {
                    if (userForView.getBookings().get(i).getDoc().getTitle().equals(orderTitle))
                        booking = userForView.getBookings().get(i);
                }
                String info = "";
                info += "Information about user's order:\n";
                info += "Title:  " + booking.getDoc().getTitle() + "\n";
                info += "Authors:  " + booking.getDoc().getAuthors() + "\n";
                info += "Price:  " + booking.getDoc().getPrice() + " rubles.\n";
                info += "Time left:  " + booking.getTimeLeft() + " days.\n";
                if (booking.getDoc().isReference()) info += "This is a reference document.\n";
                if (booking.getDoc() instanceof Book) if(booking.getDoc().isBestSeller()) info += "This is a bestseller book.\n";
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
        ubLabel.setText("Orders of " + username);
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
