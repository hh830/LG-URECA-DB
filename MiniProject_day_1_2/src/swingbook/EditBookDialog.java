package swingbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EditBookDialog extends JDialog{
    private JTextField bookIdField, bookNameField, publisherField, priceField;
    private JButton saveButton;
    private DefaultTableModel tableModel;

    public EditBookDialog(JFrame parent, DefaultTableModel tableModel, int rowIndex){
        this.tableModel = tableModel;
        setTitle("Book Save Dialog");
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(parent);

        // 선택된 row의 각 항목의 값을 구하고 JTextField 객체를 생성하면서 값을 전달
        String bookId = (String) tableModel.getValueAt(rowIndex, 0);
        String bookName = (String) tableModel.getValueAt(rowIndex, 1);
        String publisher = (String) tableModel.getValueAt(rowIndex, 2);
        String price = (String) tableModel.getValueAt(rowIndex, 3);

        // field
        bookIdField = new JTextField(bookId);
        bookNameField = new JTextField(bookName);
        publisherField = new JTextField(publisher);
        priceField = new JTextField(price);

        // button
        saveButton = new JButton("Add");

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
        add(saveButton);

        // add button actionListner
        saveButton.addActionListener(e -> {
            //tableModel.setValueAt(bookIdField.getText(), rowIndex, 0); // id는 수정되면 안됨
            tableModel.setValueAt(bookNameField.getText(), rowIndex, 1);
            tableModel.setValueAt(publisherField.getText(), rowIndex, 2);
            tableModel.setValueAt(priceField.getText(), rowIndex, 3);

            dispose(); // 버튼 누르면 사라지게
        });
    }
}
