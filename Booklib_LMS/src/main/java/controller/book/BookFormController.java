package controller.book;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.sun.javafx.collections.MappingChange;
import controller.author.AuthorController;
import controller.category.CategoryController;
import controller.publisher.PublisherController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Book;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BookFormController {

    @FXML
    public TableView tblBookview;

    @FXML
    private JFXCheckBox chkAvailability;

    @FXML
    private JFXComboBox<String> cmbAuthor;

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbPublisher;

    @FXML
    private TableColumn<Map, String> colauthor;

    @FXML
    private TableColumn<Map, Boolean> colavailability;

    @FXML
    private TableColumn<Map, String> colcategory;

    @FXML
    private TableColumn<Map, String> colid;

    @FXML
    private TableColumn<Map, String> colisbn;

    @FXML
    private TableColumn<Map, String> colname;

    @FXML
    private TableColumn<Map, String> colpublisher;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtName;


    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtSearch;

    private final BookController bookController = new BookController();
    private final CategoryController categoryController = new CategoryController();
    private final AuthorController authorController = new AuthorController();
    private final PublisherController publisherController = new PublisherController();

    public void initialize(){
        loadAuthorNames();
        loadCategoryNames();
        loadPublisherNames();
        populateTable();
        tblBookview.setOnMouseClicked(this::tblBookviewOnMouseClicked);

    }

    private void loadAuthorNames() {
        cmbAuthor.setItems(new AuthorController().getAuthorNames());
    }

    private void loadCategoryNames() {
        cmbCategory.setItems(new CategoryController().getcategoryNames());
    }
    private void loadPublisherNames() {
        cmbPublisher.setItems(new PublisherController().getPublisherNames());
    }

    @FXML
    void btnAddAuthorOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/authorform.fxml"))));
        stage.show();


    }

    @FXML
    void btnAddCategoryOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Categoryform.fxml"))));
        stage.show();
    }

    @FXML
    void btnAddPublisherOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/publisherform.fxml"))));
        stage.show();
    }

    @FXML
    void chkAvailabilityOnAction(ActionEvent event) {


    }

    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();

    }

    @FXML
    public void btnAddOnAction(ActionEvent actionEvent) {

        if (txtID.getText().isEmpty() ||
                txtName.getText().isEmpty() ||
                txtISBN.getText().isEmpty() ||
                cmbCategory.getValue() == null ||
                cmbAuthor.getValue() == null ||
                cmbPublisher.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields before adding a Book.");
            return;
        }

        Book book = new Book(
                txtID.getText(),
                txtName.getText(),
                txtISBN.getText(),
                chkAvailability.isSelected(),
                categoryController.searchCategoryByName(cmbCategory.getValue()).getId(),
                authorController.searchAuthorByName(cmbAuthor.getValue()).getId(),
                publisherController.searchPublisherByName(cmbPublisher.getValue()).getId()
        );



        boolean success;

        if (btnAdd.getText().equals("Update") ) {
            success = bookController.updateBook(book);
        } else {
            success = bookController.addBook(book);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Book successfully saved!");
            clearFields();
            populateTable();
            btnAdd.setText("Add");
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }

        System.out.println(book);

    }
    private void populateTable() {
        ObservableList<Map<String, Object>> bookList = FXCollections.observableArrayList(bookController.getAll());

        colid.setCellValueFactory(new MapValueFactory<String>("id"));
        colname.setCellValueFactory(new MapValueFactory<String>("name"));
        colisbn.setCellValueFactory(new MapValueFactory<String>("isbn"));
        colavailability.setCellValueFactory(new MapValueFactory<Boolean>("availability"));
        colcategory.setCellValueFactory(new MapValueFactory<String>("category"));
        colauthor.setCellValueFactory(new MapValueFactory<String>("author"));
        colpublisher.setCellValueFactory(new MapValueFactory<String>("publisher"));

        tblBookview.setItems(bookList);
    }



    private void searchCategory() {
        String searchText = txtSearch.getText().trim();

        // Get all books as a list of maps (from your getAll method)
        List<Map<String, Object>> books = bookController.getAll();
        ObservableList<Map<String, Object>> filteredList = FXCollections.observableArrayList();

        // Iterate over each book (which is a Map<String, Object>)
        for (Map<String, Object> book : books) {
            String id = book.get("id").toString().toUpperCase();
            String name = book.get("name").toString().toLowerCase();

            // Check if the ID or name contains the search text
            if (id.contains(searchText.toUpperCase()) || name.contains(searchText.toLowerCase())) {
                filteredList.add(book);
            }
        }

        // Set the filtered list to the table view
        tblBookview.setItems(filteredList);
    }


    private void clearFields() {
        txtID.clear();
        txtName.clear();
        txtISBN.clear();
        chkAvailability.setSelected(false);
        cmbCategory.setValue(null);
        cmbAuthor.setValue(null);
        cmbPublisher.setValue(null);

        btnAdd.setText("Add");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }


    void tblBookviewOnMouseClicked(MouseEvent event) {
        Map<String, Object> selectedBook = (Map<String, Object>) tblBookview.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            txtID.setText(selectedBook.get("id").toString());
            txtName.setText(selectedBook.get("name").toString());
            txtISBN.setText(selectedBook.get("isbn").toString());
            chkAvailability.setSelected((Boolean) selectedBook.get("availability"));

            cmbCategory.setValue(selectedBook.get("category").toString());
            cmbAuthor.setValue(selectedBook.get("author").toString());
            cmbPublisher.setValue(selectedBook.get("publisher").toString());

            btnAdd.setText("Update");
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtID.getText().trim();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please select a Book to delete.");
            return;
        }

        boolean success = bookController.deleteBook(id);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Book successfully deleted!");
            clearFields();
            populateTable();
            btnAdd.setText("Add");
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        searchCategory();
    }
}
