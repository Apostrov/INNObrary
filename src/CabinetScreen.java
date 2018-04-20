package main.java;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

class CabinetScreen extends JFrame {

    private String libDoc = null;
    private String userDoc = null;

    JTextField searchField;
    JPanel mainPanel = new JPanel();
    DocTableModel docModel;
    JTable libDocTable;

    /** Creates the window according to the type of the user (librarian or not) */
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

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // From top to bottom
        setResizable(false);

        // Table of library documents
        Box libDocBox = Box.createHorizontalBox();
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        docModel = new DocTableModel(Main.documents);
        libDocTable = new JTable(docModel);
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

        LinkedList<User> list = new LinkedList<>();
        if (Main.activeUser instanceof  Admin) {
            for (int i = 0; i < Main.users.size(); ++i)
                if (!(Main.users.get(i) instanceof Admin))
                    list.add(Main.users.get(i));
        } else {
            for (int i = 0; i < Main.users.size(); ++i)
                if (!(Main.users.get(i) instanceof Admin || Main.users.get(i) instanceof Librarian))
                    list.add(Main.users.get(i));
        }
        TableModel userModel = new UserTableModel(list);
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
        logoutBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.login.setLocationRelativeTo(null);
            Main.login.setVisible(true);
            Main.login.passField.setText(""); // Reset the password field
            DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() + "]--(" +
                    "The user has logged out.)");
        });
        // Requests button
        JButton requestsBtn = new JButton("Requests");
        requestsBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        requestsBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.requests = new RequestsScreen();
            Main.requests.setLocationRelativeTo(null);
            Main.requests.setVisible(true);
        });
        // Date modify button
        JButton dateBtn = new JButton("Set date");
        dateBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        dateBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.dateMod = new DateModifyScreen();
            Main.dateMod.setLocationRelativeTo(null);
            Main.dateMod.setVisible(true);
        });
        // Logs button
        JButton logsBtn = new JButton(" Logs ");
        logsBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logsBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.logScreen = new LogScreen();
            Main.logScreen.setVisible(true);
        });
        if (!(Main.activeUser instanceof Admin)) { logsBtn.setEnabled(false); logsBtn.setFocusPainted(false);
        logsBtn.setBorderPainted(false); }
        logoutBox.add(logoutBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(100, 0)));
        logoutBox.add(logsBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(5, 0)));
        logoutBox.add(dateBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(5, 0)));
        logoutBox.add(requestsBtn);

        // Buttons related to documents
        Box docBtnBox = Box.createHorizontalBox();
        // Add document button
        JButton addDocBtn = new JButton("Add new");
        addDocBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.docAdd.setLocationRelativeTo(null);
            Main.docAdd.setVisible(true);
        });
        addDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        if (Main.activeUser.getPriority() < 6) { addDocBtn.setEnabled(false); addDocBtn.setFocusPainted(false);
        addDocBtn.setBorderPainted(false); }
        docBtnBox.add(addDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Add copy of the document button
        JButton addDocCopyBtn = new JButton("Add copy");
        addDocCopyBtn.addActionListener(e -> {
            String docTitle = libDoc;
            if (docTitle != null) {
                Document doc = Main.findDoc(docTitle);
                doc.setCopies(doc.getCopies() + 1);
                DataBase.addDoc(doc);
                JOptionPane.showMessageDialog(mainPanel, "Copy added!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() + "]--(" +
                        "The user has added one copy to the document \"" + doc.getTitle() + "\".)");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to add a copy to some document.)");
            }
        });
        addDocCopyBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        if (Main.activeUser.getPriority() < 7) { addDocCopyBtn.setEnabled(false); addDocCopyBtn.setFocusPainted(false);
        addDocCopyBtn.setBorderPainted(false); }
        docBtnBox.add(addDocCopyBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Document info button
        JButton infoDocBtn = new JButton(" Info ");
        infoDocBtn.addActionListener(e -> {
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
                if (doc.isOutstanding()) info += "For this document is placed the outstanding request.";
                JOptionPane.showMessageDialog(mainPanel, info);
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() + "]--(" +
                        "The user has checked information about the document \"" + doc.getTitle() + "\".)");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to check the info of some document.)");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(infoDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Modify the document button
        JButton modifyDocBtn = new JButton("Modify");
        modifyDocBtn.addActionListener(e -> {
            if (libDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to modify some document.)");
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
        // Remove the document button
        JButton removeDocBtn = new JButton("Remove");
        removeDocBtn.addActionListener(e -> {
            if (libDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to remove some document.)");
            } else {
                // Check and create the list of the users (if any) that are ordering this document at the moment
                boolean docIsBooking = false;
                String userList = "";
                for (int i = 0; i < Main.users.size(); ++i) {
                    for (int j = 0; j < Main.users.get(i).getBookings().size(); ++j) {
                        if (Main.users.get(i).getBookings().get(j).getDoc().getTitle().equals(libDoc)){
                            docIsBooking = true;
                            userList += Main.users.get(i).getUsername() + " (" + Main.users.get(i).getFirstName() + " " + Main.users.get(i).getSecondName() + ")\n";
                        }
                    }
                }
                if (docIsBooking) {
                    // Forbid the removal of the document if some users are ordering it at the moment
                    JOptionPane.showMessageDialog(mainPanel, "The document is currently ordering!\n" +
                            "List of users who is ordering this document:\n" + userList);
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to remove the document \"" + Main.findDoc(libDoc).getTitle() + "\" that is currently ordering.)");
                } else {
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has removed the document\"" + Main.findDoc(libDoc).getTitle() + "\".)");
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
        if (Main.activeUser.getPriority() < 7) { removeDocBtn.setEnabled(false); removeDocBtn.setFocusPainted(false);
        removeDocBtn.setBorderPainted(false); }
        docBtnBox.add(removeDocBtn);

        // Buttons related to users
        Box userBtnBox = Box.createHorizontalBox();
        // View user info button
        JButton viewUserBtn = new JButton("Orders");
        viewUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        viewUserBtn.addActionListener(l -> {
            String username = userDoc;
            if (username != null) {
                Main.userView = new UserViewScreen(Main.findUser(username));
                Main.userView.setVisible(true);
                Main.userView.setLocationRelativeTo(null);
                Main.cabinet.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to see the orders of some user.)");
            }
        });
        // Add new user button
        JButton addUserBtn = new JButton("Add new");
        addUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addUserBtn.addActionListener(l -> {
            Main.cabinet.setVisible(false);
            Main.userAdd.setLocationRelativeTo(null);
            Main.userAdd.setVisible(true);
        });
        if (Main.activeUser.getPriority() < 6) { addUserBtn.setEnabled(false); addUserBtn.setFocusPainted(false);
        addUserBtn.setBorderPainted(false); }
        // Modify the user button
        JButton modifyUserBtn = new JButton("Modify");
        modifyUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        modifyUserBtn.addActionListener(l -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to modify some user.)");
            } else {
                Main.cabinet.setVisible(false);
                Main.userMod = new UserModifyScreen(Main.findUser(userDoc), null);
                Main.userMod.setLocationRelativeTo(null);
                Main.userMod.setVisible(true);
            }
        });
        // Remove the user button
        JButton removeUserBtn = new JButton("Remove");
        removeUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        removeUserBtn.addActionListener(l -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to remove some user.)");
            } else {
                if (Main.findUser(userDoc).getBookings().size() > 0) {
                    JOptionPane.showMessageDialog(mainPanel, "The user still has orders!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to remove the user \"" + Main.findUser(userDoc).getUsername() + "\" who still has orders.)");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "The user has been removed!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has removed the user \"" + Main.findUser(userDoc).getUsername() + "\".)");
                    DataBase.deleteUser(Main.findUser(userDoc));
                    Main.users.remove(Main.findUser(userDoc));
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(true);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        if (Main.activeUser.getPriority() < 7) { removeUserBtn.setEnabled(false); removeUserBtn.setFocusPainted(false);
        removeUserBtn.setBorderPainted(false); }
        // Debtors button
        JButton debtorUserBtn = new JButton("Debtors");
        debtorUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        debtorUserBtn.addActionListener(l -> {
            Main.cabinet.setVisible(false);
            Main.debtors.setLocationRelativeTo(null);
            Main.debtors.setVisible(true);
        });
        userBtnBox.add(addUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(viewUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(modifyUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(removeUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(debtorUserBtn);

        //Label of library documents
        JLabel libLabel = new JLabel();
        libLabel.setText("Library documents");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of users
        JLabel userLabel = new JLabel();
        userLabel.setText("Users");
        userLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // searchDocs box
        Box searchBox = Box.createHorizontalBox();
        searchBox.add(Box.createRigidArea(new Dimension(0, 0)));
        // searchDocs field
        searchField = new JTextField();
        searchField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        searchField.setMaximumSize(new Dimension(335, 30));
        searchField.setText("");
        searchField.addActionListener(e -> {
            if (!searchField.getText().equals("")) {
                String[] array = new String[Main.documents.size()];
                for (int i = 0; i < Main.documents.size(); ++i) array[i] = Main.documents.get(i).getTitle();
                ArrayList<String> searchTitles = searchDocs(searchField.getText(), array);
                ArrayList<Document> searchDocs = new ArrayList<>();
                for (int i = 0; i < searchTitles.size(); ++i) searchDocs.add(Main.findDoc(searchTitles.get(i)));
                docModel.replace(searchDocs);
                libDocTable.revalidate();
            } else {
                docModel.replace(Main.documents);
                libDocTable.revalidate();
            }
        });
        searchBox.add(searchField);
        searchBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // searchDocs button
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> {
            if (!searchField.getText().equals("")) {
                String[] array = new String[Main.documents.size()];
                for (int i = 0; i < Main.documents.size(); ++i) array[i] = Main.documents.get(i).getTitle();
                ArrayList<String> searchTitles = searchDocs(searchField.getText(), array);
                ArrayList<Document> searchDocs = new ArrayList<>();
                for (int i = 0; i < searchTitles.size(); ++i) searchDocs.add(Main.findDoc(searchTitles.get(i)));
                docModel.replace(searchDocs);
                libDocTable.revalidate();
            } else {
                docModel.replace(Main.documents);
                libDocTable.revalidate();
            }
        });
        searchBox.add(searchBtn);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(logoutBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(libLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(libDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(searchBox);
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

        if (Main.activeUser != null) {
            for (int i = 0; i < Main.activeUser.getNotifications().size(); ++i) {
                showNotification(Main.activeUser.getNotifications().get(i));
            }
            Main.activeUser.getNotifications().clear();
            DataBase.replaceNotifications(Main.activeUser);
        }

    }

    /** Initialization of the objects of the personal area page of patrons */
    private void createUserGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // Table of library books
        Box libDocBox = Box.createHorizontalBox();
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        docModel = new DocTableModel(Main.documents);
        libDocTable = new JTable(docModel);
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

        // Table of user (active account) documents
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
        logoutBtn.addActionListener(e -> {
            for (int i = 0; i < Main.users.size(); ++i)
                if (Main.users.get(i).getUsername().equals(Main.activeUser.getUsername()))
                    Main.users.get(i).copyData(Main.activeUser);
            Main.cabinet.setVisible(false);
            Main.login.setLocationRelativeTo(null);
            Main.login.setVisible(true);
            Main.login.passField.setText("");
            DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                    "]--(The user has log out.)");
        });
        // See the profile button
        JButton profileBtn = new JButton("My profile");
        profileBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        profileBtn.addActionListener(e -> {
            String info = "";
            info += "Information about me:\n";
            info += "Username:  " + Main.activeUser.getUsername() + "\n";
            info += "Password:  " + Main.activeUser.getPassword() + "\n";
            info += "First name:  " + Main.activeUser.getFirstName() + "\n";
            info += "Second name:  " + Main.activeUser.getSecondName() + "\n";
            info += "Address:  " + Main.activeUser.getAddress() + "\n";
            info += "Phone number:  " + Main.activeUser.getPhone() + "\n";
            if (Main.activeUser instanceof Student) info += "I am a student";
            else if (Main.activeUser instanceof Instructor) info += "I am an instructor";
            else if (Main.activeUser instanceof TA) info += "I am a teacher assistant";
            else if (Main.activeUser instanceof VisitingProfessor) info += "I am a visiting professor";
            else if (Main.activeUser instanceof Professor) info += "I am a professor";
            JOptionPane.showMessageDialog(mainPanel, info);
            DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                    "]--(The user has checked the information about himself.)");
        });
        // Change the profile button
        JButton changeProfBtn = new JButton("Change profile");
        changeProfBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        changeProfBtn.addActionListener(e -> {
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

        // Order the document button
        Box orderBox = Box.createHorizontalBox();
        JButton orderBtn = new JButton("Order");
        orderBtn.addActionListener(e -> {
            if (libDoc != null) {
                Document doc = Main.findDoc(libDoc);
                boolean alreadyHas = false; // Check whether the patron already has one copy of the book
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {
                    if (doc.getTitle().equals(Main.activeUser.getBookings().get(i).getDoc().getTitle()))
                        alreadyHas = true;
                }
                if (alreadyHas) {
                    JOptionPane.showMessageDialog(mainPanel, "You already have one copy of this document!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to check out the document \"" + doc.getTitle() + "\" that he already has.)");
                } else if (Main.actualCopies(doc.getTitle()) <= 0) {
                    JOptionPane.showMessageDialog(mainPanel, "There is no more such documents in the library!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to check out the document \"" + doc.getTitle() + "\" that is no more in the system.)");
                } else if (doc.getCopies() <= 0) {
                    if (doc.isOutstanding()) {
                        JOptionPane.showMessageDialog(mainPanel, "This document has been placed by an outstanding\n" +
                                "request. Please wait before it will be available.");
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has tried to check out the document \"" + doc.getTitle() + "\" for what is" +
                                " placed the outstanding request.)");
                    } else {
                        int num = 1, time = 1000000;
                        for (int i = 0; i < Main.documents.size(); ++i) {
                            if (doc.getTitle().equals(Main.documents.get(i).getTitle())) {
                                Main.priorityQueues.get(i).add(Main.activeUser);
                                num = Main.priorityQueues.get(i).size();
                                Main.cabinet.setVisible(false);
                                int duration = 21;                      // Usually it is 3 weeks
                                if (doc instanceof Book) if (doc.isBestSeller())
                                    duration = 14;                      // If the document is book-bestseller, then 2 weeks
                                if (Main.activeUser.isFaculty())
                                    duration = 28;                      // If user is faculty member, then 4 weeks
                                if (doc instanceof AudioVideo)
                                    duration = 14;                      // If the document is AV-material, then 2 weeks
                                if (Main.activeUser instanceof VisitingProfessor)
                                    duration = 7;    // Special duration for the VP
                                DataBase.doOrder(Main.activeUser, doc, duration, Main.date, false,
                                        false, false, false);

                                for (int j = 0; j < Main.users.size(); ++j) {
                                    Booking b = Main.users.get(j).findBooking(doc.getTitle());
                                    if (b != null) {
                                        if (time > b.getTimeLeft()) time = b.getTimeLeft();
                                    }
                                }

                                Main.activeUser.addBooking(new Booking(doc, duration, false));
                                Main.cabinet = new CabinetScreen(false);
                                Main.cabinet.setLocationRelativeTo(null);
                                Main.cabinet.setVisible(true);
                            }
                        }
                        JOptionPane.showMessageDialog(mainPanel, "All these documents are booked!\nYou have" +
                                " been added to the queue!\nYour number in the queue is " + num + "\n" +
                                "Approximate wating time is " + time + " days.");
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has tried to check out the document \"" + doc.getTitle() + "\"." +
                                " However all copies are on hands and the user was added to the queue.)");
                    }
                } else if (doc.isReference()) {
                    JOptionPane.showMessageDialog(mainPanel, "You cannot order a reference document!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to check out the reference document \"" + doc.getTitle() + "\".)");
                }else {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully ordered!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has checked out the document \"" + doc.getTitle() + "\".)");
                    Main.cabinet.setVisible(false);
                    doc.setCopies(doc.getCopies() - 1);
                    DataBase.addDoc(doc);
                    int duration = 21;                                                 // Usually it is 3 weeks
                    if (doc instanceof Book) if (doc.isBestSeller()) duration = 14;    // If the document is book-bestseller, then 2 weeks
                    if (Main.activeUser.isFaculty()) duration = 28;                    // If user is faculty member, then 4 weeks
                    if (doc instanceof AudioVideo) duration = 14;                      // If the document is AV-material, then 2 weeks
                    if (Main.activeUser instanceof VisitingProfessor) duration = 7;    // Special duration for the VP
                    DataBase.doOrder(Main.activeUser, doc, duration, Main.date, false, false,
                            true, false);
                    Main.activeUser.addBooking(new Booking(doc, duration, true));
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to check out some document.)");
            }
        });
        orderBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        orderBox.add(orderBtn);
        orderBox.add(Box.createRigidArea(new Dimension(5, 0)));

        Box orderBtnBox = Box.createHorizontalBox();
        // Info about order button
        JButton infoDocBtn = new JButton(" Order info ");
        infoDocBtn.addActionListener(e -> {
            if (userDoc != null) {
                Booking booking = Main.activeUser.findBooking(userDoc);
                String info = "";
                info += "Information about the order:\n";
                info += "Title:  " + booking.getDoc().getTitle() + ".\n";
                info += "Authors: " + booking.getDoc().getAuthors() + ".\n";
                info += "Price: " + booking.getDoc().getPrice() + " rubles.\n";
                if (booking.hasReceived()) {
                    info += "Date of booking: " + booking.getDate().toString() + ".\n";
                    info += "Time left: " + booking.getTimeLeft() + " days.\n";
                } else {
                    info += "Date of booking: unknown \n(the document is not received yet).\n";
                    info += "Time left: unknown \n(the document is not received yet).\n";
                }
                if (booking.getDoc().isReference()) info += "This is a reference document.\n";
                if (booking.getDoc() instanceof Book) if(booking.getDoc().isBestSeller()) info += "This is a bestseller book.\n";
                if (booking.isOverdue()) {
                    int fine = (-1 * booking.getTimeLeft()) * 100 >= booking.getDoc().getPrice() ?
                            booking.getDoc().getPrice() : (-1 * booking.getTimeLeft()) * 100;
                    info += "The booking is overdue. The fee is: " + fine + " rubles.";
                }
                JOptionPane.showMessageDialog(mainPanel, info);
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has checked the info about the order of the document" +
                        "\"" + booking.getDoc().getTitle() + "\".)");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to check the info of some of his orders.)");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // Return the order button
        JButton returnDocBtn = new JButton(" Return ");
        returnDocBtn.addActionListener(e -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to return some of his documents.)");
            } else {
                Booking booking = Main.activeUser.findBooking(userDoc);
                if (booking.hasRequestedByUser()) {
                    JOptionPane.showMessageDialog(mainPanel, "Already requested!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to return the document \"" + booking.getDoc().getTitle() + "\"" +
                            " but he has already send the request for that.)");
                } else if (!booking.hasReceived()) {
                    JOptionPane.showMessageDialog(mainPanel, "You have not received the document yet!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to return the document \"" + booking.getDoc().getTitle() + "\"" +
                            " but he has not recieved it yet.)");
                } else if (booking.hasRequestedByLib()) {
                    if (booking.isOverdue()) {
                        int fine = (-1 * booking.getTimeLeft()) * 100 >= booking.getDoc().getPrice()
                                ? booking.getDoc().getPrice() : (-1 * booking.getTimeLeft()) * 100;
                        JOptionPane.showMessageDialog(mainPanel, "Your order of document \"" +
                                booking.getDoc().getTitle() + "\" is overdue!\n" +
                                "For this reason you have to pay fee of " + fine + " rubles.");
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has been notified that he has to pay fee of " + fine + " rubles" +
                                " since the order of the document \"" + booking.getDoc().getTitle() + "\"" +
                                " is overdue.)");
                    }
                    JOptionPane.showMessageDialog(mainPanel, "The document has successfully returned!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has returned the document \"" + booking.getDoc().getTitle() + "\" to the system.)");
                    boolean hasFound = false;
                    for (int i = 0; i < Main.documents.size(); ++i) {
                        if (Main.documents.get(i).getTitle().equals(booking.getDoc().getTitle())) {
                            hasFound = true;
                            Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                            DataBase.addDoc(Main.documents.get(i));
                            DataBase.deleteOrder(Main.activeUser, booking.getDoc());
                            Main.activeUser.getBookings().remove(booking);
                            // Check for the users who has requested for this document
                            if (Main.priorityQueues.get(i).size() > 0) {
                                Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() - 1);
                                DataBase.addDoc(Main.documents.get(i));
                                User user = Main.priorityQueues.get(i).poll();
                                if (user == null) { Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                                    DataBase.addDoc(Main.documents.get(i));
                                    break;
                                }
                                user.notify(new Notification(1, Main.documents.get(i).getTitle(), "null",
                                        Main.date, 0));
                                DataBase.replaceNotifications(user);
                            }
                        }
                    }
                    if (!hasFound) {
                        booking.getDoc().setCopies(1);
                        Main.documents.add(booking.getDoc());
                        DataBase.addDoc(booking.getDoc());
                    }
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully requested for returning!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has send the request for returning the document \"" + booking.getDoc().getTitle() + "\".)");
                    for (int i = 0; i < Main.users.size(); ++i) {
                        if (Main.users.get(i) instanceof Admin || Main.users.get(i) instanceof Librarian) {
                            Main.users.get(i).notify(new Notification(7, booking.getDoc().getTitle(),
                                    Main.activeUser.getUsername(), Main.date, 0));
                            DataBase.replaceNotifications(Main.users.get(i));
                        }
                    }
                    booking.userRequest();
                    DataBase.doOrder(Main.activeUser, booking.getDoc(), booking.getDuration(), booking.getDate(),
                            booking.hasRequestedByLib(), booking.hasRequestedByUser(), booking.hasReceived(), booking.hasRenewed());
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        returnDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // Renew button
        JButton renewBtn = new JButton("  Renew  ");
        renewBtn.addActionListener(e -> {
            if (userDoc != null) {
                if (!Main.activeUser.findBooking(userDoc).hasReceived()) {
                    JOptionPane.showMessageDialog(mainPanel, "You have not received the document yet!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to renew the document \"" +
                            Main.activeUser.findBooking(userDoc).getDoc().getTitle() + "\"" +
                            " but he has not received it yet.)");
                } else if (Main.activeUser.findBooking(userDoc).isOverdue()) {
                    JOptionPane.showMessageDialog(mainPanel, "This booking is overdue!\n" +
                            "You should pay the fine!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to renew the document \""
                            + Main.activeUser.findBooking(userDoc).getDoc().getTitle() +
                            "\" but the order is overdue.)");
                } else if (Main.findDoc(userDoc).isOutstanding()) {
                    JOptionPane.showMessageDialog(mainPanel, "The outstanding request is\nplaced for this document!\n" +
                            "You cannot renew it!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to renew the document \"" +
                            Main.findDoc(userDoc).getTitle() + "\" but it has the outstanding request placed.)");
                } else if (Main.activeUser.findBooking(Main.findDoc(userDoc).getTitle()).hasRenewed() &&
                        !(Main.activeUser instanceof VisitingProfessor)) {
                    JOptionPane.showMessageDialog(mainPanel, "You have already renewed this document!\nYou are not" +
                            "allowed to do it again!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has tried to renew the document \"" +
                            Main.activeUser.findBooking(userDoc).getDoc().getTitle() + "\" but he has" +
                            " already renewed it once.)");
                } else {
                    Booking booking = Main.activeUser.findBooking(userDoc);
                    Main.cabinet.setVisible(false);
                    int duration = 21; // Usually it is 3 weeks
                    if (booking.getDoc() instanceof Book) if (booking.getDoc().isBestSeller())
                        duration = 14; // If the document is book-bestseller, then 2 weeks
                    if (Main.activeUser.isFaculty())
                        duration = 28; // If user is faculty member, then 4 weeks
                    if (booking.getDoc() instanceof AudioVideo)
                        duration = 14; // If the document is AV-material, then 2 weeks
                    if (Main.activeUser instanceof VisitingProfessor)
                        duration = 7;  // Special duration for the VP
                    booking.setRenewed();
                    booking.setDate(Main.date);
                    DataBase.doOrder(Main.activeUser, booking.getDoc(), duration, Main.date, false,
                            false, true, true);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                    JOptionPane.showMessageDialog(mainPanel, "Successfully renewed for " + duration + " days!");
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has renewed the document \"" + booking.getDoc().getTitle() + "\" " +
                            "for " + duration + " days.)");
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has tried to renew some of his documents.)");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        orderBtnBox.add(infoDocBtn);
        orderBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        orderBtnBox.add(returnDocBtn);
        orderBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        orderBtnBox.add(renewBtn);

        //Label of library documents
        JLabel libLabel = new JLabel();
        libLabel.setText("Library documents");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of user documents
        JLabel userBooksLabel = new JLabel();
        userBooksLabel.setText("Your documents");
        userBooksLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // searchDocs box
        Box searchBox = Box.createHorizontalBox();
        searchBox.add(Box.createRigidArea(new Dimension(0, 0)));
        // searchDocs field
        searchField = new JTextField();
        searchField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        searchField.setMaximumSize(new Dimension(335, 30));
        searchField.setText("");
        searchField.addActionListener(e -> {
            if (!searchField.getText().equals("")) {
                String[] array = new String[Main.documents.size()];
                for (int i = 0; i < Main.documents.size(); ++i) array[i] = Main.documents.get(i).getTitle();
                ArrayList<String> searchTitles = searchDocs(searchField.getText(), array);
                ArrayList<Document> searchDocs = new ArrayList<>();
                for (int i = 0; i < searchTitles.size(); ++i) searchDocs.add(Main.findDoc(searchTitles.get(i)));
                docModel.replace(searchDocs);
                libDocTable.revalidate();
            } else {
                docModel.replace(Main.documents);
                libDocTable.revalidate();
            }
        });
        searchBox.add(searchField);
        searchBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // searchDocs button
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> {
            if (!searchField.getText().equals("")) {
                String[] array = new String[Main.documents.size()];
                for (int i = 0; i < Main.documents.size(); ++i) array[i] = Main.documents.get(i).getTitle();
                ArrayList<String> searchTitles = searchDocs(searchField.getText(), array);
                ArrayList<Document> searchDocs = new ArrayList<>();
                for (int i = 0; i < searchTitles.size(); ++i) searchDocs.add(Main.findDoc(searchTitles.get(i)));
                docModel.replace(searchDocs);
                libDocTable.revalidate();
            } else {
                docModel.replace(Main.documents);
                libDocTable.revalidate();
            }
        });
        searchBox.add(searchBtn);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(profileBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(libLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(libDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(searchBox);
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

        if (Main.activeUser != null) {
            for (int i = 0; i < Main.activeUser.getNotifications().size(); ++i) {
                showNotification(Main.activeUser.getNotifications().get(i));
            }
            Main.activeUser.getNotifications().clear();
            DataBase.replaceNotifications(Main.activeUser);
        }
    }

    private void showNotification (Notification n) {
        switch (n.getType()) {
            case 1: { // Document receiving
                if (Main.date.compareTo(n.getDate()) > 0 && Main.date.getDay() != n.getDate().getDay()) {
                    // Notification about not receiving of the requested document
                    JOptionPane.showMessageDialog(mainPanel, "You have been removed from the queue\n" +
                            "due to your absense.");
                    DataBase.deleteOrder(Main.activeUser, Main.findDoc(n.getDoc()));
                    Main.activeUser.getBookings().remove(Main.activeUser.findBooking(n.getDoc()));
                    for (int i = 0; i < Main.documents.size(); ++i) {
                        if (Main.documents.get(i).getTitle().equals(n.getDoc())) {
                            Main.priorityQueues.get(i).poll();
                            User u = Main.priorityQueues.get(i).poll();
                            if (u == null) {
                                Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                                DataBase.addDoc(Main.documents.get(i));
                                break;
                            }
                            u.notify(new Notification(1, n.getDoc(), "null", Main.date, 0));
                            DataBase.replaceNotifications(u);
                        }
                    }
                    DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                            "]--(The user has received the notification about removal from the queue due to " +
                            "his absense.)");
                } else {
                    // Dialog window about receiving or rejection of the requested document
                    String info = "";
                    info += "The requested document is available.\n" +
                            "Do you want to order it?\n";
                    Object[] obj = new Object[]{"Yes","No"};
                    info += "Document's title: " + n.getDoc() + "\n";
                    int result = JOptionPane.showOptionDialog(mainPanel,
                            info,
                            "Message",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            obj,
                            obj[0]);
                    if (result == JOptionPane.YES_OPTION) {
                        Booking b = Main.activeUser.findBooking(n.getDoc());
                        b.setReceived();
                        DataBase.doOrder(Main.activeUser, b.getDoc(), b.getDuration(), b.getDate(), b.hasRequestedByLib(),
                                b.hasRequestedByUser(),
                        true, false);
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has received the notification about availability of the document" +
                                "\"" + n.getDoc() + "\" and has ordered it.)");
                    } else {
                        DataBase.deleteOrder(Main.activeUser, Main.activeUser.findBooking(n.getDoc()).getDoc());
                        Main.activeUser.getBookings().remove(Main.activeUser.findBooking(n.getDoc()));
                        for (int i = 0; i < Main.documents.size(); ++i) {
                            if (Main.documents.get(i).getTitle().equals(n.getDoc())) {
                                Main.priorityQueues.get(i).poll();
                                User u = Main.priorityQueues.get(i).poll();
                                if (u != null) {
                                    u.notify(new Notification(1, Main.documents.get(i).getTitle(), "null",
                                            Main.date, 0));
                                    DataBase.replaceNotifications(u);
                                } else {
                                    Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                                    DataBase.addDoc(Main.documents.get(i));
                                }
                            }
                        }
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has received the notification about availability of the document" +
                                "\"" + n.getDoc() + "\" and has not ordered it.)");
                    }
                }
                break;
            }
            case 2: {
                JOptionPane.showMessageDialog(mainPanel, "You have been removed from the queue\n" +
                        "due to the outstanding request.");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has received the notification about removal from the queue due to the " +
                        "outstanding request was placed.)");
                break;
            }
            case 3: {
                JOptionPane.showMessageDialog(mainPanel, "Your order of document \"" + n.getDoc() + "\" is overdue!\n" +
                        "For this reason you have to pay fee of " + n.getFine() + " rubles.");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has received the notification about the fee of " + n.getFine() +" rubles" +
                        " of the document \"" + n.getDoc() + "\".)");
                break;
            }
            case 4: {
                JOptionPane.showMessageDialog(mainPanel, "You have received the request for returning the document \"" +
                        n.getDoc() + "\".");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has received the notification about receiving the request for" +
                        " returning the document \"" + n.getDoc() + "\".)");
                break;
            }
            case 5: {
                JOptionPane.showMessageDialog(mainPanel, "For the document \"" + n.getDoc() +
                        "\" was placed the outstanding request\n" + "You have to return the document immediatly.");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has received the notification about the outstaning request that was" +
                        " placed for the document \"" + n.getDoc() + "\".)");
                break;
            }
            case 6: {
                JOptionPane.showMessageDialog(mainPanel, "The document \"" + n.getDoc() + "\" is now available!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has received the notification about the availability of the document" +
                        " \"" + n.getDoc() + "\".)");
                break;
            }
            case 7: {
                JOptionPane.showMessageDialog(mainPanel, "The user \"" + n.getUser() + "\" requested for returning\n" +
                        "of the document \"" + n.getDoc() + "\"!");
                DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                        "]--(The user has received the notification about the receiving of the request for" +
                        " returning of the document \"" + n.getDoc() + "\" from the user " + n.getUser() + ".)");
                break;
            }
            default: { break; }
        }
    }

    public ArrayList<String> searchDocs(String word, String[] array){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            int tag = editdist(word,array[i]);
            if(tag == 0){
                list.add(array[i]);
                continue;
            }
            if(tag <= 3) {
                list.add(array[i]);
            }else{
                if (word.length()<=array[i].length()) {
                    if (word.equals(range(array[i], 0, word.length())) || editdist(word,range(array[i],0,word.length())) <= 3) {
                        list.add(array[i]);
                    }
                }
                else{
                    if(range(word,0,array[i].length()).equals(array[i]) || editdist(range(word,0,array[i].length()),array[i]) <= 3){
                        list.add(array[i]);

                    }
                }
            }


        }

        return list;

    }
    public static String range(String a, int Brange, int Erange){
        String b = "";
        for (int i = Brange; i < Erange; i++) {
            b = b + a.charAt(i);
        }


        return b;
    }

    public int editdist(String S1, String S2) {
        int m = S1.length(), n = S2.length();
        int[] D1;
        int[] D2 = new int[n + 1];

        for(int i = 0; i <= n; i ++)
            D2[i] = i;

        for(int i = 1; i <= m; i ++) {
            D1 = D2;
            D2 = new int[n + 1];
            for(int j = 0; j <= n; j ++) {
                if(j == 0) D2[j] = i;
                else {
                    int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
                    if(D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if(D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }

}
