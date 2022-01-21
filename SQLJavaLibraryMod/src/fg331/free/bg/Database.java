package fg331.free.bg;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {

//    static String url = "jdbc:mysql://localhost:3306/new_schema";
//    static String username = "root";
//    static String password = "793mbhd87@(C";

    static String url = "jdbc:sqlite:libraryDB.db";


    //TODO Добавянето е наоравено за 4.11
    // Заемането на книга също

    public static boolean addUser(String fName, String lName, String phone) {

        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
            Connection connection = DriverManager.getConnection(url);

            String sql = "Insert INTO user (fName, lName, phone) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, phone);

//            System.out.println(fName);
//            System.out.println(lName);
//            System.out.println(phone);

            statement.executeUpdate();

            statement.close();
            connection.close();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public static boolean addBook(String name, String genre, String author) {

        try {
            Connection connection = DriverManager.getConnection(url);

            String sql = "Insert INTO book (Name, Genre, Author, taken) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, name);
            statement.setString(2, genre);
            statement.setString(3, author);
            statement.setInt(4, 0);

            statement.executeUpdate();

            System.out.println(name);
            System.out.println(genre);
            System.out.println(author);

            statement.close();
            connection.close();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public static String listUsers() {

        try {
            Connection connection = DriverManager.getConnection(url);

            String sql = "Select * FROM user";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            StringBuilder stringBuilder = new StringBuilder();
            while (result.next()) {
                stringBuilder.append(result.getString(1)).append(" ").append(result.getString(2)).append(" ").append(result.getString(3)).append(" ").
                        append(result.getString(4)).append(" ").append("\n");
            }

            result.close();
            statement.close();
            connection.close();

            return stringBuilder.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";
    }

    public static boolean createtransactions(String userID, String bookID, String duration) {

        try {
            Connection connection = DriverManager.getConnection(url);

//            String sql = "Insert INTO transactions (bookID, userID, startDate, duration) VALUES (?, ?, ?, ?)";

            String sql = "Insert INTO transactions (bookID, userID, startDate, duration) VALUES (?, ?, ?, ?)";


            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(bookID));
            statement.setInt(2, Integer.parseInt(userID));
//            statement.setDate(3, Date.valueOf(LocalDate.now()));
            System.out.println(String.valueOf(LocalDate.now()));
            statement.setString(3, String.valueOf(LocalDate.now()));
            statement.setInt(4, Integer.parseInt(duration));

            statement.executeUpdate();


            sql = "UPDATE book SET isTaken = true, takenByWhom =" + userID + " WHERE `bookID` = " + bookID;
            PreparedStatement statement1 = connection.prepareStatement(sql);

            statement1.executeUpdate();

            statement.close();
            statement1.close();
            connection.close();
            System.out.println("Conecto");

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public static String checkUser(String userID) {

        try {
            Connection connection = DriverManager.getConnection(url);


            //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            String sql = "Select * FROM book Where taken = " + userID;

            // всичко от книги където книга.ид = транзакция.книгаИд        И       юзър.ид = транзакция.юзърИд

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            StringBuilder booksTakenByUser = new StringBuilder();

            while (result.next()) {
//                booksTakenByUser.app(new String(result.getString(1) + " " + result.getString(2) + " " + result.getString(3) + " " +
//                        result.getString(4)));
                booksTakenByUser.append(result.getString(1)).append(", ").append(result.getString(2)).append(", ").append(result.getString(3)).append(", ").append(result.getString(4)).append("\n");
            }

            if (booksTakenByUser.length() - 1 >= 0) {
                booksTakenByUser.deleteCharAt(booksTakenByUser.length() - 1);
            }

            String[] rowedBooksTaken = booksTakenByUser.toString().split("\n");

            sql = "Select * From transactions Where userID = " + userID + " and completed = 0";

            Statement statement1 = connection.createStatement();
            ResultSet result1 = statement1.executeQuery(sql);

            ArrayList<String> dates = new ArrayList<>();
            ArrayList<Integer> durations = new ArrayList<>();
//            ArrayList<Integer> bookID = new ArrayList<>();

            while (result1.next()) {
//                bookID.add(Integer.valueOf(result.getString(2)));
                dates.add(result1.getString(4));
                durations.add(Integer.valueOf(result1.getString(5)));
            }

            System.out.println(durations.toString());

            ArrayList<String> delayedReturnBooks = new ArrayList<>();
            ArrayList<String> notYetReturnedBooks = new ArrayList<>();

            int inc = 0;
            if (dates.size() != 0)
                for (String s : rowedBooksTaken) {
                    LocalDate returnByDate = LocalDate.parse(dates.get(inc)).plusDays(durations.get(inc));
                    LocalDate current = LocalDate.now();

                    if (current.isAfter(returnByDate)) {
                        //TODO взима старите рекорди тъй като няма поле аз потвърждаване на връщането в transactions
                        delayedReturnBooks.add(s);

                    } else if (current.isBefore(returnByDate)) {
                        notYetReturnedBooks.add(s);
                    }

                    inc++;
                }


            //---------------------------------------------------------------------------------------------------------------------------------------

            //TODO да взема всички книгиИД от транзакции и с тях да апендвам самите книги и да го върна

            sql = "Select Count(*) from transactions where userID = " + userID;
            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            int count = 0;
            while (result.next())
                count = result.getInt(1);

            int[] bookIndexesInHistoryOfUser = new int[count];

            System.out.println(count);
            sql = "Select bookID from transactions where userID = " + userID;
            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            inc = 0;
            while (result.next()) {
                bookIndexesInHistoryOfUser[inc] = result.getInt(1);
                inc++;
            }
//                System.out.println(result.getInt(1));

            sql = "Select Count(*) from book";
            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            result.next();
            int countOfAllBooksInLibrary = result.getInt(1);

            System.out.println(countOfAllBooksInLibrary / 1000);

            int div = countOfAllBooksInLibrary % 1000;

//            String[] informationOfEveryBook = new String[];
            ArrayList<String> informationOfEveryBook = new ArrayList<>();

            StringBuilder finalBuilder = new StringBuilder();

            finalBuilder.append("Просрочени:").append("\n");
            for (String s : delayedReturnBooks) {
                finalBuilder.append(s).append("\n");
            }

            finalBuilder.append("Още не върнати:").append("\n");
            for (String s : notYetReturnedBooks) {
                finalBuilder.append(s).append("\n");
            }

            finalBuilder.append("История :").append("\n");

            if (bookIndexesInHistoryOfUser.length != 0)
                for (int i = 0; i < Math.ceil((double) countOfAllBooksInLibrary / 1000); i++) {

                    sql = "Select * from book where bookID < " + (1000 + 1000 * i) + " and bookID > " + 1000 * i;
                    statement = connection.createStatement();
                    result = statement.executeQuery(sql);

                    while (result.next()) {
                        informationOfEveryBook.add(new String(result.getString(1) + ", " + result.getString(2) + ", " +
                                result.getString(3) + ", " + result.getString(4)));
                    }

                    for (int j = 1000 * i; j < 1000 + 1000 * i; j++) {
                        // взима книгаИндекс и проверява във взетия списък от книги

                        if (j >= count)
                            break;
                        else
                            finalBuilder.append(informationOfEveryBook.get(bookIndexesInHistoryOfUser[j] - 1)).append("\n");
                    }

                }
            //---------------------------------------------------------------------------------------------------------------------------------------


            if (finalBuilder.length() - 1 >= 0)
                finalBuilder.deleteCharAt(finalBuilder.length() - 1);


            //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

            //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//            sql = "Select * From transactions Where transactions.userID = " + userID;
//            statement = connection.prepareStatement(sql);
//            ResultSet result1 = statement.executeQuery(sql);

            //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

            //TODO взима всички книги
            // взима транзакциите на юзъра
            //


            //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

            //TODO имам листа с айдита на книгите от транзакциите
            // трябва да го потърся в листа от книги
            //

            //TODO КАКВО ИСКАМ
            // да се върне textPane в който има форматирано Невърнари книги ........ Взети книги ....... Върнати книги ........
            // да това ще трябва да класифицирам като взети и не взети книги
            // взетите ще трябва да проверя в листа на книгите по ИД дали е взета или не
            //

            //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

            //TODO вече взима Транзакции и Книги сега трябва да върне текста
            // Просрочени книги
            // ..........
            // Не върнати книги
            // ..........
            // Върнати книги
            // ..........


            //TODO да вземе от транзакции книгите които не са върнати (в query или окастряне)
            //

            //TODO взима взетите книги и сравнява ИД на книга и ИД на кинга от транзакция

            result.close();
            statement.close();
            connection.close();

            return finalBuilder.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";
    }

    public static boolean returnBook(String userID, String bookID) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);

            String sql = "UPDATE book SET taken = 0 WHERE bookID = " + bookID;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            sql = "UPDATE transactions SET completed = 1 WHERE bookID = " + bookID + " and userID = " + userID;
            statement = connection.createStatement();
            statement.executeUpdate(sql);


            statement.close();
            connection.close();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;

    }

    public static String returnBooksList() {

        try {
            Connection connection = DriverManager.getConnection(url);

            String sql = "SELECT book.bookID, book.Name, book.Genre, book.Author, user.userID, user.fName, user.lName FROM book left JOIN user ON book.taken=user.userID";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            StringBuilder builder = new StringBuilder();

            while (result.next()) {
                if (result.getString(5) == null) {
                    builder.append(result.getString(1)).append(", ").append(result.getString(2)).append(", ").append(result.getString(3))
                            .append(", ").append(result.getString(4)).append(", Не е взета").append("\n");
                } else {
                    builder.append(result.getString(1)).append(", ").append(result.getString(2)).append(", ").append(result.getString(3))
                            .append(", ").append(result.getString(4)).append(", ").append(result.getString(5)).append(", ")
                            .append(result.getString(6)).append(", ").append(result.getString(7)).append("\n");
                }
            }

            if (builder.length() > 1)
                builder.deleteCharAt(builder.length() - 1);

            result.close();
            statement.close();
            connection.close();

            return builder.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";
    }
}
