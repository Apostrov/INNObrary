import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

class CabinetScreen extends JFrame {

    private String libDoc = null;
    private String userDoc = null;

    CabinetScreen(boolean isLibrarian) {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            if (isLibrarian) // Different GUIs for the users and librarians
                createLibGUI();
            else
                createUserGUI();
        });
    }

    /** Initialization of the objects of the personal area page of librarians */
    private void createLibGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(); // Main container
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // From top to bottom
        setResizable(false);

        // Table of library documents
        Box libDocBox = Box.createHorizontalBox();
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel docModel = new DocTableModel(Main.documents);
        JTable libDocTable = new JTable(docModel);
        ListSelectionModel libCellSelectionModel = libDocTable.getSelectionModel();
        libCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        libCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = libDocTable.getSelectedRows();
            int[] selectedColumns = libDocTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    libDoc = libDocTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane libBookScroll = new JScrollPane(libDocTable);
        libBookScroll.setPreferredSize(new Dimension(50, 50)); // Indentation
        libBookScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        /*libBookList.addListSelectionListener(e -> {
            // What to do when the element of libBookList was selected
        });*/
        libDocBox.add(libBookScroll);
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of users
        Box userBox = Box.createHorizontalBox();
        userBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel userModel = new UserTableModel(Main.users);
        JTable userDocTable = new JTable(userModel);
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

        JScrollPane userScroll = new JScrollPane(userDocTable);
        userScroll.setPreferredSize(new Dimension(50, 50));
        userScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userBox.add(userScroll);
        userBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Log out button
        Box logoutBox = Box.createHorizontalBox();
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logoutBtn.addActionListener(e -> { // What to do when logout button was pressed
            Main.cabinet.setVisible(false);
            Main.login.setVisible(true);
            Main.login.passField.setText(""); // Reset the password field
        });
        logoutBox.add(logoutBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(325, 0)));

        // Buttons related to books
        Box docBtnBox = Box.createHorizontalBox();
        // Add button
        JButton addDocBtn = new JButton("  Add  ");
        addDocBtn.addActionListener(e -> {
            // TODO: implement the functionality of the button 'Add' (add a document)
        });
        addDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(addDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Info button
        JButton infoDocBtn = new JButton(" Info ");
        infoDocBtn.addActionListener(e -> { // What to do when info button was pressed
            String docTitle = libDoc;
            if (docTitle != null) {
                Document doc = Main.findDoc(docTitle);
                String info = "";
                info += "Information about the document:\n";
                info += "Title:  " + doc.title + "\n";
                info += "Authors:  ";
                for (int i = 0; i < doc.authors.size(); ++i)
                    info += doc.authors.get(i) + (doc.authors.size() > 1 ? "," : "") + " ";
                info += "\n";
                info += "Price:  " + doc.getPrice() + " rubles.\n";
                info += "Copies left:  " + doc.getCopies() + "\n";
                if (doc.isReference()) info += "This is a reference document.\n";
                if (doc instanceof Book) if(((Book) doc).isBS()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(infoDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Modify button
        JButton modifyDocBtn = new JButton("Modify");
        modifyDocBtn.addActionListener(e -> {
            // TODO: implement the functionality of the button 'Modify' (modify a document)
        });
        modifyDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(modifyDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Remove button
        JButton removeDocBtn = new JButton("Remove");
        removeDocBtn.addActionListener(e -> {
            // TODO: implement the functionality of the button 'Remove' (remove a document)
        });
        removeDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(removeDocBtn);

        // User view button
        JButton viewUserBtn = new JButton("View");
        viewUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        viewUserBtn.addActionListener(l -> {
            String username = userDoc;
            if (username != null) { // What to do when view button (view user) was pressed
                Main.userView = new UserViewScreen(Main.findUser(username));
                Main.userView.setVisible(true);
                Main.cabinet.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            }
        });

        //Label of library books
        JLabel libLabel = new JLabel();
        libLabel.setText("Library documents");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of users
        JLabel userLabel = new JLabel();
        userLabel.setText("Users");
        userLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(logoutBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(libLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(libDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(docBtnBox, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(userLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(viewUserBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(450, 640));
        pack();
        setLocationRelativeTo(null);
    }

    /** Initialization of the objects of the personal area page of patrons */
    private void createUserGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // Table of library books
        Box libDocBox = Box.createHorizontalBox();
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel docModel = new DocTableModel(Main.documents);
        JTable libDocTable = new JTable(docModel);
        ListSelectionModel libCellSelectionModel = libDocTable.getSelectionModel();
        libCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        libCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = libDocTable.getSelectedRows();
            int[] selectedColumns = libDocTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    libDoc = libDocTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane libDocScroll = new JScrollPane(libDocTable);
        libDocScroll.setPreferredSize(new Dimension(50, 50));
        libDocScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        libDocBox.add(libDocScroll);
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Table of user documents
        Box userDocBox = Box.createHorizontalBox();
        userDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel userModel;
        if (Main.activeUser != null) {
            userModel = new UserDocTableModel(Main.activeUser.getBookings());
        } else {
            userModel = null;
        }
        JTable userTable = new JTable(userModel);
        ListSelectionModel userCellSelectionModel = userTable.getSelectionModel();
        userCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = userTable.getSelectedRows();
            int[] selectedColumns = userTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    userDoc = userTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane userDocScroll = new JScrollPane(userTable);
        userDocScroll.setPreferredSize(new Dimension(50, 50));
        userDocScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userDocBox.add(userDocScroll);
        userDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Log out button
        Box logoutBox = Box.createHorizontalBox();
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logoutBtn.addActionListener(e -> { // What to do when logout button was pressed
            for (int i = 0; i < Main.users.size(); ++i)
                if (Main.users.get(i).getUsername().equals(Main.activeUser.getUsername()))
                    Main.users.get(i).setBookings(Main.activeUser.getBookings());
            Main.cabinet.setVisible(false);
            Main.login.setVisible(true);
            Main.login.passField.setText("");
        });
        logoutBox.add(logoutBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(325, 0)));

        // Order button
        Box orderBox = Box.createHorizontalBox();
        JButton orderBtn = new JButton("Order");
        orderBtn.addActionListener(e -> { // What to do when order the book button was pressed
            if (libDoc != null) {
                Document doc = Main.findDoc(libDoc);
                boolean alreadyHas = false; // Check whether the patron already has one copy of the book
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {
                    if (doc.title.equals(Main.activeUser.getBookings().get(i).doc.title))
                        alreadyHas = true;
                }
                if (doc.getCopies() <= 0) {
                    JOptionPane.showMessageDialog(mainPanel, "There is no more such documents!");
                } else if (doc.isReference()) {
                    JOptionPane.showMessageDialog(mainPanel, "You cannot order a reference document!");
                } else if (alreadyHas) {
                    JOptionPane.showMessageDialog(mainPanel, "You already have one copy of this document!");
                } else {
                    Main.cabinet.setVisible(false);
                    doc.setCopies(doc.getCopies() - 1);
                    int delay = 21;                                                 // Usually it is 3 weeks
                    if (Main.activeUser.isFaculty()) delay = 28;                    // If user is faculty member, then 4 weeks
                    if (doc instanceof Book) if (((Book) doc).isBS()) delay = 14;   // If the document is book-bestseller, then 2 weeks
                    if (doc instanceof AudioVideo) delay = 14;                      // If the document is AV-material, then 2 weeks
                    Main.activeUser.addBooking(new Booking(doc, delay));
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            }
        });
        orderBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        orderBox.add(orderBtn);
        orderBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Info about order button
        JButton infoDocBtn = new JButton(" Order info ");
        infoDocBtn.addActionListener(e -> { // What to do when info about the order button was pressed
            String docTitle = userDoc;
            if (docTitle != null) {
                Booking booking = Main.activeUser.getBookings().get(0);             // Here we need a booking because
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {    // we should provide the time that is left
                    if (Main.activeUser.getBookings().get(i).doc.title.equals(docTitle))
                        booking = Main.activeUser.getBookings().get(i);
                }
                String info = "";
                info += "Information about the order:\n";
                info += "Title:  " + booking.doc.title + "\n";
                info += "Authors: ";
                for (int i = 0; i < booking.doc.authors.size(); ++i)
                    info += booking.doc.authors.get(i) + (booking.doc.authors.size() > 1 ? "," : "") + " ";
                info += "\n";
                info += "Price: " + booking.doc.getPrice() + " rubles.\n";
                info += "Time left: " + booking.getDaysLeft() + " days.\n";
                if (booking.doc.isReference()) info += "This is a reference document.\n";
                if (booking.doc instanceof Book) if(((Book) booking.doc).isBS()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of library books
        JLabel libLabel = new JLabel();
        libLabel.setText("Library documents");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of user's books
        JLabel userBooksLabel = new JLabel();
        userBooksLabel.setText("Your documents");
        userBooksLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(logoutBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(libLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(libDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(orderBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(userBooksLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userDocBox, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(infoDocBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(450, 640));
        pack();
        setLocationRelativeTo(null);
    }


}
