package controller.bookborrow;

import com.jfoenix.controls.JFXButton;
import controller.book.BookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import model.Book;
import model.BookBorrow;
import model.Category;
import model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Bookborrowform {

    @FXML
    private JFXButton btnClear1;

    @FXML
    private JFXButton btnClear2;

    @FXML
    private JFXButton btnClear3;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnIsue;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableColumn<Map, String> colauthor;

    @FXML
    private TableColumn<?, ?> colbook;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<Map, String> colid;

    @FXML
    private TableColumn<Map, String> colname;

    @FXML
    private Label lblPoints;

    @FXML
    private Label rightPnlLblId;

    @FXML
    private Label rightPnlLblName;

    @FXML
    private Label rightPnlLblStatus;

    @FXML
    private Label rightPnlLblTp;

    @FXML
    private TableView<?> rightPnlTblView;

    @FXML
    private Spinner<Integer> spinnerDays;

    @FXML
    private TableView<Map<String, Object>> tblBook;

    @FXML
    private TextField txtId1;

    @FXML
    private TextField txtId2;

    @FXML
    private TextField txtId3;

    @FXML
    private TextField txtName1;

    @FXML
    private TextField txtName2;

    @FXML
    private TextField txtName3;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton btnReturnBook1;

    @FXML
    private JFXButton btnReturnBook2;

    @FXML
    private JFXButton btnReturnBook3;

    @FXML
    private Label lblFineAmount;

    @FXML
    private Label lblPBook1;

    @FXML
    private Label lblPBook2;

    @FXML
    private Label lblPBook3;

    @FXML
    private Label lblfFne;

    @FXML
    private BookController bookController = new BookController();

    @FXML
    private BookBorrowController bookBorrowController = new BookBorrowController();

    @FXML
    public void initialize() {
        setRightpnl();
        populateTable();
        tblBook.setOnMouseClicked(this::tblBookviewOnMouseClicked);
        setSpinnerDays();
        showPendingBooks();
    }

    @FXML
    void btnClear1OnAction(ActionEvent event) {
        txtId1.clear();
        txtName1.clear();

    }

    @FXML
    void btnClear2OnAction(ActionEvent event) {
        txtId2.clear();
        txtName2.clear();

    }

    @FXML
    void btnClear3OnAction(ActionEvent event) {
        txtId3.clear();
        txtName3.clear();

    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        showPendingBooks();

    }

    @FXML
    void btnIsueOnAction(ActionEvent event) {
        Book book1 = bookController.searchBook(txtId1.getText());
        Book book2 = bookController.searchBook(txtId2.getText());
        Book book3 = bookController.searchBook(txtId3.getText());

        Integer days = spinnerDays.getValue();

            boolean bookBorrow1 = bookBorrowController.addBookBorrow(
                    new BookBorrow(
                            bookBorrowController.nextId(),
                            txtId1.getText().trim(),
                            rightPnlLblId.getText(),
                            LocalDate.now().toString(),
                            false,
                            LocalDate.now().plusDays(days).toString(),
                            null
                    )
            );



            boolean bookBorrow2 = bookBorrowController.addBookBorrow(
                    new BookBorrow(
                            bookBorrowController.nextId(),
                            txtId2.getText().trim(),
                            rightPnlLblId.getText(),
                            LocalDate.now().toString(),
                            false,
                            LocalDate.now().plusDays(days).toString(),
                            null
                    )
            );



            boolean bookBorrow3 = bookBorrowController.addBookBorrow(
                    new BookBorrow(
                            bookBorrowController.nextId(),
                            txtId3.getText().trim(),
                            rightPnlLblId.getText(),
                            LocalDate.now().toString(),
                            false,
                            LocalDate.now().plusDays(days).toString(),
                            null
                    )
            );

        txtId1.clear();
        txtId2.clear();
        txtId3.clear();
        txtName1.clear();
        txtName2.clear();
        txtName3.clear();
        populateTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    private void setRightpnl(){
        Member m1 = bookborrowloginformController.memberar[0];
        rightPnlLblId.setText(m1.getId());
        rightPnlLblName.setText(m1.getName());
        rightPnlLblTp.setText(m1.getContact());
        rightPnlLblStatus.setText("Active");

    }

    private void populateTable() {
        ObservableList<Map<String, Object>> bookList = FXCollections.observableArrayList(bookController.getAll());
        colid.setCellValueFactory(new MapValueFactory<String>("id"));
        colname.setCellValueFactory(new MapValueFactory<String>("name"));
        colauthor.setCellValueFactory(new MapValueFactory<String>("author"));
        tblBook.setItems(bookList);
    }

    void tblBookviewOnMouseClicked(MouseEvent event) {
        Map<String, Object> selectedCategory = tblBook.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            if (txtId1.getText().isEmpty()) {
                txtId1.setText(selectedCategory.get("id").toString());
                txtName1.setText(selectedCategory.get("name").toString());
            } else if (txtId2.getText().isEmpty()) {
                txtId2.setText(selectedCategory.get("id").toString());
                txtName2.setText(selectedCategory.get("name").toString());
            } else if (txtId3.getText().isEmpty()) {
                txtId3.setText(selectedCategory.get("id").toString());
                txtName3.setText(selectedCategory.get("name").toString());
            } else {
                System.out.println("All fields are filled. Please clear fields before adding more.");
            }
        }
    }

    void setSpinnerDays() {
        spinnerDays.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 7));
    }

    public void btnReturnBook1OnAction(ActionEvent actionEvent) {
    }

    public void btnReturnBook2OnAction(ActionEvent actionEvent) {
    }

    public void btnReturnBook3OnAction(ActionEvent actionEvent) {

    }

    public void showPendingBooks() {
        ArrayList<BookBorrow> bookBorrows = bookBorrowController.searchPendingBookBorrowByMemberId(rightPnlLblId.getText());

        // Check if bookBorrows is null or empty
        if (bookBorrows == null || bookBorrows.isEmpty()) {
            System.out.println("No pending books found.");
            clearPendingBookLabels();
            return;
        }

        // Clear previous labels before updating
        clearPendingBookLabels();

        // Safely set labels with null checks
        for (int i = 0; i < bookBorrows.size() && i < 3; i++) {
            BookBorrow borrow = bookBorrows.get(i);
            if (borrow != null && borrow.getBookId() != null) {
                String bookId = borrow.getBookId();

                if (i == 0 && lblPBook1 != null) {
                    lblPBook1.setText(bookId);
                } else if (i == 1 && lblPBook2 != null) {
                    lblPBook2.setText(bookId);
                } else if (i == 2 && lblPBook3 != null) {
                    lblPBook3.setText(bookId);
                }
            }
        }
    }

    // Helper method to clear labels safely
    private void clearPendingBookLabels() {
        if (lblPBook1 != null) {
            lblPBook1.setText("");
        }
        if (lblPBook2 != null) {
            lblPBook2.setText("");
        }
        if (lblPBook3 != null) {
            lblPBook3.setText("");
        }
    }





}