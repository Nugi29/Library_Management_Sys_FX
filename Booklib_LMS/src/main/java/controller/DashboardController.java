package controller;

import DB.DBConnection;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardController {

    public String getTotalBookCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT COUNT(*) FROM book");
            resultSet.next();
            int bookCount = resultSet.getInt(1);
            String formattedBookCount = String.format("%03d", bookCount);
            return formattedBookCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTotalUserCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT COUNT(*) FROM member");
            resultSet.next();
            int userCount = resultSet.getInt(1);
            String formattedUserCount = String.format("%03d", userCount);
            return formattedUserCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTotalFineCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT COUNT(*) FROM fine");
            resultSet.next();
            int fineCount = resultSet.getInt(1);
            String formattedFineCount = String.format("%03d", fineCount);
            return formattedFineCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer getTotalBorrowedBooksCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT COUNT(*) FROM book_borrow WHERE isReturned = 0";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getTotalReturnedBooksCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT COUNT(*) FROM book_borrow WHERE isReturned = 1";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
