import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserViewScreen extends JFrame {

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
        Box booksList = Box.createHorizontalBox();
        booksList.add(Box.createRigidArea(new Dimension(10, 0)));

        ArrayList<String> libBookTitles = new ArrayList<>();
        if (userForView != null) for (int i = 0; i < userForView.getBookings().size(); ++i) libBookTitles.add(userForView.getBookings().get(i).doc.title);
        JList<String> userBooks = new JList<>(libBookTitles.toArray(new String[libBookTitles.size()]));
        userBooks.setLayoutOrientation(JList.VERTICAL);
        userBooks.setVisibleRowCount(0);

        JScrollPane ubScroll = new JScrollPane(userBooks);
        ubScroll.setPreferredSize(new Dimension(50, 50));
        ubScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userBooks.addListSelectionListener(e -> {

        });
        booksList.add(ubScroll);
        booksList.add(Box.createRigidArea(new Dimension(10, 0)));

        // Back button
        Box backBox = Box.createHorizontalBox();
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            Main.cabinet.setVisible(true);
            Main.userView.setVisible(false);
        });
        backBox.add(backBtn);
        backBox.add(Box.createRigidArea(new Dimension(215, 0)));

        // Info about order button
        JButton infoBookBtn = new JButton(" Order info ");
        infoBookBtn.addActionListener(e -> {
            String order = userBooks.getSelectedValue();
            if (order != null) {
                Booking booking = userForView.getBookings().get(0);
                for (int i = 0; i < userForView.getBookings().size(); ++i) {
                    if (userForView.getBookings().get(i).doc.title.equals(order))
                        booking = userForView.getBookings().get(i);
                }
                String info = "";
                info += "Information about user's order:\n";
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
                JOptionPane.showMessageDialog(mainPanel, "Select user's order!");
            }
        });
        infoBookBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of library books
        JLabel ubLabel = new JLabel();
        ubLabel.setText("User's books");
        ubLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(ubLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(booksList);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(infoBookBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

}
