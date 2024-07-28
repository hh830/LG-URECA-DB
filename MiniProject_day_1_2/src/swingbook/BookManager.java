package swingbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookManager extends JFrame {
    private JTable table; //grid ui component
    private DefaultTableModel tableModel; // grid data
    private JButton addButton, editButton;

    public BookManager(){
        // 화면 UI 관련 설정
        setTitle("Book Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x표시 누를 때 종료해라
        setLocationRelativeTo(null);

        // table
        // int[][] a = new int[]{1,2}와 같은 표현
        tableModel = new DefaultTableModel(new Object[] {"Book Id", "Book Name", "Publisher", "Price"}, 0);
        table = new JTable(tableModel);

        // button
        addButton = new JButton("Add Book");
        editButton = new JButton("Edit Book");

        // button 2개를 담는 JPanel 객체를 만들고 그 객체럴 BookManager에 담는다.
        JPanel buttonPanel = new JPanel(); // default layout : Flow Layout
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        // table을 BookManager에 붙인다.
        // BookManager의 layout에 따라 결정


        // BookManager의 layout 설정
        setLayout(new BorderLayout());
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
