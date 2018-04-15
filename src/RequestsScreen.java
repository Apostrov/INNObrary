package main.java;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.LinkedList;

class RequestsScreen extends JFrame {

    private String document = null;
    private UserReqTableModel usersModel;
    private JScrollPane userScroll;
    private JTable usersTable;

    RequestsScreen() {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            createGUI();
        });
    }

    private void createGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // List of document who requested selected document (if any)
        Box requestsBox = Box.createHorizontalBox();
        requestsBox.add(Box.createRigidArea(new Dimension(10, 0)));

        if (document != null) {
            LinkedList<User> req_users = new LinkedList<>();
            int index = 0;
            for (int i = 0; i < Main.documents.size(); ++i) if (Main.documents.get(i).getTitle().equals(document)) index = i;
            req_users.addAll(Main.priorityQueues.get(index));
            usersModel = new UserReqTableModel(req_users);
        } else {
            usersModel = new UserReqTableModel(new LinkedList<>());
        }
        usersTable = new JTable(usersModel);
        ListSelectionModel usersCellSelectionModel = usersTable.getSelectionModel();
        usersCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        userScroll = new JScrollPane(usersTable);
        userScroll.setPreferredSize(new Dimension(50, 50));
        userScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        requestsBox.add(userScroll);
        requestsBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of requested documents
        Box reqDocBox = Box.createHorizontalBox();
        reqDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel docModel = new DocReqTableModel(Main.documents);
        JTable docTable = new JTable(docModel);
        ListSelectionModel docCellSelectionModel = docTable.getSelectionModel();
        docCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        docCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = docTable.getSelectedRows();
            int[] selectedColumns = docTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    document = docTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
            // The lines below are used to real-time update the table
            LinkedList<User> req_users = new LinkedList<>();
            int index = 0;
            for (int i = 0; i < Main.documents.size(); ++i) if (Main.documents.get(i).getTitle().equals(document)) index = i;
            req_users.addAll(Main.priorityQueues.get(index));
            usersModel.replace(req_users);
            usersTable.revalidate();
        });

        JScrollPane docScroll = new JScrollPane(docTable);
        docScroll.setPreferredSize(new Dimension(50, 50));
        docScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        reqDocBox.add(docScroll);
        reqDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Back button
        Box backBox = Box.createHorizontalBox();
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        backBtn.addActionListener(e -> { // What to do when back button was pressed
            Main.requests.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBox.add(backBtn);
        backBox.add(Box.createRigidArea(new Dimension(235, 0)));

        // Box for outstanding buttons
        Box outBox = Box.createHorizontalBox();
        // Outstanding user button
        JButton setOutBtn = new JButton("Make outstanding");
        setOutBtn.addActionListener(e -> {
            if (document == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to place the outstanding request for some document.)");
            } else if (Main.findDoc(document).isOutstanding()) {
                JOptionPane.showMessageDialog(mainPanel, "There is already placed an outstanding request!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to place the outstanding request for the document \"" +
                        Main.findDoc(document).getTitle() + "\" that already has the outstanding request placed for it.)");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "The outstanding request\nsuccessfully placed!\n" +
                        "All users in the queue were notified\nabout removal from the queue.\n");
                Document doc = Main.findDoc(document);
                doc.setOutstanding(true);
                DataBase.addDoc(doc);
                for (int i = 0; i < Main.documents.size(); ++i) {
                    if (doc.getTitle().equals(Main.documents.get(i).getTitle())) {
                        // Remove all requests related to this document
                        for (int j = 0; j < Main.priorityQueues.get(i).size(); ++j) {
                            User u = Main.priorityQueues.get(i).poll();
                            DataBase.deleteOrder(u, Main.documents.get(i));
                            u.getBookings().remove(u.findBooking(Main.documents.get(i).getTitle()));
                            u.notify(new Notification(2, "null", "null", Main.date, 0));
                            DataBase.replaceNotifications(u);
                        }
                        for (int j = 0; j < Main.users.size(); ++j) {
                            if (Main.users.get(j).findBooking(doc.getTitle()) != null) {
                                Main.users.get(j).notify(new Notification(5, doc.getTitle(), "null", Main.date, 0));
                                DataBase.replaceNotifications(Main.users.get(j));
                            }
                        }
                    }
                }
                LinkedList<User> req_users = new LinkedList<>();
                int index = 0;
                for (int i = 0; i < Main.documents.size(); ++i) if (Main.documents.get(i).getTitle().equals(document)) index = i;
                req_users.addAll(Main.priorityQueues.get(index));
                usersModel.replace(req_users);
                usersTable.revalidate();
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has placed the outstanding request for the document \"" + doc.getTitle() + "\".)");
            }
        });
        setOutBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        if (Main.activeUser != null && Main.activeUser.getPriority() < 6) { setOutBtn.setEnabled(false); setOutBtn.setFocusPainted(false); setOutBtn.setBorderPainted(false); }

        // Reset outstanding user button
        JButton resetOutBtn = new JButton("Remove request");
        resetOutBtn.addActionListener(e -> { // What to do when libRequest button was pressed
            if (document == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to remove the request from some document.)");
            } else if (!Main.findDoc(document).isOutstanding()) {
                JOptionPane.showMessageDialog(mainPanel, "There is no placed outstanding request!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to remove the outstanding request from the document" +
                        " \"" + Main.findDoc(document).getTitle() + "\" that has no outstanding request placed for it.)");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "The outstanding request\nsuccessfully removed!\n" +
                        "All users were notified\nabout availability of the document.\n");
                Document doc = Main.findDoc(document);
                doc.setOutstanding(false);
                DataBase.addDoc(doc);
                // Notify all users
                for (int i = 0; i < Main.users.size(); ++i) {
                    Main.users.get(i).notify(new Notification(6, doc.getTitle(), "null", Main.date, 0));
                    DataBase.replaceNotifications(Main.users.get(i));
                }
                LinkedList<User> req_users = new LinkedList<>();
                int index = 0;
                for (int i = 0; i < Main.documents.size(); ++i) if (Main.documents.get(i).getTitle().equals(document)) index = i;
                req_users.addAll(Main.priorityQueues.get(index));
                usersModel.replace(req_users);
                usersTable.revalidate();
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has removed the outstanding request from the document \"" + doc.getTitle() + "\".)");
            }
        });
        resetOutBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        if (Main.activeUser != null && Main.activeUser.getPriority() < 6) { resetOutBtn.setEnabled(false); resetOutBtn.setFocusPainted(false); resetOutBtn.setBorderPainted(false); }

        outBox.add(setOutBtn);
        outBox.add(Box.createRigidArea(new Dimension(5, 0)));
        outBox.add(resetOutBtn);

        //Label of the users
        JLabel usersLabel = new JLabel();
        usersLabel.setText("Users");
        usersLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(reqDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(usersLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(requestsBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(outBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

}
