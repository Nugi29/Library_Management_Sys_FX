package controller;

import DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        String key = "#5541Asd";

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

        basicTextEncryptor.setPassword(key);

        String SQL = "SELECT * FROM admin WHERE  username="+"'"+txtUsername.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(SQL);

        if (resultSet.next() ){
            Admin admin = new Admin(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );



            if (basicTextEncryptor.decrypt(admin.getPassword()).equals(txtPassword.getText())){
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
                stage.setTitle("Booklib LMS");
                stage.setResizable(false);
                stage.show();

                txtPassword.getScene().getWindow().hide();



            }else{
                new Alert(Alert.AlertType.ERROR,"Check your Password!!").show();
                txtUsername.clear();
                txtPassword.clear();
            }



        }else{
            new Alert(Alert.AlertType.ERROR,"User Not Found!!").show();
            txtUsername.clear();
            txtPassword.clear();
        }
    }

//    @FXML
//    void btnRegisterOnAction(ActionEvent event) {
//
//    }

}
