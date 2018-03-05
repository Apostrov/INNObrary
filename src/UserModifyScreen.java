import javax.swing.*;
import java.awt.*;

class UserModifyScreen extends JFrame {

    private boolean isFaculty = false;
    private String password;
    private String firstName;
    private String secondName;
    private String address;
    private String phone;
    private JTextField passwordField;
    private JTextField fNameField;
    private JTextField sNameField;
    private JTextField addressField;
    private JTextField phoneField;

    UserModifyScreen(User user) {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            createGUI(user);
        });
    }

    private void createGUI(User user) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // Back button box
        Box backBtnBox = Box.createHorizontalBox();
        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            Main.userMod.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBtnBox.add(backBtn);
        backBtnBox.add(Box.createRigidArea(new Dimension(200, 0)));

        // Boxes for input fields
        Box registerBox = Box.createHorizontalBox();
        Box labelBox = Box.createVerticalBox();
        Box fieldBox = Box.createVerticalBox();

        // Password label
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password:  ");
        passwordLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(passwordLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Password field
        passwordField = new JTextField();
        passwordField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(160, 20));
        passwordField.setText(user.getPassword());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(passwordField);

        // First name label
        JLabel fNameLabel = new JLabel();
        fNameLabel.setText("First name:  ");
        fNameLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(fNameLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // First name field
        fNameField = new JTextField();
        fNameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        fNameField.setMaximumSize(new Dimension(160, 20));
        fNameField.setText(user.getFirstName());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(fNameField);

        // Second name label
        JLabel sNameLabel = new JLabel();
        sNameLabel.setText("Second name:  ");
        sNameLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(sNameLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Second name field
        sNameField = new JTextField();
        sNameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        sNameField.setMaximumSize(new Dimension(160, 20));
        sNameField.setText(user.getSecondName());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(sNameField);

        // Address label
        JLabel addressLabel = new JLabel();
        addressLabel.setText("Address:  ");
        addressLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(addressLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Address field
        addressField = new JTextField();
        addressField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addressField.setMaximumSize(new Dimension(160, 20));
        addressField.setText(user.getAddress());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(addressField);

        // Phone label
        JLabel phoneLabel = new JLabel();
        phoneLabel.setText("Phone number:  ");
        phoneLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(phoneLabel);
        // Phone field
        phoneField = new JTextField();
        phoneField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        phoneField.setMaximumSize(new Dimension(160, 20));
        phoneField.setText(user.getPhone());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(phoneField);

        registerBox.add(Box.createRigidArea(new Dimension(15, 0)));
        registerBox.add(labelBox);
        registerBox.add(fieldBox);
        registerBox.add(Box.createRigidArea(new Dimension(15, 0)));

        // Register button box
        Box registerBtnBox = Box.createHorizontalBox();
        // Register button
        JButton registerBtn = new JButton("Modify user");
        registerBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        registerBtn.addActionListener(e -> {
                    updateData();
                    if (password == null || firstName == null || secondName == null || address == null || phone == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else if (password.equals("") || firstName.equals("") || secondName.equals("") || address.equals("") || phone.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else {
                        user.setPassword(password);
                        user.setFaculty(isFaculty);
                        user.setFirstName(firstName);
                        user.setSecondName(secondName);
                        user.setAddress(address);
                        user.setPhone(phone);
                        DataBase.addUser(user);
                        JOptionPane.showMessageDialog(mainPanel, "The user successfully modified!");
                        Main.cabinet = new CabinetScreen(true);
                        Main.userMod.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                    }
                }
        );
        registerBtnBox.add(registerBtn);
        registerBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        // Faculty check box
        Box facultyBox = Box.createHorizontalBox();

        JCheckBox facultyCheckBox = new JCheckBox("Is faculty member");
        facultyCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        facultyCheckBox.setSelected(user.isFaculty());
        facultyCheckBox.addItemListener(e -> isFaculty = facultyCheckBox.isSelected());

        facultyBox.add(facultyCheckBox);
        facultyBox.add(Box.createRigidArea(new Dimension(0, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 65)));
        mainPanel.add(registerBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(facultyBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(registerBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void updateData () {
        password = passwordField.getText();
        firstName = fNameField.getText();
        secondName = sNameField.getText();
        address = addressField.getText();
        phone = phoneField.getText();
    }

}
