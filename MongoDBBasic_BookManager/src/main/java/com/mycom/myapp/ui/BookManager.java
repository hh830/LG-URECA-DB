package com.mycom.myapp.ui;

import com.mycom.myapp.dao.BookDao;
import com.mycom.myapp.dto.BookDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BookManager extends JFrame {
    private JTable table; //grid ui component
    private DefaultTableModel tableModel; // grid data
    private JButton addButton, editButton, listButton, searchButton;
    private JTextField searchWordField;
    // Dao
    private BookDao bookDao = new BookDao();

    public BookManager(){


        // 화면 UI 관련 설정
        setTitle("Book Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표시 누를 때 종료해라
        setLocationRelativeTo(null);

        // table
        // int[][] a = new int[]{1,2}와 같은 표현
        tableModel = new DefaultTableModel(new Object[] {"Book Id", "Book Name", "Publisher", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are not editable
            }
        };

        table = new JTable(tableModel);

        // DB로부터 현재 book 테이블에 있는 데이터를 가져와서 보여준다
        listBook();

        // search
        Dimension textFieldSize = new Dimension(400, 28); // 크기를 갖는 객체
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("제목 검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        // button
        addButton = new JButton("등록");
        editButton = new JButton("수정");
        listButton = new JButton("목록");

        // button 2개를 담는 JPanel 객체를 만들고 그 객체럴 BookManager에 담는다.
        JPanel buttonPanel = new JPanel(); // default layout : Flow Layout
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(listButton);

        // table을 BookManager에 붙인다.
        // BookManager의 layout에 따라 결정


        // BookManager의 layout 설정
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER); // table < scroll pane < jframe
        add(buttonPanel, BorderLayout.SOUTH);

        // event 객체를 받아서 처리하는 로직을 가진 객체
        // button action event 처리
        // functional interface -> 람다 사용가능
        addButton.addActionListener( e -> {
            // AddBookDialog.를 띄운다.
            AddBookDialog addBookDialog = new AddBookDialog(this, this.tableModel);
            addBookDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            // table에 선택된 row가 있으면 EditBookDialog를 띄운다.
            // table에 선택된 row
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0){
                EditBookDialog editBookDialog = new EditBookDialog(this, this.tableModel, selectedRow);
                editBookDialog.setVisible(true);
            } else{
                JOptionPane.showMessageDialog(this, "도서를 선택하세요.");
            }

        });

        listButton.addActionListener(e -> listBook());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // double click
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        EditBookDialog editDialog = new EditBookDialog(BookManager.this, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
                }
            }
        });

        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if(!searchWord.isBlank()){ // 널이거나 값이 없거나
                listBook(searchWord);
            }
        });
    }

    private void clearTable(){
        tableModel.setRowCount(0);
    }

    private void listBook() {
        // 현재 tableModel을 정리하고
        clearTable();

        List<BookDto> bookList = bookDao.listBook();

        for (BookDto book : bookList) {
            tableModel.addRow(new Object[] {book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice() });
        }
    }

    private void listBook(String searchWord) {
        // 현재 tableModel을 정리하고
        clearTable();

        List<BookDto> bookList = bookDao.listBookSearch(searchWord);

        for (BookDto book : bookList) {
            tableModel.addRow(new Object[] {book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice() });
        }
    }

    BookDto detailBook(int bookId) {
        return bookDao.detailBook(bookId);
    }

    void insertBook(BookDto book) {
        bookDao.insertBook(book);
        listBook();
    }

    void updateBook(BookDto book) {
        bookDao.updateBook(book);
        listBook();
    }

    void deleteBook(int bookId) {
        bookDao.deleteBook(bookId);
        listBook();
    }

    public static void main(String[] args) {
        // main() thread와 별개로 별도의 스레드로 화면을 띄운다.(메인메소드가 끝나도 화면에 남기게)
        // thread 처리를 간단히 해주는 utility method 제공
        // invokeLater ( thread 객체 <- runnable interface를 구현한 <- runnable interface가 functional interface임
        // 결과적으로 invokeLater(lamda식 표현 객체)
        SwingUtilities.invokeLater(() -> {
            new BookManager().setVisible(true);
        });


    }


}
