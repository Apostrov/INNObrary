import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

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
            Main.login.setLocationRelativeTo(null);
            Main.login.setVisible(true);
            Main.login.passField.setText(""); // Reset the password field
        });
        logoutBox.add(logoutBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(325, 0)));

        // Buttons related to books
        Box docBtnBox = Box.createHorizontalBox();
        // Add button
        JButton addDocBtn = new JButton("Add new");
        addDocBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.docAdd.setLocationRelativeTo(null);
            Main.docAdd.setVisible(true);
        });
        addDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(addDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        JButton addDocCopyBtn = new JButton("Add copy");
        addDocCopyBtn.addActionListener(e -> {
            String docTitle = libDoc;
            if (docTitle != null) {
                Document doc = Main.findDoc(docTitle);
                doc.setCopies(doc.getCopies() + 1);
                DataBase.addDoc(doc);
                JOptionPane.showMessageDialog(mainPanel, "Copy added!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            }
        });
        addDocCopyBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(addDocCopyBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Info button
        JButton infoDocBtn = new JButton(" Info ");
        infoDocBtn.addActionListener(e -> { // What to do when info button was pressed
            String docTitle = libDoc;
            if (docTitle != null) {
                Document doc = Main.findDoc(docTitle);
                String info = "";
                info += "Information about the document:\n";
                info += "Title:  " + doc.getTitle() + "\n";
                info += "Authors:  " + doc.getAuthors() + "\n";
                info += "Price:  " + doc.getPrice() + " rubles.\n";
                info += "Copies left:  " + doc.getCopies() + "\n";
                if (doc.isReference()) info += "This is a reference document.\n";
                if (doc instanceof Book) if (doc.isBestSeller()) info += "This is a bestseller book.\n";
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
            if (libDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            } else {
                Main.docMod = new DocModifyScreen(Main.findDoc(libDoc));
                Main.docMod.setLocationRelativeTo(null);
                Main.docMod.setVisible(true);
                Main.cabinet.setVisible(false);
            }
        });
        modifyDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(modifyDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Remove button
        JButton removeDocBtn = new JButton("Remove");
        removeDocBtn.addActionListener(e -> {
            if (libDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            } else {
                boolean docIsBooking = false;
                String userList = "";
                for (int i = 0; i < Main.users.size(); ++i) {
                    for (int j = 0; j < Main.users.get(i).getBookings().size(); ++j) {
                        if (Main.users.get(i).getBookings().get(j).getDoc().getTitle().equals(libDoc)){
                            docIsBooking = true;
                            userList += Main.users.get(i).getFirstName() + " " + Main.users.get(i).getSecondName() + "\n";
                        }
                    }
                }
                if (docIsBooking) {
                    JOptionPane.showMessageDialog(mainPanel, "The document is currently ordering!\nList of users who is ordering this document:\n" + userList);
                } else {
                    DataBase.deleteDoc(Main.findDoc(libDoc));
                    Main.documents.remove(Main.findDoc(libDoc));
                    JOptionPane.showMessageDialog(mainPanel, "The document has been removed!");
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(true);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        removeDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(removeDocBtn);

        // Buttons related to users
        Box userBtnBox = Box.createHorizontalBox();
        // View user button
        JButton viewUserBtn = new JButton(" Info ");
        viewUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        viewUserBtn.addActionListener(l -> {
            String username = userDoc;
            if (username != null) { // What to do when view button (view user) was pressed
                Main.userView = new UserViewScreen(Main.findUser(username));
                Main.userView.setVisible(true);
                Main.userView.setLocationRelativeTo(null);
                Main.cabinet.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            }
        });
        JButton addUserBtn = new JButton("Add new");
        addUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addUserBtn.addActionListener(l -> {
            Main.cabinet.setVisible(false);
            Main.userAdd.setLocationRelativeTo(null);
            Main.userAdd.setVisible(true);
        });
        JButton modifyUserBtn = new JButton("Modify");
        modifyUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        modifyUserBtn.addActionListener(l -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            } else {
                Main.cabinet.setVisible(false);
                Main.userMod = new UserModifyScreen(Main.findUser(userDoc));
                Main.userMod.setLocationRelativeTo(null);
                Main.userMod.setVisible(true);
            }
        });
        JButton removeUserBtn = new JButton("Remove");
        removeUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        removeUserBtn.addActionListener(l -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "The user has been removed!");
                DataBase.deleteUser(Main.findUser(userDoc));
                Main.users.remove(Main.findUser(userDoc));
                Main.cabinet.setVisible(false);
                Main.cabinet = new CabinetScreen(true);
                Main.cabinet.setLocationRelativeTo(null);
                Main.cabinet.setVisible(true);
            }
        });
        userBtnBox.add(addUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(viewUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(modifyUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(removeUserBtn);

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
        mainPanel.add(userBtnBox);
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
        Box profileBox = Box.createHorizontalBox();
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logoutBtn.addActionListener(e -> { // What to do when logout button was pressed
            for (int i = 0; i < Main.users.size(); ++i)
                if (Main.users.get(i).getUsername().equals(Main.activeUser.getUsername()))
                    Main.users.get(i).copyData(Main.activeUser);
            Main.cabinet.setVisible(false);
            Main.login.setLocationRelativeTo(null);
            Main.login.setVisible(true);
            Main.login.passField.setText("");
        });
        JButton profileBtn = new JButton("My profile");
        profileBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        profileBtn.addActionListener(e -> { // What to do when my profile button was pressed
            String info = "";
            info += "Information about me:\n";
            info += "Username:  " + Main.activeUser.getUsername() + "\n";
            info += "Password:  " + Main.activeUser.getPassword() + "\n";
            info += "First name:  " + Main.activeUser.getFirstName() + "\n";
            info += "Second name:  " + Main.activeUser.getSecondName() + "\n";
            info += "Address:  " + Main.activeUser.getAddress() + "\n";
            info += "Phone number:  " + Main.activeUser.getPhone() + "\n";
            if (Main.activeUser.isFaculty())
                info += "I am a faculty member\n";
            else
                info += "I am a student\n";
            JOptionPane.showMessageDialog(mainPanel, info);
        });
        JButton changeProfBtn = new JButton("Change profile");
        changeProfBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        changeProfBtn.addActionListener(e -> { // What to do when my profile button was pressed
            Main.changeProf = new ProfChangeScreen(Main.findUser(Main.activeUser.getUsername()));
            Main.cabinet.setVisible(false);
            Main.changeProf.setLocationRelativeTo(null);
            Main.changeProf.setVisible(true);
        });
        profileBox.add(logoutBtn);
        profileBox.add(Box.createRigidArea(new Dimension(125, 0)));
        profileBox.add(profileBtn);
        profileBox.add(Box.createRigidArea(new Dimension(5, 0)));
        profileBox.add(changeProfBtn);

        // Order button
        Box orderBox = Box.createHorizontalBox();
        JButton orderBtn = new JButton("Order");
        orderBtn.addActionListener(e -> { // What to do when order the book button was pressed
            if (libDoc != null) {
                Document doc = Main.findDoc(libDoc);
                boolean alreadyHas = false; // Check whether the patron already has one copy of the book
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {
                    if (doc.getTitle().equals(Main.activeUser.getBookings().get(i).getDoc().getTitle()))
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
                    DataBase.addDoc(doc);
                    int duration = 21;                                                 // Usually it is 3 weeks
                    if (Main.activeUser.isFaculty()) duration = 28;                    // If user is faculty member, then 4 weeks
                    if (doc instanceof Book) if (doc.isBestSeller()) duration = 14;    // If the document is book-bestseller, then 2 weeks
                    if (doc instanceof AudioVideo) duration = 14;                      // If the document is AV-material, then 2 weeks
                    DataBase.doOrder(Main.activeUser, doc, duration, false, false);
                    Main.activeUser.addBooking(new Booking(doc, duration));
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            }
        });
        orderBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        orderBox.add(orderBtn);
        orderBox.add(Box.createRigidArea(new Dimension(5, 0)));

        Box orderBtnBox = Box.createHorizontalBox();
        // Info about order button
        JButton infoDocBtn = new JButton(" Order info ");
        infoDocBtn.addActionListener(e -> { // What to do when info about the order button was pressed
            String docTitle = userDoc;
            if (docTitle != null) {
                Booking booking = Main.activeUser.getBookings().get(0);             // Here we need a booking because
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {    // we should provide the time that is left
                    if (Main.activeUser.getBookings().get(i).getDoc().getTitle().equals(docTitle))
                        booking = Main.activeUser.getBookings().get(i);
                }
                String info = "";
                info += "Information about the order:\n";
                info += "Title:  " + booking.getDoc().getTitle() + "\n";
                info += "Authors: " + booking.getDoc().getAuthors() + "\n";
                info += "Price: " + booking.getDoc().getPrice() + " rubles.\n";
                info += "Time left: " + booking.getTimeLeft() + " days.\n";
                if (booking.getDoc().isReference()) info += "This is a reference document.\n";
                if (booking.getDoc() instanceof Book) if(booking.getDoc().isBestSeller()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // Info about order button
        JButton returnDocBtn = new JButton(" Return ");
        returnDocBtn.addActionListener(e -> { // What to do when return about the order button was pressed
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
            } else {
                Booking booking = Main.activeUser.getBookings().get(0);             // Here we need a booking because
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {    // we should provide the time that is left
                    if (Main.activeUser.getBookings().get(i).getDoc().getTitle().equals(userDoc))
                        booking = Main.activeUser.getBookings().get(i);
                }
                if (booking.hasRequestedByUser()) {
                    JOptionPane.showMessageDialog(mainPanel, "Already requested!");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully requested!");
                    booking.userRequest();
                    DataBase.doOrder(Main.activeUser, booking.getDoc(), booking.getTimeLeft(), booking.hasRequestedByLib(), booking.hasRequestedByUser());
                    //DataBase.deleteOrder(Main.activeUser, booking.getDoc());
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        returnDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        orderBtnBox.add(infoDocBtn);
        orderBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        orderBtnBox.add(returnDocBtn);

        //Label of library books
        JLabel libLabel = new JLabel();
        libLabel.setText("Library documents");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of user's books
        JLabel userBooksLabel = new JLabel();
        userBooksLabel.setText("Your documents");
        userBooksLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(profileBox);
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
        mainPanel.add(orderBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);


        setPreferredSize(new Dimension(450, 640));
        pack();
        setLocationRelativeTo(null);
    }

}
