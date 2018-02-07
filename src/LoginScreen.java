import javax.swing.*;
import java.awt.*;

class LoginScreen extends JFrame {

    JPasswordField passwordField;

    LoginScreen() {
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

        // Username field
        JTextField usernameField = new JTextField();
        usernameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        usernameField.setMaximumSize(new Dimension(160, 20));

        // Password field
        passwordField = new JPasswordField();
        passwordField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(160, 20));

        // Log in button
        JButton loginBtn = new JButton("Sign in ");
        loginBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> {
                if (checkForUser(usernameField.getText(), new String(passwordField.getPassword()))){
                    Main.activeUser = Main.findUser(usernameField.getText());
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setVisible(true);
                    Main.login.setVisible(false);
                } else if (checkForLib(usernameField.getText(), new String(passwordField.getPassword()))){
                    Main.activeUser = new Librarian(usernameField.getText(), new String(passwordField.getPassword()));
                    Main.cabinet = new CabinetScreen(true);
                    Main.cabinet.setVisible(true);
                    Main.login.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Wrong login or password!");
                }
        });

        // Register button
        JButton registerBtn = new JButton("Sign up");
        registerBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        registerBtn.addActionListener(e -> {
            Main.login.setVisible(false);
            Main.register.setVisible(true);
        });

        // Main label
        JLabel logo = new JLabel();
        logo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        logo.setFont(new Font("name", Font.PLAIN, 30));
        logo.setText("INNObrary");

        mainPanel.add(Box.createRigidArea(new Dimension(0, 75)));
        mainPanel.add(logo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(registerBtn);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private boolean checkForUser(String username, String password) {
        boolean founded = false;
        for (int i = 0; i < Main.users.size(); ++i) {
            if (Main.users.get(i).getUsername().equals(username) && Main.users.get(i).getPassword().equals(password)) founded = true;
        }
        return founded;
    }

    private boolean checkForLib(String username, String password) {
        boolean founded = false;
        for (int i = 0; i < Main.librarians.size(); ++i) {
            if (Main.librarians.get(i).getUsername().equals(username) && Main.librarians.get(i).getPassword().equals(password)) founded = true;
        }
        return founded;
    }

}