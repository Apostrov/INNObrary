import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class CabinetScreen extends JFrame {

    private String selectedLibBook = "";

    CabinetScreen(boolean isLibrarian) {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            if (isLibrarian)
                createLibGUI();
            else
                createUserGUI();
        });
    }

    private void createLibGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // List of library books
        Box booksList = Box.createHorizontalBox();
        booksList.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> libBookTitles = new ArrayList<>();
        for (Document d : Main.documents) libBookTitles.add(d.title);
        JList<String> library = new JList<>(libBookTitles.toArray(new String[libBookTitles.size()]));
        library.setLayoutOrientation(JList.VERTICAL);
        library.setVisibleRowCount(0);

        JScrollPane libScroll = new JScrollPane(library);
        libScroll.setPreferredSize(new Dimension(50, 50));
        libScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        library.addListSelectionListener(e -> {

        });
        booksList.add(libScroll);
        booksList.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of users
        Box userList = Box.createHorizontalBox();
        userList.add(Box.createRigidArea(new Dimension(10, 0)));

        String[] userNames = new String[Main.users.size()];
        for (int i = 0; i < Main.users.size(); ++i) userNames[i] = Main.users.get(i).getUsername();
        JList<String> userary = new JList<>(userNames);
        userary.setLayoutOrientation(JList.VERTICAL);
        userary.setVisibleRowCount(0);

        JScrollPane userScroll = new JScrollPane(userary);
        userScroll.setPreferredSize(new Dimension(50, 50));
        userScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userary.addListSelectionListener(e -> {

        });
        userList.add(userScroll);
        userList.add(Box.createRigidArea(new Dimension(10, 0)));

        // Log out button
        Box logoutBox = Box.createHorizontalBox();
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.login.setVisible(true);
            Main.login.passwordField.setText("");
        });
        logoutBox.add(logoutBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(215, 0)));

        // Buttons related to books
        Box bookButtons = Box.createHorizontalBox();
        // Add button
        JButton addBookBtn = new JButton("  Add  ");
        addBookBtn.addActionListener(e -> {

        });
        addBookBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        bookButtons.add(addBookBtn);
        bookButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        // Info button
        JButton infoBookBtn = new JButton(" Info ");
        infoBookBtn.addActionListener(e -> {
            String document = library.getSelectedValue();
            if (document != null) {
                Document doc = Main.findDoc(document);
                String info = "";
                info += "Information about the book:\n";
                info += "Title: " + doc.title + "\n";
                info += "Authors: ";
                for (int i = 0; i < doc.authors.size(); ++i)
                    info += doc.authors.get(i) + " ";
                info += "\n";
                info += "Price: " + doc.price + "\n";
                info += "Copies: " + doc.getCopies() + "\n";
                if (doc.isReference()) info += "This is a reference document.\n";
                if (doc instanceof Book) if(((Book) doc).isBS()) info += "This is a bestseller book.\n";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the book!");
            }
        });
        infoBookBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        bookButtons.add(infoBookBtn);
        bookButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        // Modify button
        JButton modifyBookBtn = new JButton("Modify");
        modifyBookBtn.addActionListener(e -> {

        });
        modifyBookBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        bookButtons.add(modifyBookBtn);
        bookButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        // Remove button
        JButton removeBookBtn = new JButton("Remove");
        removeBookBtn.addActionListener(e -> {

        });
        removeBookBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        bookButtons.add(removeBookBtn);

        // User view button
        JButton userViewBtn = new JButton("View");
        userViewBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userViewBtn.addActionListener(l -> {
            String user = userary.getSelectedValue();
            if (user != null) {
                Main.userView = new UserViewScreen(Main.findUser(user));
                Main.userView.setVisible(true);
                Main.cabinet.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Choose the user!");
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
        mainPanel.add(booksList);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(bookButtons, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(userLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userList);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userViewBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     *
     * TODO:
     *
     * */
    private void createUserGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // List of library books
        Box booksList = Box.createHorizontalBox();
        booksList.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> libBookTitles = new ArrayList<>();
        for (Document d : Main.documents) libBookTitles.add(d.title);
        JList<String> library = new JList<>(libBookTitles.toArray(new String[libBookTitles.size()]));
        library.setLayoutOrientation(JList.VERTICAL);
        library.setVisibleRowCount(0);

        JScrollPane libScroll = new JScrollPane(library);
        libScroll.setPreferredSize(new Dimension(50, 50));
        libScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);


        library.addListSelectionListener(e -> selectedLibBook = library.getSelectedValue());
        booksList.add(libScroll);
        booksList.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of user books
        Box userBooksList = Box.createHorizontalBox();
        userBooksList.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> userBookTitles = new ArrayList<>();
        if (Main.activeUser != null) for (Booking b : Main.activeUser.getBookings()) userBookTitles.add(b.doc.title);
        JList<String> ubList = new JList<>(userBookTitles.toArray(new String[userBookTitles.size()]));
        ubList.setLayoutOrientation(JList.VERTICAL);
        ubList.setVisibleRowCount(0);

        JScrollPane userScroll = new JScrollPane(ubList);
        userScroll.setPreferredSize(new Dimension(50, 50));
        userScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        ubList.addListSelectionListener(e -> {

        });
        userBooksList.add(userScroll);
        userBooksList.add(Box.createRigidArea(new Dimension(10, 0)));

        // Log out button
        Box logoutBox = Box.createHorizontalBox();
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            for (int i = 0; i < Main.users.size(); ++i)
                if (Main.users.get(i).getUsername().equals(Main.activeUser.getUsername()))
                    Main.users.get(i).setBookings(Main.activeUser.getBookings());
            Main.cabinet.setVisible(false);
            Main.login.setVisible(true);
            Main.login.passwordField.setText("");
        });
        logoutBox.add(logoutBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(215, 0)));

        // Order button
        Box bookButtons = Box.createHorizontalBox();
        JButton addBookBtn = new JButton("Order");
        addBookBtn.addActionListener(e -> {
            Document doc = Main.findDoc(selectedLibBook);
            String info = "";
            info += "Title: " + doc.title + "\n";
            info += "Authors: ";
            for (int i = 0; i < doc.authors.size(); ++i)
                info += doc.authors.get(i) + " ";
            info += "\n";
            info += "Price: " + doc.price + "\n";
            info += "Copies: " + doc.getCopies() + "\n";
            if (doc.isReference()) info += "This is a reference document.\n";
            if (doc instanceof Book) if(((Book) doc).isBS()) info += "This is a bestseller book.\n";
            info += "\n";
            info += "Would you like to offer this document?";
            int result = JOptionPane.showConfirmDialog(mainPanel, info, "Information", JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                boolean alreadyHas = false;
                for (int i = 0; i < Main.documents.size(); ++i) {
                    for (int j = 0; j < Main.activeUser.getBookings().size(); ++j) {
                        if (Main.documents.get(i).title.equals(Main.activeUser.getBookings().get(j).doc.title))
                            alreadyHas = true;
                    }
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
                    int delay = 21;
                    if (Main.activeUser.isFaculty()) delay = 28;
                    if (doc instanceof Book) if(((Book) doc).isBS()) delay = 14;
                    Main.activeUser.addBooking(new Booking(doc, delay));
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        addBookBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        bookButtons.add(addBookBtn);
        bookButtons.add(Box.createRigidArea(new Dimension(5, 0)));

        // Info about order button
        JButton infoBookBtn = new JButton(" Order info ");
        infoBookBtn.addActionListener(e -> {
            String document = ubList.getSelectedValue();
            if (document != null) {
                Booking booking = Main.activeUser.getBookings().get(0);
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {
                    if (Main.activeUser.getBookings().get(i).doc.title.equals(document))
                        booking = Main.activeUser.getBookings().get(i);
                }
                String info = "";
                info += "Information about the order:\n";
                info += "Title: " + booking.doc.title + "\n";
                info += "Authors: ";
                for (int i = 0; i < booking.doc.authors.size(); ++i)
                    info += booking.doc.authors.get(i) + " ";
                info += "\n";
                info += "Price: " + booking.doc.price + "\n";
                info += "Time left: " + booking.getDaysLeft() + "\n";
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
        mainPanel.add(booksList);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(bookButtons, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(userBooksLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userBooksList);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(infoBookBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }


}