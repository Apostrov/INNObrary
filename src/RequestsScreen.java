package main.java;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RequestsScreen extends JFrame {

    private String user = null;
    private String request = null;
    private ReqTableModel requestModel;
    private JScrollPane requestScroll;
    private JTable requestTable;

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

        // List of user requests (if any)
        Box requestsBox = Box.createHorizontalBox();
        requestsBox.add(Box.createRigidArea(new Dimension(10, 0)));

        if (user != null)
            requestModel = new ReqTableModel(Main.findUser(user).getBookings());
        else
            requestModel = new ReqTableModel(new ArrayList<>());
        requestTable = new JTable(requestModel);
        ListSelectionModel requestCellSelectionModel = requestTable.getSelectionModel();
        requestCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        requestCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = requestTable.getSelectedRows();
            int[] selectedColumns = requestTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    request = requestTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        requestScroll = new JScrollPane(requestTable);
        requestScroll.setPreferredSize(new Dimension(50, 50));
        requestScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        requestsBox.add(requestScroll);
        requestsBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of users who has requests
        Box userRequestBox = Box.createHorizontalBox();
        userRequestBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel userModel = new UserReqTableModel(Main.users);
        JTable userTable = new JTable(userModel);
        ListSelectionModel userCellSelectionModel = userTable.getSelectionModel();
        userCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = userTable.getSelectedRows();
            int[] selectedColumns = userTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    user = userTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
            // The lines below are used to real-time update the table
            List<Booking> list = new LinkedList<>();
            list.addAll(Main.findUser(user).getBookings());
            requestModel.replace(list);
            requestTable.revalidate();
        });

        JScrollPane usersScroll = new JScrollPane(userTable);
        usersScroll.setPreferredSize(new Dimension(50, 50));
        usersScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userRequestBox.add(usersScroll);
        userRequestBox.add(Box.createRigidArea(new Dimension(10, 0)));

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

        // Outstanding request button
        JButton outRequestBtn = new JButton("Make outstanding");
        outRequestBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        outRequestBtn.addActionListener(e -> { // What to do when libRequest button was pressed
            if (request == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select user's request!");
            } else if (Main.findDoc(request).isOutstanding()) {
                JOptionPane.showMessageDialog(mainPanel, "There is already placed an outstanding request!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "The outstanding request\nsuccessfully placed!");
                Document doc = Main.findDoc(request);
                for (int i = 0; i < Main.reqDocs.size(); ++i) {
                    if (doc.getTitle().equals(Main.reqDocs.get(i).getTitle())) {
                        Main.reqDocs.get(i).setOutstanding(true);
                        Main.priorityQueues.get(i).clear();
                        Main.priorityQueues.get(i).add(Main.findUser(user));
                    }
                }
            }
        });
        outRequestBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of the requests
        JLabel requestsLabel = new JLabel();
        requestsLabel.setText("Requests");
        requestsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userRequestBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(requestsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(requestsBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(outRequestBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

}
