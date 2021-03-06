package main.java;

import javax.swing.*;
import java.awt.*;

class DocModifyScreen extends JFrame {

    private String keywords;
    private String price;
    private String edition;
    private String editionYear;
    private String publisher;
    private boolean isReference;
    private boolean isBestSeller;
    private JTextField keywordsField;
    private JTextField priceField;
    private JTextField editionField;
    private JTextField editionYField;
    private JTextField publisherField;
    private JCheckBox refCheckBox;
    private JCheckBox BSCheckBox;

    DocModifyScreen(Document doc) {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            if (doc instanceof Book)
                createGUIBook(doc);
            else
                createGUI(doc);
        });
    }

    private void createGUIBook(Document doc) {
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

        // Keywords label
        JLabel keywordsLabel = new JLabel();
        keywordsLabel.setText("Keywords:  ");
        keywordsLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(keywordsLabel);
        // Price field
        keywordsField = new JTextField();
        keywordsField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        keywordsField.setMaximumSize(new Dimension(160, 20));
        String keys = "";
        for (int i = 0; i < doc.getKeywords().size(); ++i)
            keys += doc.getKeywords().get(i) + (i != doc.getKeywords().size() - 1 ? ", " : "");
        keywordsField.setText(keys);
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(keywordsField);

        // Price label
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price:  ");
        priceLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(priceLabel);
        // Price field
        priceField = new JTextField();
        priceField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        priceField.setMaximumSize(new Dimension(160, 20));
        priceField.setText(Integer.toString(doc.getPrice()));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(priceField);

        // Edition label
        JLabel editionLabel = new JLabel();
        editionLabel.setText("Edition:  ");
        editionLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(editionLabel);
        // Edition field
        editionField = new JTextField();
        editionField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        editionField.setMaximumSize(new Dimension(160, 20));
        editionField.setText(Integer.toString(doc.getEdition()));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(editionField);

        // Edition year label
        JLabel editionYLabel = new JLabel();
        editionYLabel.setText("Edition year:  ");
        editionYLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(editionYLabel);
        // Edition year field
        editionYField = new JTextField();
        editionYField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        editionYField.setMaximumSize(new Dimension(160, 20));
        editionYField.setText(Integer.toString(doc.getYear()));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(editionYField);

        // Publisher label
        JLabel publisherLabel = new JLabel();
        publisherLabel.setText("Publisher:  ");
        publisherLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(publisherLabel);
        // Publisher field
        publisherField = new JTextField();
        publisherField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        publisherField.setMaximumSize(new Dimension(160, 20));
        publisherField.setText(doc.getPublisher());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(publisherField);

        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(labelBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(fieldBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Modify document button box
        Box docAddBtnBox = Box.createHorizontalBox();
        // Modify document button
        JButton docAddBtn = new JButton("Modify document");
        docAddBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        docAddBtn.addActionListener(e -> {
                    updateBookData();
                    if (keywords == null || price == null || edition == null || editionYear == null || publisher == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has tried to modify the document \"" + doc.getTitle() + "\".)");
                    } else if (keywords.equals("") || price.equals("") || edition.equals("") || editionYear.equals("") || publisher.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has tried to modify the document \"" + doc.getTitle() + "\".)");
                    } else {
                        doc.setKeywords(keywords);
                        doc.setPrice(Integer.parseInt(price));
                        doc.setReference(isReference);
                        doc.setEdition(Integer.parseInt(edition));
                        doc.setYear(Integer.parseInt(editionYear));
                        doc.setPublisher(publisher);
                        doc.setBestSeller(isBestSeller);
                        DataBase.addDoc(doc);
                        JOptionPane.showMessageDialog(mainPanel, "Document successfully modified!");
                        Main.cabinet = new CabinetScreen(true);
                        Main.docMod.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has modified the document \"" + doc.getTitle() + "\".)");
                    }
                }
        );
        docAddBtnBox.add(docAddBtn);
        docAddBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        // Bestseller & reference check boxes
        Box refBSBox = Box.createHorizontalBox();

        refCheckBox = new JCheckBox("Is reference");
        refCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        refCheckBox.setSelected(doc.isReference());
        refCheckBox.addItemListener(e -> isReference = refCheckBox.isSelected());

        BSCheckBox = new JCheckBox("Is bestseller");
        BSCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        BSCheckBox.setSelected(doc.isBestSeller());
        BSCheckBox.addItemListener(e -> isBestSeller = BSCheckBox.isSelected());

        refBSBox.add(refCheckBox);
        refBSBox.add(Box.createRigidArea(new Dimension(5, 0)));
        refBSBox.add(BSCheckBox);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 55)));
        mainPanel.add(docAddBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(refBSBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void createGUI(Document doc) {
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

        // Keywords label
        JLabel keywordsLabel = new JLabel();
        keywordsLabel.setText("Keywords:  ");
        keywordsLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(keywordsLabel);
        // Price field
        keywordsField = new JTextField();
        keywordsField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        keywordsField.setMaximumSize(new Dimension(160, 20));
        String keys = "";
        for (int i = 0; i < doc.getKeywords().size(); ++i)
            keys += doc.getKeywords().get(i) + (i != doc.getKeywords().size() - 1 ? ", " : "");
        keywordsField.setText(keys);
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(keywordsField);

        // Price label
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price:  ");
        priceLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(priceLabel);
        // Price field
        priceField = new JTextField();
        priceField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        priceField.setMaximumSize(new Dimension(160, 20));
        priceField.setText(Integer.toString(doc.getPrice()));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(priceField);

        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(labelBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(fieldBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Modify document button box
        Box docAddBtnBox = Box.createHorizontalBox();
        // Modify document button
        JButton docAddBtn = new JButton("Modify document");
        docAddBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        docAddBtn.addActionListener(e -> {
                    updateAVData();
                    if (keywords == null ||  price == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has tried ot modify the document \"" + doc.getTitle() + "\".)");
                    } else if (keywords.equals("") || price.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has tried ot modify the document \"" + doc.getTitle() + "\".)");
                    } else {
                        doc.setKeywords(keywords);
                        doc.setPrice(Integer.parseInt(price));
                        doc.setReference(isReference);
                        DataBase.addDoc(doc);
                        JOptionPane.showMessageDialog(mainPanel, "Document successfully modified!");
                        priceField.setText("");
                        Main.cabinet = new CabinetScreen(true);
                        Main.docMod.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                        DataBase.log("[" + Main.date.toString() + "][" + Main.activeUser.getUsername() +
                                "]--(The user has modified the document \"" + doc.getTitle() + "\".)");
                    }
                }
        );
        docAddBtnBox.add(docAddBtn);
        docAddBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        // Bestseller & reference check boxes
        Box refBSBox = Box.createHorizontalBox();

        JCheckBox refCheckBox = new JCheckBox("Is reference");
        refCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        refCheckBox.setSelected(doc.isReference());
        refCheckBox.addItemListener(e -> isReference = refCheckBox.isSelected());

        refBSBox.add(refCheckBox);
        refBSBox.add(Box.createRigidArea(new Dimension(0, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 55)));
        mainPanel.add(docAddBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(refBSBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void updateBookData() {
        isBestSeller = BSCheckBox.isSelected();
        isReference = refCheckBox.isSelected();
        price = priceField.getText();
        edition = editionField.getText();
        editionYear = editionYField.getText();
        publisher = publisherField.getText();
        keywords = keywordsField.getText();
    }

    private void updateAVData() {
        price = priceField.getText();
        keywords = keywordsField.getText();
    }

}
