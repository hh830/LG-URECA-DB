package app.book.ui;

import app.book.dto.BookDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddBookDialog extends JDialog {
    private JTextField bookIdField, bookNameField, publisherField, priceField;
    private JButton addButton;
    private DefaultTableModel tableModel;

    public AddBookDialog(BookManager parent, DefaultTableModel tableModel){
        this.tableModel = tableModel;
        setTitle("Book Add Dialog");
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(parent);

        // input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4,2));

        // field
        bookIdField = new JTextField();
        bookNameField = new JTextField();
        publisherField = new JTextField();
        priceField = new JTextField();

        // button
        addButton = new JButton("Add");

        // add field with label
        inputPanel.add(new JLabel("Book Id"));
        inputPanel.add(bookIdField);
        inputPanel.add(new JLabel("Book Name"));
        inputPanel.add(bookNameField);
        inputPanel.add(new JLabel("Publisher"));
        inputPanel.add(publisherField);
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(priceField);

        // button panel
        JPanel buttonPanel = new JPanel();

        // button
        addButton = new JButton("Add");
        buttonPanel.add(addButton);

        // add inputPanel, buttonPanel to Dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // add button actionListner
        addButton.addActionListener( e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            String bookName = bookNameField.getText();
            String publisher = publisherField.getText();
            int price = Integer.parseInt(priceField.getText());

            parent.insertBook(new BookDto(bookId, bookName, publisher, price));

            dispose();
        });
    }
}
