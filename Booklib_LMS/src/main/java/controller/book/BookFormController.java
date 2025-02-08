package controller.book;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BookFormController {

    @FXML
    private JFXCheckBox chkAvailability;

    @FXML
    private JFXComboBox<?> cmbAuthor;

    @FXML
    private JFXComboBox<?> cmbCategory;

    @FXML
    private JFXComboBox<?> cmbPublisher;

    @FXML
    private TableColumn<?, ?> colauthor;

    @FXML
    private TableColumn<?, ?> colavailability;

    @FXML
    private TableColumn<?, ?> colcategory;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colisbn;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colpublisher;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtName;

    @FXML
    void btnAddAuthorOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/autherform.fxml"))));
        stage.show();


    }

    @FXML
    void btnAddCategoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddPublisherOnAction(ActionEvent event) {

    }

    @FXML
    void chkAvailabilityOnAction(ActionEvent event) {

    }

}
