package controller.category;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Author;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryController implements CategoryService{

    @Override
    public boolean addCategory(Category category) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO category (id, name) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getId());
            preparedStatement.setString(2, category.getName());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCategory(Category category) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE category SET name=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCategory(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM category WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category searchCategory(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM author WHERE id=" + "'" + id + "'");
            resultSet.next();

            return new Category(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> getAll() {
        try {
            List<Category> categoryList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM category");

            while (resultSet.next()) {
                categoryList.add(
                        new Category(
                                resultSet.getString(1),
                                resultSet.getString(2)
                        )  );

            }
            return categoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getcategoryNames(){
        ObservableList<String> categoryNameList = FXCollections.observableArrayList();
        List<Category> categoryList = getAll();
        categoryList.forEach(category -> {
            categoryNameList.add(category.getName());
        });
        return categoryNameList;
    }
}
