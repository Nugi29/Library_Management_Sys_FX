package controller.publisher;

import com.jfoenix.controls.JFXButton;
import controller.category.CategoryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Category;
import model.Publisher;

import java.util.List;

public class PublisherFormController {

    @FXML
    private Button btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> collocation;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableView<Publisher> tblpublisherview;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    private final PublisherController publisherController = new PublisherController();

    @FXML
    public void initialize() {
        populateTable();
        tblpublisherview.setOnMouseClicked(this::tblPublisherviewOnMouseClicked);



        Stage stage = (Stage) tblpublisherview.getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setResizable(false);

    }



    @FXML
    private void btnAddOnAction(ActionEvent event) {
        if (    txtId.getText().isEmpty() ||
                txtName.getText().isEmpty() ||
                txtLocation.getText().isEmpty() ||
                txtContact.getText().isEmpty()
        ) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields before adding a Publisher.");
            return;
        }

        Publisher publisher = new Publisher(txtId.getText(),txtName.getText(),txtLocation.getText(),txtContact.getText());

        boolean success;

        if (btnAdd.getText().equals("Update") ) {
            success = publisherController.updatePublisher(publisher);
        } else {
            success = publisherController.addPublisher(publisher);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Publisher successfully saved!");
            clearFields();
            populateTable();
            btnAdd.setText("Add");
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }
    }

    private void populateTable() {
        ObservableList<Publisher> publisherList = FXCollections.observableArrayList(publisherController.getAll());
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        collocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblpublisherview.setItems(publisherList);

    }

    private void searchCategory(){
        String searchText = txtSearch.getText().trim();

        List<Publisher> publishers = publisherController.getAll();
        ObservableList<Publisher> filteredList = FXCollections.observableArrayList();

        for (Publisher publisher : publishers) {
            if (publisher.getId().toUpperCase().contains(searchText.toUpperCase()) ||
                    publisher.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(publisher);
            }
        }
        tblpublisherview.setItems(filteredList);
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtLocation.clear();
        txtContact.clear();
        btnAdd.setText("Add");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }

    private void tblPublisherviewOnMouseClicked(MouseEvent event) {
        Publisher selectedPublisher = tblpublisherview.getSelectionModel().getSelectedItem();
        if (selectedPublisher != null) {
            txtId.setText(selectedPublisher.getId());
            txtName.setText(selectedPublisher.getName());
            txtLocation.setText(selectedPublisher.getLocation());
            txtContact.setText(selectedPublisher.getContact());
            btnAdd.setText("Update");
        }
    }

    @FXML
    private void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a category to delete.");
            return;
        }

        boolean success = publisherController.deletePublisher(id);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Publisher successfully deleted!");
            clearFields();
            populateTable();
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }
    }

    @FXML
    private void btnSearchOnAction(ActionEvent event) {
        searchCategory();
    }


}
