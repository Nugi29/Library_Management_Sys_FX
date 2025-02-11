package controller.book;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Author;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookController implements BookService {


    @Override
    public boolean addBook(Book book) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO book (id,name,isbn,availability_status,category_id,author_id,publisher_id) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getId());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setBoolean(4, book.getAvailability_status());
            preparedStatement.setString(5, book.getCategory_id());
            preparedStatement.setString(6, book.getAuthor_id());
            preparedStatement.setString(7, book.getPublisher_id());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateBook(Book member) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE book SET name=?, isbn=?, availability_status=?, category_id=?, author_id=?, publisher_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getIsbn());
            preparedStatement.setBoolean(3, member.getAvailability_status());
            preparedStatement.setString(4, member.getCategory_id());
            preparedStatement.setString(5, member.getAuthor_id());
            preparedStatement.setString(6, member.getPublisher_id());
            preparedStatement.setString(7, member.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBook(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM book WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Book searchBook(String id) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE id=?");
            preparedStatement.setString(1, id);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Map<String, Object>> getAll() {
        try {
            List<Map<String, Object>> bookList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT b.id, b.name, b.isbn, b.availability_status, " +
                    "c.name AS category, " +
                    "a.name AS author, " +
                    "p.name AS publisher " +
                    "FROM book b " +
                    "JOIN category c ON b.category_id = c.id " +
                    "JOIN author a ON b.author_id = a.id " +
                    "JOIN publisher p ON b.publisher_id = p.id";

            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                Map<String, Object> bookData = new HashMap<>();
                bookData.put("id", resultSet.getString("id"));
                bookData.put("name", resultSet.getString("name"));
                bookData.put("isbn", resultSet.getString("isbn"));
                bookData.put("availability", resultSet.getBoolean("availability_status"));
                bookData.put("category", resultSet.getString("category"));
                bookData.put("author", resultSet.getString("author"));
                bookData.put("publisher", resultSet.getString("publisher"));

                bookList.add(bookData);
            }
            return bookList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
