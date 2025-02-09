package controller.publisher;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherController implements PublisherService{
    @Override
    public boolean addPublisher(Publisher publisher) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO publisher (id,name,location,contact) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, publisher.getId());
            preparedStatement.setString(2, publisher.getName());
            preparedStatement.setString(3, publisher.getLocation());
            preparedStatement.setString(4, publisher.getContact());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatePublisher(Publisher publisher) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE publisher SET name=?, location=?, contact=?  WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getLocation());
            preparedStatement.setString(3, publisher.getContact());
            preparedStatement.setString(4, publisher.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePublisher(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM publisher WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Publisher searchPublisher(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM publisher WHERE id=" + "'" + id + "'");
            resultSet.next();

            return new Publisher(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Publisher> getAll() {
        try {
            List<Publisher> publisherList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM publisher");

            while (resultSet.next()) {
                publisherList.add(
                        new Publisher(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4)
                        )  );

            }
            return publisherList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<String> getPublisherNames(){
        ObservableList<String> publisherNameList = FXCollections.observableArrayList();
        List<Publisher> publisherList = getAll();
        publisherList.forEach(publisher -> {
            publisherNameList.add(publisher.getName());
        });
        return publisherNameList;
    }
}
