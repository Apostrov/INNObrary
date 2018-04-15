package main.java;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

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
        backBtn.addActionListener(e -> {
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
            Main.userView.setVisible(false);
        });
        // Request button
        JButton requestBtn = new JButton("Request");
        requestBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        requestBtn.addActionListener(e -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select user's order!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to send a request for returning of some of the orders of user " + userForView.getUsername() + ".)");
            } else {
                Booking b = userForView.findBooking(userDoc);
                if (b.hasRequestedByUser()) {
                    JOptionPane.showMessageDialog(mainPanel, "User has already requested for returning!\n" +
                            "Press the 'Return' button!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to send a request for returning of the document \"" + b.getDoc().getTitle() +
                            "\" of user " + userForView.getUsername() + " that is already have been requested from the user.)");
                } else if (b.hasRequestedByLib()) {
                    JOptionPane.showMessageDialog(mainPanel, "Already requested!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to send a request for returning of the document \"" + b.getDoc().getTitle() + "\" again.)");
                } else if (!b.hasReceived()) {
                    JOptionPane.showMessageDialog(mainPanel, "User has not received the document yet!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to send a request for returning of the document \"" + b.getDoc().getTitle() + "\"" +
                            " but user " + userForView.getUsername() + " has not received it yet.)");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully requested!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has sent the request for returning the document \"" + b.getDoc().getTitle() + "\"" +
                            " of user " + userForView.getUsername() + ".)");
                    b.libRequest();
                    DataBase.doOrder(userForView, b.getDoc(), b.getDuration(), b.getDate(), b.hasRequestedByLib(), b.hasRequestedByUser(), b.hasReceived(), b.hasRenewed());
                    userForView.notify(new Notification(4, b.getDoc().getTitle(), "null", Main.date, 0));
                    DataBase.replaceNotifications(userForView);
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
        returnBtn.addActionListener(e -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select user's order!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to accept some document of the user " + userForView.getUsername() + ".)");
            } else {
                Booking booking = userForView.findBooking(userDoc);
                if (booking.hasRequestedByUser()) {
                    boolean hasFound = false;
                    User user = null;
                    for (int i = 0; i < Main.documents.size(); ++i) {
                        if (Main.documents.get(i).getTitle().equals(booking.getDoc().getTitle())) {
                            hasFound = true;
                            DataBase.deleteOrder(userForView, booking.getDoc());
                            userForView.getBookings().remove(booking);
                            // Check for the users who has requested for this document
                            user = Main.priorityQueues.get(i).poll();
                            if (user == null) {
                                booking.getDoc().setCopies(booking.getDoc().getCopies() + 1);
                                DataBase.addDoc(booking.getDoc());
                                break;
                            }
                            user.notify(new Notification(1, booking.getDoc().getTitle(), "null", Main.date, 0));
                            DataBase.replaceNotifications(user);
                            DataBase.addDoc(booking.getDoc());
                        }
                    }
                    String info = "Successfully returned!\n";
                    if (booking.isOverdue()) {
                        info += "The order is overdue.\nThe user will be notified to pay fee.";
                        int fine = (-1 * booking.getTimeLeft()) * 100 >= booking.getDoc().getPrice() ? booking.getDoc().getPrice() : (-1 * booking.getTimeLeft()) * 100;
                        userForView.notify(new Notification(3, booking.getDoc().getTitle(), "null", Main.date, fine));
                        DataBase.replaceNotifications(userForView);
                    }
                    if (user != null) info += "\nThe next user in the queue was notified\nabout availability of the document.";
                    JOptionPane.showMessageDialog(mainPanel, info);
                    if (!hasFound) {
                        booking.getDoc().setCopies(1);
                        Main.documents.add(booking.getDoc());
                        DataBase.addDoc(booking.getDoc());
                    }
                    Main.userView.setVisible(false);
                    Main.userView = new UserViewScreen(userForView);
                    Main.userView.setLocationRelativeTo(null);
                    Main.userView.setVisible(true);
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has accepted the document \"" + booking.getDoc().getTitle() + "\"" +
                            " from the user " + userForView.getUsername() + ".)");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "User has not requested yet!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to accept the document \"" + booking.getDoc().getTitle() + "\"" +
                            " from the user " + userForView.getUsername() + " who has not requested for " +
                            "returning yet.)");
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
        orderInfoBtn.addActionListener(e -> {
            String orderTitle = userDoc; 
            if (orderTitle != null) {
                Booking booking = userForView.findBooking(userDoc);
                String info = "";
                info += "Information about user's order:\n";
                info += "Title:  " + booking.getDoc().getTitle() + ".\n";
                info += "Authors:  " + booking.getDoc().getAuthors() + ".\n";
                info += "Price:  " + booking.getDoc().getPrice() + " rubles.\n";
                if (booking.hasReceived()) {
                    info += "Date of booking: " + booking.getDate().toString() + ".\n";
                    info += "Time left:  " + booking.getTimeLeft() + " days.\n";
                } else {
                    info += "Date of booking:  unknown\n(User has not received the document yet).\n";
                    info += "Time left:   unknown\n(User has not received the document yet).\n";
                }
                if (booking.getDoc().isReference()) info += "This is a reference document.\n";
                if (booking.getDoc() instanceof Book) if(booking.getDoc().isBestSeller()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has checked the info about the order of the document \"" + booking.getDoc().getTitle() +
                        "\" of user " + userForView.getUsername() + ".)");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select user's order!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to check the info about some of the orders of user " + userForView.getUsername() + ".)");
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
