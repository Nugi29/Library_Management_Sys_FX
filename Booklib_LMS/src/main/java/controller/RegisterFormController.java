package controller;

import DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Admin;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {


    @FXML
    private Button btnRegister;

    @FXML
    public AnchorPane dashbordSubAnchor;

    @FXML
    private PasswordField txtCfmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;


    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException {
        String key = "#5541Asd";

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

        basicTextEncryptor.setPassword(key);


        String SQL = "INSERT INTO admin (username,email,password) VALUES(?,?,?)";

        if (txtPassword.getText().equals(txtCfmPassword.getText())){

            Connection connection = DBConnection.getInstance().getConnection();

            ResultSet resultSet = connection
                    .createStatement()
                    .executeQuery("SELECT * FROM admin WHERE username=" + "'" + txtUsername.getText() + "'");

            if (!resultSet.next() && !txtUsername.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
                Admin admin = new Admin(
                        txtUsername.getText(),
                        txtEmail.getText(),
                        txtPassword.getText()
                );

                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1, admin.getUsername());
                psTm.setString(2, admin.getEmail());
                psTm.setString(3,basicTextEncryptor.encrypt(admin.getPassword()));
                psTm.executeUpdate();

                new Alert(Alert.AlertType.INFORMATION,"User Registered!").show();

                txtUsername.clear();
                txtEmail.clear();
                txtPassword.clear();
                txtCfmPassword.clear();

            }else{
                new Alert(Alert.AlertType.ERROR,"user found!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Check your password!!").show();
        }

    }



}
