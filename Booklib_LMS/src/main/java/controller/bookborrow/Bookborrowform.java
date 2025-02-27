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
import java.util.Optional;

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

        // Set buttons enabled/disabled based on pending books
        updateReturnButtonsState();
    }

    private void updateReturnButtonsState() {
        btnReturnBook1.setDisable(lblPBook1.getText().isEmpty());
        btnReturnBook2.setDisable(lblPBook2.getText().isEmpty());
        btnReturnBook3.setDisable(lblPBook3.getText().isEmpty());
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
        //nooooooo workingg---------------------------------------------------------------------
        // Input validation
        if (rightPnlLblId.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No member selected",
                    "Please select a member before issuing books.");
            return;
        }

        // Validate that at least one book is selected
        if (txtId1.getText().isEmpty() && txtId2.getText().isEmpty() && txtId3.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No books selected",
                    "Please select at least one book to borrow.");
            return;
        }

        Book book1 = !txtId1.getText().isEmpty() ? bookController.searchBook(txtId1.getText()) : null;
        Book book2 = !txtId2.getText().isEmpty() ? bookController.searchBook(txtId2.getText()) : null;
        Book book3 = !txtId3.getText().isEmpty() ? bookController.searchBook(txtId3.getText()) : null;

        Integer days = spinnerDays.getValue();

        boolean allSuccess = true;

        // Process book 1 if selected
        if (!txtId1.getText().isEmpty()) {
            if (book1 == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Book not found",
                        "Book ID " + txtId1.getText() + " could not be found.");
                allSuccess = false;
            } else {
                boolean success = bookBorrowController.addBookBorrow(
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
                allSuccess &= success;
            }
        }

        // Process book 2 if selected
        if (!txtId2.getText().isEmpty()) {
            if (book2 == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Book not found",
                        "Book ID " + txtId2.getText() + " could not be found.");
                allSuccess = false;
            } else {
                boolean success = bookBorrowController.addBookBorrow(
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
                allSuccess &= success;
            }
        }

        // Process book 3 if selected
        if (!txtId3.getText().isEmpty()) {
            if (book3 == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Book not found",
                        "Book ID " + txtId3.getText() + " could not be found.");
                allSuccess = false;
            } else {
                boolean success = bookBorrowController.addBookBorrow(
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
                allSuccess &= success;
            }
        }

        if (allSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Books borrowed",
                    "Books have been successfully borrowed.");
            txtId1.clear();
            txtId2.clear();
            txtId3.clear();
            txtName1.clear();
            txtName2.clear();
            txtName3.clear();
            populateTable();
            showPendingBooks();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to borrow books",
                    "Some or all books could not be borrowed.");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String searchTerm = txtSearch.getText().trim();

        if (searchTerm.isEmpty()) {
            // If search is empty, show all books
            populateTable();
            return;
        }

        ObservableList<Map<String, Object>> bookList = FXCollections.observableArrayList();

        // Get all books and filter
        for (Map<String, Object> book : bookController.getAll()) {
            String id = book.get("id").toString().toLowerCase();
            String name = book.get("name").toString().toLowerCase();
            String author = book.get("author").toString().toLowerCase();

            // Check if the search term matches any of the fields
            if (id.contains(searchTerm.toLowerCase()) ||
                    name.contains(searchTerm.toLowerCase()) ||
                    author.contains(searchTerm.toLowerCase())) {
                bookList.add(book);
            }
        }

        // Update the table with filtered results
        tblBook.setItems(bookList);

        // Show message if no results found
        if (bookList.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Search Results",
                    "No matching books found",
                    "No books match the search term: " + searchTerm);
        }
    }

    private void setRightpnl() {
        try {
            Member m1 = bookborrowloginformController.memberar[0];
            rightPnlLblId.setText(m1.getId());
            rightPnlLblName.setText(m1.getName());
            rightPnlLblTp.setText(m1.getContact());
            rightPnlLblStatus.setText("Active");
        } catch (Exception e) {
            System.err.println("Error setting right panel: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to load member information",
                    "Please log in again.");
        }
    }

    private void populateTable() {
        try {
            ObservableList<Map<String, Object>> bookList = FXCollections.observableArrayList(bookController.getAll());
            colid.setCellValueFactory(new MapValueFactory<String>("id"));
            colname.setCellValueFactory(new MapValueFactory<String>("name"));
            colauthor.setCellValueFactory(new MapValueFactory<String>("author"));
            tblBook.setItems(bookList);
        } catch (Exception e) {
            System.err.println("Error populating table: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to load book data",
                    "Database error: " + e.getMessage());
        }
    }

    void tblBookviewOnMouseClicked(MouseEvent event) {
        Map<String, Object> selectedBook = tblBook.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            if (txtId1.getText().isEmpty()) {
                txtId1.setText(selectedBook.get("id").toString());
                txtName1.setText(selectedBook.get("name").toString());
            } else if (txtId2.getText().isEmpty()) {
                txtId2.setText(selectedBook.get("id").toString());
                txtName2.setText(selectedBook.get("name").toString());
            } else if (txtId3.getText().isEmpty()) {
                txtId3.setText(selectedBook.get("id").toString());
                txtName3.setText(selectedBook.get("name").toString());
            } else {
                showAlert(Alert.AlertType.WARNING, "All Fields Filled",
                        "Maximum books selected",
                        "You can only borrow up to 3 books at a time. Please clear a field before adding more.");
            }
        }
    }

    void setSpinnerDays() {
        spinnerDays.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 7));
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
                    btnReturnBook1.setDisable(false);
                } else if (i == 1 && lblPBook2 != null) {
                    lblPBook2.setText(bookId);
                    btnReturnBook2.setDisable(false);
                } else if (i == 2 && lblPBook3 != null) {
                    lblPBook3.setText(bookId);
                    btnReturnBook3.setDisable(false);
                }
            }
        }

        updateReturnButtonsState();
    }

    public void btnReturnBook1OnAction(ActionEvent actionEvent) {
        returnBook(lblPBook1.getText());
    }

    public void btnReturnBook2OnAction(ActionEvent actionEvent) {
        returnBook(lblPBook2.getText());
    }

    public void btnReturnBook3OnAction(ActionEvent actionEvent) {
        returnBook(lblPBook3.getText());
    }

    private void returnBook(String bookId) {
        if (bookId == null || bookId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "No book selected",
                    "Please select a book to return.");
            return;
        }

        try {
            // Find the book borrow record
            ArrayList<BookBorrow> bookBorrows = bookBorrowController.searchPendingBookBorrowByMemberId(rightPnlLblId.getText());
            BookBorrow targetBorrow = null;

            for (BookBorrow borrow : bookBorrows) {
                if (borrow.getBookId().equals(bookId)) {
                    targetBorrow = borrow;
                    break;
                }
            }

            if (targetBorrow == null) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Book borrow record not found",
                        "Could not find a record for this book.");
                return;
            }

            // Confirm return with the user
            boolean confirmReturn = showConfirmationDialog("Return Book",
                    "Confirm book return",
                    "Are you sure you want to return the book: " + bookId + "?");

            if (confirmReturn) {
                // Mark the book as returned
                targetBorrow.setIsReturned(true);
                targetBorrow.setReturnDate(LocalDate.now().toString());

                boolean success = bookBorrowController.updateBookBorrow(targetBorrow);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Book returned successfully",
                            "The book has been marked as returned.");

                    // Refresh the pending books display
                    showPendingBooks();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Failed to return book",
                            "Database error occurred while updating the record.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error returning book: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to process book return",
                    "Error: " + e.getMessage());
        }
    }

    // Helper method to clear labels safely
    private void clearPendingBookLabels() {
        if (lblPBook1 != null) {
            lblPBook1.setText("");
            btnReturnBook1.setDisable(true);
        }
        if (lblPBook2 != null) {
            lblPBook2.setText("");
            btnReturnBook2.setDisable(true);
        }
        if (lblPBook3 != null) {
            lblPBook3.setText("");
            btnReturnBook3.setDisable(true);
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method for confirmation dialogs
    private boolean showConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}