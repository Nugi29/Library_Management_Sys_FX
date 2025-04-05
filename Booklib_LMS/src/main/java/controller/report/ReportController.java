package controller.report;

import DB.DBConnection;
import controller.fine.FineController;
import model.Member;
import model.Publisher;
import model.reportModels.AvailableBooks;
import model.reportModels.BorrowedBooks;
import model.reportModels.OverdueBooksWithFines;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportController {

    public List<AvailableBooks> getAvailableBooksList() {
        try {
            List<AvailableBooks> itemList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT b.id, b.name, b.isbn, p.name as publisher, c.name as category, a.name as author FROM book b , publisher p, category c, author a where availability_status = 1 AND b.publisher_id = p.id AND b.category_id = c.id AND b.author_id = a.id;");

            while (resultSet.next()) {
                itemList.add(
                        new AvailableBooks(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        ));
            }
            return itemList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BorrowedBooks> getBorrowedBooksList() {
        try {
            List<BorrowedBooks> booksList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT bb.id AS borrow_id, b.id AS book_id, b.name AS book_name, m.name AS member_name, m.contact, bb.borrowed_date, bb.isReturned FROM book_borrow bb, member m, book b Where bb.book_id = b.id and bb.member_id = m.id;");

            while (resultSet.next()) {
                booksList.add(
                        new BorrowedBooks(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7)
                        ));
            }
            return booksList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OverdueBooksWithFines> getOverdueBooksListWithFines() {
        String datFine = String.valueOf(FineController.FINE_RATE_PER_DAY);
        try {
            List<OverdueBooksWithFines> booksList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT bb.id AS borrow_id, " +
                            "b.id AS book_id, " +
                            "b.name AS book_name, " +
                            "m.name AS member_name, " +
                            "m.contact, " +
                            "bb.borrowed_date, " +
                            "DATE_ADD(bb.borrowed_date, INTERVAL 14 DAY) AS due_date, " +
                            "DATEDIFF(CURDATE(), DATE_ADD(bb.borrowed_date, INTERVAL 14 DAY)) AS overdue_days, " +
                            "COALESCE(f.amount, DATEDIFF(CURDATE(), DATE_ADD(bb.borrowed_date, INTERVAL 14 DAY)) * " + datFine + ", 0) AS fine " +
                            "FROM book_borrow bb " +
                            "JOIN book b ON bb.book_id = b.id " +
                            "JOIN member m ON bb.member_id = m.id " +
                            "LEFT JOIN fine f ON f.book_borrow_id = bb.id " +
                            "WHERE bb.isReturned = 0 " +
                            "AND CURDATE() > DATE_ADD(bb.borrowed_date, INTERVAL 14 DAY);"
            );
            while (resultSet.next()) {
                booksList.add(
                        new OverdueBooksWithFines(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9)
                        ));
            }
            return booksList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
