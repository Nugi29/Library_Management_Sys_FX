package controller.bookborrow;

import DB.DBConnection;
import controller.book.BookController;
import model.Book;
import model.BookBorrow;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookBorrowController {

    private final BookController bookController = new BookController();

    public boolean addBookBorrow( BookBorrow bookBorrow) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO book_borrow (id,book_id,member_id,borrowed_date,isReturned,returnDate,ReturnedDate) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookBorrow.getId());
            preparedStatement.setString(2, bookBorrow.getBookId());
            preparedStatement.setString(3, bookBorrow.getMemberId());
            preparedStatement.setString(4, bookBorrow.getBorrowDate());
            preparedStatement.setBoolean(5, bookBorrow.getIsReturned());
            preparedStatement.setString(6, bookBorrow.getReturnDate());
            preparedStatement.setString(7, bookBorrow.getReturnedDate());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateBookBorrow(BookBorrow bookBorrow) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE book_borrow SET  book_id=?, member_id=?, borrowed_date=?, isReturned=?, returnDate=?,ReturnedDate=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookBorrow.getBookId());
            preparedStatement.setString(2, bookBorrow.getMemberId());
            preparedStatement.setString(3, bookBorrow.getBorrowDate());
            preparedStatement.setBoolean(4, bookBorrow.getIsReturned());
            preparedStatement.setString(5, bookBorrow.getReturnDate());
            preparedStatement.setString(6, bookBorrow.getReturnedDate());
            preparedStatement.setString(7, bookBorrow.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteBookBorrow(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM book_borrow WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public BookBorrow searchBookBorrow(String id) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book_borrow WHERE id=?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new BookBorrow(
                        resultSet.getString("id"),
                        resultSet.getString("book_id"),
                        resultSet.getString("member_id"),
                        resultSet.getString("borrowed_date"),
                        resultSet.getBoolean("isReturned"),
                        resultSet.getString("returnDate"),
                        resultSet.getString("ReturnedDate")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> bookBorrowList = new ArrayList<>();

        String query = "SELECT " +
                "    bb.id AS borrow_id, " +
                "    m.name AS member_name, " +
                "    b.name AS book_name, " +
                "    b.isbn AS book_isbn, " +
                "    b.availability_status, " +
                "    bb.borrowed_date, " +
                "    bb.returnDate AS return_date, " +
                "    bb.returnedDate AS returned_date, " +
                "    bb.isReturned " +
                "FROM book_borrow bb " +
                "JOIN member m ON bb.member_id = m.id " +
                "JOIN book b ON bb.book_id = b.id;";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = ((Statement) statement).executeQuery(query)) {

            while (resultSet.next()) {
                Map<String, Object> borrowData = new HashMap<>();
                borrowData.put("borrow_id", resultSet.getString("borrow_id"));
                borrowData.put("member_name", resultSet.getString("member_name"));
                borrowData.put("book_name", resultSet.getString("book_name"));
                borrowData.put("book_isbn", resultSet.getString("book_isbn"));
                borrowData.put("availability_status", resultSet.getBoolean("availability_status"));
                borrowData.put("borrowed_date", resultSet.getString("borrowed_date"));
                borrowData.put("return_date", resultSet.getString("return_date"));
                borrowData.put("returned_date", resultSet.getString("returned_date"));
                borrowData.put("isReturned", resultSet.getBoolean("isReturned"));

                bookBorrowList.add(borrowData);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book borrow data", e);
        }

        return bookBorrowList;
    }

    public String nextId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT id FROM book_borrow ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                String lastId = resultSet.getString("id");
                Integer number = Integer.parseInt(lastId.substring(4));
                number++;
                return String.format("BRW-%04d", number);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList <BookBorrow> searchPendingBookBorrowByMemberId(String memberId) {
        ArrayList <BookBorrow> bookBorrows = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book_borrow WHERE member_id=? AND isReturned=false");
            preparedStatement.setString(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                bookBorrows.add(new BookBorrow(
                        resultSet.getString("id"),
                        resultSet.getString("book_id"),
                        resultSet.getString("member_id"),
                        resultSet.getString("borrowed_date"),
                        resultSet.getBoolean("isReturned"),
                        resultSet.getString("returnDate"),
                        resultSet.getString("ReturnedDate")
                ));
            }
            return bookBorrows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
