package controller.member;

import DB.DBConnection;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberController implements MemberService {

    @Override
    public boolean addMember(Member member) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "INSERT INTO member (id, name, address, contact, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, member.getId());
            preparedStatement.setString(2, member.getName());
            preparedStatement.setString(3, member.getAddress());
            preparedStatement.setString(4, member.getContact());
            preparedStatement.setString(5, member.getEmail());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMember(Member member) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE member SET name=?, address=?, contact=?, email=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getAddress());
            preparedStatement.setString(3, member.getContact());
            preparedStatement.setString(4, member.getEmail());
            preparedStatement.setString(5, member.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMember(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM member WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Member searchMember(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM member WHERE id=" + "'" + id + "'");
            resultSet.next();
            return new Member(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Member> getAll() {
        try {
            List<Member> itemList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM member");

            while (resultSet.next()) {
                itemList.add(
                        new Member(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        )  );

            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
