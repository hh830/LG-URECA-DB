package swingbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddBookDialog extends JDialog {
    private JTextField bookIdField, bookNameField, publisherField, priceField;
    private JButton addButton;
    private DefaultTableModel tableModel;

    public AddBookDialog(JFrame parent, DefaultTableModel tableModel){
        this.tableModel = tableModel;
        setTitle("Book Add Dialog");
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(parent);

        // field
        bookIdField = new JTextField();
        bookNameField = new JTextField();
        publisherField = new JTextField();
        priceField = new JTextField();

        // button
        addButton = new JButton("Add");

        // add field with label
        add(new JLabel("Book Id"));
        add(bookIdField);
        add(new JLabel("Book Name"));
        add(bookNameField);
        add(new JLabel("Publisher"));
        add(publisherField);
        add(new JLabel("Price"));
        add(priceField);

        add(new JLabel(""));
        add(addButton);

        // add button actionListner
        addButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            String bookName = bookNameField.getText();
            String publisher = publisherField.getText();
            String price = priceField.getText();

            tableModel.addRow(new Object[] {bookId, bookName, publisher, price});
        });
    }
}
