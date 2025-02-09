package controller.author;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Author;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorController implements AuthorService{

    @Override
    public boolean addAuthor(Author author) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO author (id, name,contact) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, author.getId());
            preparedStatement.setString(2, author.getName());
            preparedStatement.setString(3, author.getContact());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateAuthor(Author author) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE author SET name=?, contact=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getContact());
            preparedStatement.setString(3, author.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteAuthor(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM author WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author searchAuthor(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM author WHERE id=" + "'" + id + "'");
            resultSet.next();

            return new Author(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> getAll() {
        try {
            List<Author> itemList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM author");

            while (resultSet.next()) {
                itemList.add(
                        new Author(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3)
                        )  );

            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<String> getAuthorNames(){
        ObservableList<String> authorNameList = FXCollections.observableArrayList();
        List<Author> authorList = getAll();
        authorList.forEach(author -> {
            authorNameList.add(author.getName());
        });
        return authorNameList;
    }

}
