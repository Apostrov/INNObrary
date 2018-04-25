package main.java;

import javax.swing.*;
import java.awt.*;

class LogScreen extends JFrame {

    LogScreen() {
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

        // List of logs
        Box logsBox = Box.createHorizontalBox();
        logsBox.add(Box.createRigidArea(new Dimension(10, 0)));
        JList<Object> logList = new JList<>(Main.logs.toArray());
        logList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane logScroll = new JScrollPane(logList);
        logScroll.setPreferredSize(new Dimension(400, 200));
        logsBox.add(logScroll);
        logsBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Back button
        Box backBox = Box.createHorizontalBox();
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
            Main.logScreen.setVisible(false);
        });
        backBox.add(backBtn);
        backBox.add(Box.createRigidArea(new Dimension(875, 0)));

        //Label of library books
        JLabel ubLabel = new JLabel();
        ubLabel.setText("Logs of INNObrary");
        ubLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(ubLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(logsBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(1000, 650));
        pack();
        setLocationRelativeTo(null);
    }

}
