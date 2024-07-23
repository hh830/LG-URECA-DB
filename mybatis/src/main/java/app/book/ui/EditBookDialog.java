package app.book.ui;


import app.book.dto.BookDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EditBookDialog extends JDialog{
    private JTextField bookIdField, bookNameField, publisherField, priceField;
    private JButton updateButton, deleteButton;
    private DefaultTableModel tableModel;

    public EditBookDialog(BookManager parent, DefaultTableModel tableModel, int rowIndex){
        this.tableModel = tableModel;
        setTitle("Book Save Dialog");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        // 선택된 book의 bookId로 book table에서 조회
        Integer bookId = (Integer) tableModel.getValueAt(rowIndex, 0);
        BookDto book = parent.detailBook(bookId);

        // input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4,2));

        // field
        bookIdField = new JTextField(String.valueOf(bookId));
        bookIdField.setEditable(false);
        bookNameField = new JTextField(book.getBookName());
        publisherField = new JTextField(book.getPublisher());
        priceField = new JTextField(String.valueOf(book.getPrice()));

        // button
        updateButton = new JButton("Add");

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
        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // add inputPanel, buttonPanel to Dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // add button actionListner
        updateButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "수정할까요?", "수정 확인", JOptionPane.YES_NO_OPTION);
            if(ret == JOptionPane.YES_OPTION){
                //int curBookId = Integer.parseInt(bookIdField.getText());
                String bookName = bookNameField.getText();
                String publisher = publisherField.getText();
                int price = Integer.parseInt(priceField.getText());

                parent.updateBook(new BookDto(bookId, bookName, publisher, price)); // 위쪽에 선택된 row에서 변수를 사용 - editable false 했기 때문
                dispose();
            }
        });

        deleteButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if(ret == JOptionPane.YES_OPTION){
                //int curBookId = Integer.parseInt(bookIdField.getText());
                parent.deleteBook(bookId);
                dispose();
            }
        });
    }
}
