package controller.book;

import DB.DBConnection;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookController implements BookService {
    @Override
    public boolean addBook(Book member) {
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            String query = "INSERT INTO book (id,name,isbn,availability_status,publisher,) VALUES (?,?,?,?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, member.getId());
//            preparedStatement.setString(2, member.getName());
//            preparedStatement.setString(3, member.getIsbn());
//            preparedStatement.setBoolean(4, member.getAvailability_status());
//            preparedStatement.setString(5, member.getPublisher());
//            preparedStatement.setString(6, member.getAuthor());
//            preparedStatement.setString(7, member.getCategory());
//
//
//            return preparedStatement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return false;
    }

    @Override
    public boolean updateBook(Book member) {
        return false;
    }

    @Override
    public boolean deleteBook(String id) {
        return false;
    }

    @Override
    public Book searchBook(String id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
