package controller.author;

import com.jfoenix.controls.JFXButton;
import controller.member.MemberController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Author;
import model.Member;

import java.util.List;

public class AuthorFormController {

    @FXML
    public TableView tblAuthorview;

    @FXML
    public TextField txtSearch;

    @FXML
    public JFXButton btnSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    private final AuthorController authorController = new AuthorController();

    @FXML
    public void initialize() {
        populateTable();
        tblAuthorview.setOnMouseClicked(this::tblAuthorviewOnMouseClicked);
    }



    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() ||
                txtName.getText().isEmpty() ||
                txtContact.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields before adding a member.");
            return;
        }

        Author author = new Author( txtId.getText(),
                                    txtName.getText(),
                                    txtContact.getText()
        );

        boolean success;

        if (btnAdd.getText().equals("Update Member") ) {
            success = authorController.updateAuthor(author);
        } else {
            success = authorController.addAuthor(author);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Member successfully saved!");
            clearFields();
            populateTable();
            btnAdd.setText("Add Member");
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }
    }

    private void populateTable() {
        ObservableList<Author> authorList = FXCollections.observableArrayList(authorController.getAll());
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblAuthorview.setItems(authorList);

    }

    private void searchAuthor(){
        String searchText = txtSearch.getText().trim();

        List<Author> authors = authorController.getAll();
        ObservableList<Author> filteredList = FXCollections.observableArrayList();

        for (Author author : authors) {
            if (author.getId().toUpperCase().contains(searchText.toUpperCase()) ||
                    author.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(author);
            }
        }
        tblAuthorview.setItems(filteredList);
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        btnAdd.setText("Add Member");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }


    void tblAuthorviewOnMouseClicked(MouseEvent event) {
        Author selectedauthor = (Author) tblAuthorview.getSelectionModel().getSelectedItem();
        if (selectedauthor != null) {
            txtId.setText(selectedauthor.getId());
            txtName.setText(selectedauthor.getName());
            txtContact.setText(selectedauthor.getContact());
            btnAdd.setText("Update Member");
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a member to delete.");
            return;
        }

        boolean success = authorController.deleteAuthor(id);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Member successfully deleted!");
            clearFields();
            populateTable();
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }
    }
    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) {
        searchAuthor();
    }
}
