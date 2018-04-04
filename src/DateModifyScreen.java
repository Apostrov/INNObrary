package main.java;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

class DateModifyScreen extends JFrame {

    private String day;
    private String month;
    private String year;

    private JTextField dayField;
    private JTextField monthField;
    private JTextField yearField;

    DateModifyScreen() {
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

        // Back button box
        Box backBtnBox = Box.createHorizontalBox();
        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            Main.docMod.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBtnBox.add(backBtn);
        backBtnBox.add(Box.createRigidArea(new Dimension(200, 0)));

        // Boxes for input fields
        Box docAddBox = Box.createHorizontalBox();
        Box labelBox = Box.createVerticalBox();
        Box fieldBox = Box.createVerticalBox();

        // Day label
        JLabel dayLabel = new JLabel();
        dayLabel.setText("Day:  ");
        dayLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(dayLabel);
        // Day field
        dayField = new JTextField();
        dayField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        dayField.setMaximumSize(new Dimension(160, 20));
        dayField.setText(Integer.toString(Main.date.getDate()));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(dayField);

        // Month label
        JLabel monthLabel = new JLabel();
        monthLabel.setText("Month:  ");
        monthLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(monthLabel);
        // Month field
        monthField = new JTextField();
        monthField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        monthField.setMaximumSize(new Dimension(160, 20));
        monthField.setText(Integer.toString(Main.date.getMonth() + 1));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(monthField);

        // Year label
        JLabel yearLabel = new JLabel();
        yearLabel.setText("Year:  ");
        yearLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(yearLabel);
        // Month field
        yearField = new JTextField();
        yearField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        yearField.setMaximumSize(new Dimension(160, 20));
        yearField.setText(Integer.toString(Main.date.getYear() + 1900));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(yearField);

        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(labelBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(fieldBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Modify document button box
        Box dateModifyBtnBox = Box.createHorizontalBox();
        // Modify document button
        JButton dateModifyBtn = new JButton("Modify date");
        dateModifyBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        dateModifyBtn.addActionListener(e -> {
                    updateDateData();
                    if (day == null || month == null || year == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else if (day.equals("") || month.equals("") || year.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else {
                        Main.date = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(day));
                        DataBase.saveDate();
                        JOptionPane.showMessageDialog(mainPanel, "Date was successfully modified!");
                        Main.cabinet = new CabinetScreen(true);
                        Main.dateMod.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                    }
                }
        );
        dateModifyBtnBox.add(dateModifyBtn);
        dateModifyBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 55)));
        mainPanel.add(docAddBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(dateModifyBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void updateDateData() {
        day = dayField.getText();
        month = monthField.getText();
        year = yearField.getText();
    }

}
