package fg331.free.bg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display {

    private JFrame frame;

    private String title;
    private int width, height;

    private String[] labelNames = new String[]{"Добавяне на потребител", "Добавяне на книга", "Лист на потребители", "Лист на книги", "Заемане на книга", "Връщане на книга"};
    private String[] doUserLabels = new String[]{"Първо име", "Фамилия", "Телефон"};
    private String[] doBookLabels = new String[]{"Име", "Жанр", "Автор"};
    private String buttonName = "Давай";

    private Font font = new Font("Times New Roman", Font.BOLD, 36);

    private JPanel jPanel = new JPanel(new GridBagLayout());


    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        Frame();
    }

    private void createJPanel1() {
        JPanel jPanel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel firstName = new JLabel(doUserLabels[0]);
        firstName.setFont(font);
        JLabel lastName = new JLabel(doUserLabels[1]);
        lastName.setFont(font);
        JLabel phone = new JLabel(doUserLabels[2]);
        phone.setFont(font);
        JLabel confirm = new JLabel("Потребителят е въведен");
        confirm.setVisible(false);

        JTextField addFirstName = new JTextField();
        addFirstName.setFont(font);
        addFirstName.setPreferredSize(new Dimension(400, 30));
        JTextField addLastName = new JTextField();
        addLastName.setFont(font);
        addLastName.setPreferredSize(new Dimension(400, 30));
        JTextField addPhone = new JTextField();
        addPhone.setFont(font);
        addPhone.setPreferredSize(new Dimension(400, 30));

        JButton back = new JButton("Назад");
        back.addActionListener(e1 -> {
            frame.remove(jPanel1);
            frame.add(jPanel);
            frame.pack();
        });

        JButton forward = new JButton("Създай");
        forward.addActionListener(e1 -> {
            if (Database.addUser(addFirstName.getText(), addLastName.getText(), addPhone.getText()))
                confirm.setVisible(true);
        });

        c.ipady = 30;

        jPanel1.add(firstName, c);
        c.gridy = 1;
        jPanel1.add(lastName, c);
        c.gridy = 2;
        jPanel1.add(phone, c);

        c.gridx = 1;
        c.gridy = 0;
        jPanel1.add(addFirstName, c);
        c.gridy = 1;
        jPanel1.add(addLastName, c);
        c.gridy = 2;
        jPanel1.add(addPhone, c);

        c.gridx = 0;

        c.gridy = 4;
        jPanel1.add(confirm, c);

        c.gridy = 3;
        jPanel1.add(back, c);

        c.gridx = 1;
        jPanel1.add(forward, c);

        frame.remove(jPanel);
        frame.add(jPanel1);

        frame.pack();
    }

    private void createJPanel2() {
        JPanel jPanel2 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel name = new JLabel(doBookLabels[0]);
        name.setFont(font);
        JLabel genre = new JLabel(doBookLabels[1]);
        genre.setFont(font);
        JLabel author = new JLabel(doBookLabels[2]);
        author.setFont(font);
        JLabel confirm = new JLabel("Книгата е въведена");
        confirm.setVisible(false);

        JTextField addName = new JTextField();
        addName.setFont(font);
        addName.setPreferredSize(new Dimension(400, 30));
        JTextField addGenre = new JTextField();
        addGenre.setFont(font);
        addGenre.setPreferredSize(new Dimension(400, 30));
        JTextField addAuthor = new JTextField();
        addAuthor.setFont(font);
        addAuthor.setPreferredSize(new Dimension(400, 30));

        JButton back = new JButton("Назад");
        back.addActionListener(e1 -> {
            frame.remove(jPanel2);
            frame.add(jPanel);
            frame.pack();
        });

        JButton forward = new JButton("Създай");
        forward.addActionListener(e1 -> {
            if (Database.addBook(addName.getText(), addGenre.getText(), addAuthor.getText()))
                confirm.setVisible(true);
        });

        c.ipady = 30;

        jPanel2.add(name, c);
        c.gridy = 1;
        jPanel2.add(genre, c);
        c.gridy = 2;
        jPanel2.add(author, c);

        c.gridx = 1;
        c.gridy = 0;
        jPanel2.add(addName, c);
        c.gridy = 1;
        jPanel2.add(addGenre, c);
        c.gridy = 2;
        jPanel2.add(addAuthor, c);

        c.gridx = 0;

        c.gridy = 4;
        jPanel2.add(confirm, c);

        c.gridy = 3;
        jPanel2.add(back, c);

        c.gridx = 1;
        jPanel2.add(forward, c);

        frame.remove(jPanel);
        frame.add(jPanel2);

        frame.pack();
    }

    private void createJPanel3() {
        JPanel jPanel3 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JTextPane textPane = new JTextPane();
        textPane.setText(Database.listUsers());
        textPane.setFont(font);

        JScrollPane scrollPane = new JScrollPane(textPane);

        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(400, 30));
        JButton back = new JButton("Назад");
        back.addActionListener(e1 -> {
            frame.remove(jPanel3);
            frame.add(jPanel);
            frame.pack();
        });

        JButton forward = new JButton("Провери");
        forward.addActionListener(e1 -> {
            //TODO да вземе ид и да върне всички книги на които в транзакцията пасва ид на потреб
//            Database.listUsers();
            // TODO да покаже всички книги взимани от потребитяле първо закъсняло не върнати второ нормално не върнати трети върнати

//            createJPanel3sub1(textField.getText());
//            String booksTakenByUser = Database.checkUser(textField.getText());

            JPanel jPanel3sub1 = new JPanel(new GridBagLayout());
            GridBagConstraints c1 = new GridBagConstraints();

            JTextPane textPane1 = new JTextPane();
            textPane1.setText(Database.checkUser(textField.getText()));
            textPane1.setFont(font);

            JScrollPane scrollPane1 = new JScrollPane(textPane1);
            JScrollBar scrollBar = scrollPane1.createVerticalScrollBar();

            scrollPane1.add(scrollBar);

            JButton back1 = new JButton("Назад");
            back1.addActionListener(e2 -> {
                frame.remove(jPanel3sub1);
                frame.add(jPanel3);
                frame.pack();
            });

            c1.ipady = 30;
            jPanel3sub1.add(scrollPane1, c1);
            c1.ipady = 1;
            jPanel3sub1.add(back1, c1);

            frame.remove(jPanel3);
            frame.add(jPanel3sub1);
            frame.pack();
        });

        c.ipady = 30;
        c.gridx = 0;
        c.gridy = 0;
        jPanel3.add(scrollPane, c);
        c.gridy = 1;
        jPanel3.add(textField, c);
        c.gridy = 2;
        jPanel3.add(back, c);
        c.gridx = 1;
        jPanel3.add(forward, c);

        frame.remove(jPanel);
        frame.add(jPanel3);

        frame.pack();
    }

    private void createJPanel3sub1(String textField) {

    }

    private void createJPanel4(){

        JPanel jPanel4 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JTextPane textPane1 = new JTextPane();
        textPane1.setText(Database.returnBooksList());
        textPane1.setFont(font);

        JScrollPane scrollPane1 = new JScrollPane(textPane1);
        JScrollBar scrollBar = scrollPane1.createVerticalScrollBar();

        scrollPane1.add(scrollBar);

        JButton back1 = new JButton("Назад");
        back1.addActionListener(e2 -> {
            frame.remove(jPanel4);
            frame.add(jPanel);
            frame.pack();
        });

        c.ipady = 30;
        jPanel4.add(scrollPane1, c);
        c.ipady = 1;
        jPanel4.add(back1, c);

        frame.remove(jPanel);
        frame.add(jPanel4);
        frame.pack();
    }

    private void createJPanel5() {
        JPanel jPanel5 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JTextField userID = new JTextField();
        userID.setFont(font);
        userID.setPreferredSize(new Dimension(400, 30));
        JTextField bookID = new JTextField();
        bookID.setFont(font);
        bookID.setPreferredSize(new Dimension(400, 30));
        JTextField duration = new JTextField();
        duration.setFont(font);
        duration.setPreferredSize(new Dimension(400, 30));

        JLabel userIDText = new JLabel("Потребител ID");
        userIDText.setFont(font);
        JLabel bookIDText = new JLabel("Книга ID");
        bookIDText.setFont(font);
        JLabel durationIDText = new JLabel("Продължителност");
        durationIDText.setFont(font);

        JLabel confirm = new JLabel("НГРЕНГИУГ");
        confirm.setVisible(false);
        confirm.setFont(font);

        JButton createTransaction = new JButton("Напред");
        createTransaction.addActionListener(e1 -> {
            if (Database.createtransactions(userID.getText(), bookID.getText(), duration.getText()))
                confirm.setVisible(true);
        });

        JButton back = new JButton("Назад");
        back.addActionListener(e1 -> {
            frame.remove(jPanel5);
            frame.add(jPanel);
            frame.pack();
        });


        jPanel5.add(userIDText, c);
        c.gridy = 1;
        jPanel5.add(bookIDText, c);
        c.gridy = 2;
        jPanel5.add(durationIDText, c);
        c.gridy = 3;
        jPanel5.add(back, c);
        c.gridy = 4;
        jPanel5.add(confirm, c);

        c.gridx = 1;
        c.gridy = 0;
        jPanel5.add(userID, c);
        c.gridy = 1;
        jPanel5.add(bookID, c);
        c.gridy = 2;
        jPanel5.add(duration, c);
        c.gridy = 3;
        jPanel5.add(createTransaction, c);


        frame.remove(jPanel);
        frame.add(jPanel5);

        frame.pack();
    }

    private void createJPanel6() {
        JPanel jPanel6 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel userID = new JLabel("Потребителско ИН");
        userID.setFont(font);
        JLabel bookID = new JLabel("Книга ИН");
        bookID.setFont(font);

        JTextField userIDText = new JTextField();
        userIDText.setPreferredSize(new Dimension(400, 30));
        userIDText.setFont(font);
        JTextField bookIDText = new JTextField();
        bookIDText.setPreferredSize(new Dimension(400, 30));
        bookIDText.setFont(font);

        JLabel confirm = new JLabel("НГРЕНГИУГ");
        confirm.setVisible(false);

        JButton returnBook = new JButton(" Връщане");
        returnBook.addActionListener(e1 -> {
            if (Database.returnBook(userIDText.getText(), bookIDText.getText()))
                confirm.setVisible(true);
        });

        JButton back = new JButton("Назад");
        back.addActionListener(e1 -> {
            frame.remove(jPanel6);
            frame.add(jPanel);
            frame.pack();
        });

        c.ipady = 30;
        jPanel6.add(userID, c);
        c.gridy = 1;
        jPanel6.add(bookID, c);
        c.gridy = 2;
        jPanel6.add(back, c);
        c.gridy = 3;
        jPanel6.add(confirm);
        c.gridx = 1;
        c.gridy = 0;
        jPanel6.add(userIDText, c);
        c.gridy = 1;
        jPanel6.add(bookIDText, c);
        c.gridy = 2;
        jPanel6.add(returnBook, c);

        frame.remove(jPanel);
        frame.add(jPanel6);

        frame.pack();
    }

    private void Frame() {
        frame = new JFrame(title);

//        frame.setBounds(0, 0, width, height);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(0,0);

        JLabel addUser = new JLabel(labelNames[0]);
        addUser.setFont(font);
        JLabel addBook = new JLabel(labelNames[1]);
        addBook.setFont(font);
        JLabel listUsers = new JLabel(labelNames[2]);
        listUsers.setFont(font);
        JLabel listBooks = new JLabel(labelNames[3]);
        listBooks.setFont(font);
        JLabel transaction = new JLabel(labelNames[4]);
        transaction.setFont(font);
        JLabel returnBook = new JLabel(labelNames[5]);
        returnBook.setFont(font);

        JButton doUser = new JButton(buttonName);
        doUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createJPanel1();
            }
        });
        doUser.setFont(font);
        JButton doBook = new JButton(buttonName);
        doBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createJPanel2();
            }
        });
        doBook.setFont(font);
        JButton doUsers = new JButton(buttonName);
        doUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createJPanel3();
            }
        });
        doUsers.setFont(font);
        JButton doBooks = new JButton(buttonName);
        doBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createJPanel4();
            }
        });
        doBooks.setFont(font);
        JButton doTransaction = new JButton(buttonName);
        doTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createJPanel5();
            }
        });
        doTransaction.setFont(font);
        JButton doreturnBook = new JButton(buttonName);
        doreturnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createJPanel6();
            }
        });
        doreturnBook.setFont(font);

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 30;
//        c.ipadx = width/2;
//        c.gridwidth = 3;
        jPanel.add(addUser, c);
        c.gridy = 1;
        jPanel.add(addBook, c);
        c.gridy = 2;
        jPanel.add(listUsers, c);
        c.gridy = 3;
        jPanel.add(listBooks, c);
        c.gridy = 4;
        jPanel.add(transaction, c);
        c.gridy = 5;
        jPanel.add(returnBook, c);

        c.gridx = 1;
        c.gridy = 0;
//        c.ipadx = 0;
//        c.gridwidth=1;
        jPanel.add(doUser, c);
        c.gridy = 1;
        jPanel.add(doBook, c);
        c.gridy = 2;
        jPanel.add(doUsers, c);
        c.gridy = 3;
        jPanel.add(doBooks, c);
        c.gridy = 4;
        jPanel.add(doTransaction, c);
        c.gridy = 5;
        jPanel.add(doreturnBook, c);


        frame.add(jPanel);

        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }
}


