package controller.book;

import DB.DBConnection;
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
    public boolean updateBook(Book book) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE book SET name=?, isbn=?, availability_status=?, category_id=?, author_id=?, publisher_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setBoolean(3, book.getAvailability_status());
            preparedStatement.setString(4, book.getCategory_id());
            preparedStatement.setString(5, book.getAuthor_id());
            preparedStatement.setString(6, book.getPublisher_id());
            preparedStatement.setString(7, book.getId());
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
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Book(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("availability_status"),
                        resultSet.getString("category_id"),
                        resultSet.getString("author_id"),
                        resultSet.getString("publisher_id")
                );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public Book searchBookByName(String name) {

        try {

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE name=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Book(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("availability_status"),
                        resultSet.getString("category_id"),
                        resultSet.getString("author_id"),
                        resultSet.getString("publisher_id")
                );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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

    //---------------------------------------------------------------------------------------
    public List<Map<String, Object>> getAllAvailable() {
        try {
            List<Map<String, Object>> bookList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();

            String query ="SELECT b.id, b.name, b.isbn, b.availability_status,c.name AS category,a.name AS author,p.name AS publisher FROM book b JOIN category c ON b.category_id = c.id  JOIN author a ON b.author_id = a.id JOIN publisher p ON b.publisher_id = p.id WHERE b.availability_status = 1";


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

    public String nextId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT id FROM book ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString("id");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Book findBookByName(String bookName) {

        for (Map<String, Object> book : getAll()) {
            if (book.get("name").toString().equals(bookName)) {
                return searchBook(book.get("id").toString());
            }
        }
        return null;
    }

}
