import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {

    RegisterScreen () {
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

        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            Main.register.setVisible(false);
            Main.login.setVisible(true);
        });

        mainPanel.add(Box.createRigidArea(new Dimension(10, 15)));
        mainPanel.add(backBtn);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }
}