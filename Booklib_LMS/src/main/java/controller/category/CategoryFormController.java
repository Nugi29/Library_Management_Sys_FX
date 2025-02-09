package controller.category;

import com.jfoenix.controls.JFXButton;
import controller.author.AuthorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Author;
import model.Category;

import java.util.List;

public class CategoryFormController {

    @FXML
    private Button btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableView<Category> tblCategoryview;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    private final CategoryController categoryController = new CategoryController();

    @FXML
    public void initialize() {
        populateTable();
        tblCategoryview.setOnMouseClicked(this::tblCategoryviewOnMouseClicked);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() ||
                txtName.getText().isEmpty() ) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields before adding a Category.");
            return;
        }

        Category category = new Category(txtId.getText(),txtName.getText());



        boolean success;

        if (btnAdd.getText().equals("Update") ) {
            success = categoryController.updateCategory(category);
        } else {
            success = categoryController.addCategory(category);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Category successfully saved!");
            clearFields();
            populateTable();
            btnAdd.setText("Add");
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }

    }


    private void populateTable() {
        ObservableList<Category> categoryList = FXCollections.observableArrayList(categoryController.getAll());
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCategoryview.setItems(categoryList);

    }

    private void searchCategory(){
        String searchText = txtSearch.getText().trim();

        List<Category> categorys = categoryController.getAll();
        ObservableList<Category> filteredList = FXCollections.observableArrayList();

        for (Category category : categorys) {
            if (category.getId().toUpperCase().contains(searchText.toUpperCase()) ||
                    category.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(category);
            }
        }
        tblCategoryview.setItems(filteredList);
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        btnAdd.setText("Add");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }


    void tblCategoryviewOnMouseClicked(MouseEvent event) {
        Category selectedcategory = tblCategoryview.getSelectionModel().getSelectedItem();
        if (selectedcategory != null) {
            txtId.setText(selectedcategory.getId());
            txtName.setText(selectedcategory.getName());
            btnAdd.setText("Update");
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
            showAlert(Alert.AlertType.WARNING, "Please select a category to delete.");
            return;
        }

        boolean success = categoryController.deleteCategory(id);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Category successfully deleted!");
            clearFields();
            populateTable();
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        searchCategory();
    }

}
