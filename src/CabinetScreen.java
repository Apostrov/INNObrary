import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class CabinetScreen extends JFrame {

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

        // List of library books
        Box libBookBox = Box.createHorizontalBox(); // Additional container to represent the element in the horizontal way
        libBookBox.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> libBookTitles = new ArrayList<>();
        for (Document d : Main.documents) libBookTitles.add(d.title);
        JList<String> libBookList = new JList<>(libBookTitles.toArray(new String[libBookTitles.size()]));
        libBookList.setLayoutOrientation(JList.VERTICAL);
        libBookList.setVisibleRowCount(0);

        JScrollPane libBookScroll = new JScrollPane(libBookList);
        libBookScroll.setPreferredSize(new Dimension(50, 50)); // Indentation
        libBookScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        /*libBookList.addListSelectionListener(e -> {
            // What to do when the element of libBookList was selected
        });*/
        libBookBox.add(libBookScroll);
        libBookBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of users
        Box userBox = Box.createHorizontalBox();
        userBox.add(Box.createRigidArea(new Dimension(10, 0)));

        String[] userNames = new String[Main.users.size()];
        for (int i = 0; i < Main.users.size(); ++i) userNames[i] = Main.users.get(i).getUsername();
        JList<String> userList = new JList<>(userNames);
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setVisibleRowCount(0);

        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(50, 50));
        userScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        /*userList.addListSelectionListener(e -> {
            What to do when the element of userList was selected
        });*/
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
        logoutBox.add(Box.createRigidArea(new Dimension(215, 0)));

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
            String docTitle = libBookList.getSelectedValue();
            if (docTitle != null) {
                Document doc = Main.findDoc(docTitle);
                String info = "";
                info += "Information about the book:\n";
                info += "Title:  " + doc.title + "\n";
                info += "Authors:  ";
                for (int i = 0; i < doc.authors.size(); ++i)
                    info += doc.authors.get(i) + (doc.authors.size() > 1 ? "," : "") + " ";
                info += "\n";
                info += "Price:  " + doc.price + " rubles.\n";
                info += "Copies left:  " + doc.getCopies() + "\n";
                if (doc.isReference()) info += "This is a reference document.\n";
                if (doc instanceof Book) if(((Book) doc).isBS()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the book!");
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
            String username = userList.getSelectedValue();
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
        libLabel.setText("Library books");
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
        mainPanel.add(libBookBox);
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

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    /** Initialization of the objects of the personal area page of patrons */
    private void createUserGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // List of library books
        Box bookBox = Box.createHorizontalBox();
        bookBox.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> libBookTitles = new ArrayList<>();
        for (Document d : Main.documents) libBookTitles.add(d.title);
        JList<String> libBookList = new JList<>(libBookTitles.toArray(new String[libBookTitles.size()]));
        libBookList.setLayoutOrientation(JList.VERTICAL);
        libBookList.setVisibleRowCount(0);

        JScrollPane libBookScroll = new JScrollPane(libBookList);
        libBookScroll.setPreferredSize(new Dimension(50, 50));
        libBookScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        /*libBookList.addListSelectionListener(e -> {
            // What to do when the element of libBookList was selected
        });*/
        bookBox.add(libBookScroll);
        bookBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of user books
        Box userBookBox = Box.createHorizontalBox();
        userBookBox.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> userBookTitles = new ArrayList<>();
        if (Main.activeUser != null) for (Booking b : Main.activeUser.getBookings()) userBookTitles.add(b.doc.title);
        JList<String> userBookList = new JList<>(userBookTitles.toArray(new String[userBookTitles.size()]));
        userBookList.setLayoutOrientation(JList.VERTICAL);
        userBookList.setVisibleRowCount(0);

        JScrollPane userBookScroll = new JScrollPane(userBookList);
        userBookScroll.setPreferredSize(new Dimension(50, 50));
        userBookScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        /*userBookList.addListSelectionListener(e -> {
            // What to do when the element of libBookList was selected
        });*/
        userBookBox.add(userBookScroll);
        userBookBox.add(Box.createRigidArea(new Dimension(10, 0)));

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
        logoutBox.add(Box.createRigidArea(new Dimension(215, 0)));

        // Order button
        Box orderBox = Box.createHorizontalBox();
        JButton orderBtn = new JButton("Order");
        orderBtn.addActionListener(e -> { // What to do when order the book button was pressed
            if (libBookList.getSelectedValue() != null) {
                Document doc = Main.findDoc(libBookList.getSelectedValue());
                String info = "";
                info += "Title:  " + doc.title + "\n";
                info += "Authors:  ";
                for (int i = 0; i < doc.authors.size(); ++i)
                    info += doc.authors.get(i) + (doc.authors.size() > 1 ? "," : "") + " ";
                info += "\n";
                info += "Price:  " + doc.price + " rubles.\n";
                info += "Copies left:  " + doc.getCopies() + "\n";
                if (doc.isReference()) info += "This is a reference document.\n";
                if (doc instanceof Book) if (((Book) doc).isBS()) info += "This is a bestseller book.\n";
                info += "\n";
                info += "Would you like to order this document?";
                int result = JOptionPane.showConfirmDialog(mainPanel, info, "Information", JOptionPane.YES_NO_OPTION);
                if (result == 0) { // If the result is equals to 0 then user pressed the button 'Yes'
                    boolean alreadyHas = false; // Check whether the patron already has one copy of the book
                    for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {
                        if (doc.title.equals(Main.activeUser.getBookings().get(i).doc.title))
                            alreadyHas = true;
                    }
                    if (doc.getCopies() <= 0) {
                        JOptionPane.showMessageDialog(mainPanel, "There is no more such documents!");
                    } else if (doc.isReference()) {
                        JOptionPane.showMessageDialog(mainPanel, "I am sorry, you cannot order a reference document.");
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
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the book!");
            }
        });
        orderBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        orderBox.add(orderBtn);
        orderBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Info about order button
        JButton infoBookBtn = new JButton(" Order info ");
        infoBookBtn.addActionListener(e -> { // What to do when info about the order button was pressed
            String docTitle = userBookList.getSelectedValue();
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
                info += "Price: " + booking.doc.price + " rubles.\n";
                info += "Time left: " + booking.getDaysLeft() + " days.\n";
                if (booking.doc.isReference()) info += "This is a reference document.\n";
                if (booking.doc instanceof Book) if(((Book) booking.doc).isBS()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
            }
        });
        infoBookBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of library books
        JLabel libLabel = new JLabel();
        libLabel.setText("Library books");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of user's books
        JLabel userBooksLabel = new JLabel();
        userBooksLabel.setText("Your books");
        userBooksLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(logoutBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(libLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(bookBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(orderBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(userBooksLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userBookBox, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(infoBookBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }


}