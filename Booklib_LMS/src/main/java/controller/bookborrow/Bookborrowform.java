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

    // Maximum number of books a user can borrow at once
    private final int MAX_BOOKS_ALLOWED = 3;

    @FXML
    private Label lblBorrowLimit;

    @FXML
    private Button btnReturnToGetNew;

    @FXML
    public void initialize() {
        try {
            setRightpnl();
            populateTable();
            tblBook.setOnMouseClicked(this::tblBookviewOnMouseClicked);
            setSpinnerDays();
            showPendingBooks();

            // Set the borrow limit label
            if (lblBorrowLimit != null) {
                lblBorrowLimit.setText("Book Limit: " + MAX_BOOKS_ALLOWED);
            }

            // Set buttons enabled/disabled based on pending books
            updateReturnButtonsState();
            updateBorrowLimitDisplay();
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Initialization Error",
                    "Failed to initialize the form",
                    "An error occurred: " + e.getMessage());
        }
    }

    private void updateReturnButtonsState() {
        btnReturnBook1.setDisable(lblPBook1.getText().isEmpty());
        btnReturnBook2.setDisable(lblPBook2.getText().isEmpty());
        btnReturnBook3.setDisable(lblPBook3.getText().isEmpty());
    }

    // Method to update the borrow limit display and return button
    private void updateBorrowLimitDisplay() {
        try {
            // Count how many books the user has borrowed
            ArrayList<BookBorrow> borrowedBooks = bookBorrowController.searchPendingBookBorrowByMemberId(rightPnlLblId.getText());
            int borrowedCount = (borrowedBooks != null) ? borrowedBooks.size() : 0;

            // Update the status display
            if (lblBorrowLimit != null) {
                lblBorrowLimit.setText("Books borrowed: " + borrowedCount + " / " + MAX_BOOKS_ALLOWED);
            }

            // If the user has reached the limit, enable the return-to-get-new button
            if (btnReturnToGetNew != null) {
                btnReturnToGetNew.setDisable(borrowedCount < MAX_BOOKS_ALLOWED);

                // Change button appearance based on whether user has reached limit
                if (borrowedCount >= MAX_BOOKS_ALLOWED) {
                    btnReturnToGetNew.getStyleClass().add("warning-button");
                } else {
                    btnReturnToGetNew.getStyleClass().remove("warning-button");
                }
            }

            // Disable the issue button if the user has reached the limit
            if (btnIsue != null) {
                boolean hasSelectedBooks = !txtId1.getText().isEmpty() || !txtId2.getText().isEmpty() || !txtId3.getText().isEmpty();
                btnIsue.setDisable(borrowedCount >= MAX_BOOKS_ALLOWED && hasSelectedBooks);

                // Show a tooltip if disabled
                if (borrowedCount >= MAX_BOOKS_ALLOWED) {
                    Tooltip tooltip = new Tooltip("You must return a book before borrowing a new one.");
                    btnIsue.setTooltip(tooltip);
                } else {
                    btnIsue.setTooltip(null);
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating borrow limit display: " + e.getMessage());
        }
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
    void btnReturnToGetNewOnAction(ActionEvent event) {
        showReturnDialog();
    }

    private void showReturnDialog() {
        try {
            // Get all pending books
            ArrayList<BookBorrow> borrowedBooks = bookBorrowController.searchPendingBookBorrowByMemberId(rightPnlLblId.getText());

            if (borrowedBooks == null || borrowedBooks.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Books to Return",
                        "You don't have any books to return.",
                        "Borrow some books first.");
                return;
            }

            // Create a dialog
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Return Books");
            dialog.setHeaderText("Select a book to return:");

            // Set the button types
            ButtonType returnButtonType = new ButtonType("Return", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(returnButtonType, ButtonType.CANCEL);

            // Create the book selection ListView
            ListView<String> listView = new ListView<>();
            ObservableList<String> bookItems = FXCollections.observableArrayList();

            // Map to keep track of book names to book borrow records
            java.util.Map<String, BookBorrow> bookMap = new java.util.HashMap<>();

            // Add each book to the list
            for (BookBorrow borrow : borrowedBooks) {
                Book book = bookController.searchBook(borrow.getBookId());
                if (book != null) {
                    String displayText = book.getName() + " (ID: " + book.getId() + ")";
                    bookItems.add(displayText);
                    bookMap.put(displayText, borrow);
                }
            }

            listView.setItems(bookItems);
            dialog.getDialogPane().setContent(listView);

            // Convert the result
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == returnButtonType) {
                    return listView.getSelectionModel().getSelectedItem();
                }
                return null;
            });

            // Show the dialog and process the result
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(bookString -> {
                BookBorrow borrowToReturn = bookMap.get(bookString);
                if (borrowToReturn != null) {
                    // Process the return
                    processBookReturn(borrowToReturn);
                }
            });
        } catch (Exception e) {
            System.err.println("Error showing return dialog: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to show return dialog",
                    "An error occurred: " + e.getMessage());
        }
    }

    private void processBookReturn(BookBorrow borrow) {
        try {
            // Mark the book as returned
            borrow.setIsReturned(true);
            borrow.setReturnedDate(LocalDate.now().toString());

            // Update the book's availability status
            Book book = bookController.searchBook(borrow.getBookId());

            if (book == null) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Book not found",
                        "Could not find the book with ID: " + borrow.getBookId());
                return;
            }

            book.setAvailability_status(true);
            bookController.updateBook(book);

            boolean success = bookBorrowController.updateBookBorrow(borrow);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Book returned successfully",
                        "The book has been marked as returned. You can now borrow a new book.");

                // Refresh the pending books display and table
                showPendingBooks();
                populateTable();
                updateBorrowLimitDisplay();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Failed to return book",
                        "Database error occurred while updating the record.");
            }
        } catch (Exception e) {
            System.err.println("Error processing book return: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to process book return",
                    "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    void btnIsueOnAction(ActionEvent event) {
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

        // Check if the user has reached the borrow limit
        ArrayList<BookBorrow> currentlyBorrowed = bookBorrowController.searchPendingBookBorrowByMemberId(rightPnlLblId.getText());
        int borrowedCount = (currentlyBorrowed != null) ? currentlyBorrowed.size() : 0;
        int selectedCount = 0;
        if (!txtId1.getText().isEmpty()) selectedCount++;
        if (!txtId2.getText().isEmpty()) selectedCount++;
        if (!txtId3.getText().isEmpty()) selectedCount++;

        if (borrowedCount + selectedCount > MAX_BOOKS_ALLOWED) {
            showAlert(Alert.AlertType.ERROR, "Borrow Limit Reached",
                    "You cannot borrow more than " + MAX_BOOKS_ALLOWED + " books",
                    "You currently have " + borrowedCount + " books borrowed. You can select up to "
                            + (MAX_BOOKS_ALLOWED - borrowedCount) + " more books, or return some books first.");
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
                book1.setAvailability_status(false);
                bookController.updateBook(book1);

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
                book2.setAvailability_status(false);
                bookController.updateBook(book2);
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
                book3.setAvailability_status(false);
                bookController.updateBook(book3);
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
            updateBorrowLimitDisplay();
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
            ObservableList<Map<String, Object>> bookList = FXCollections.observableArrayList(bookController.getAllAvailable());
            colid.setCellValueFactory(new MapValueFactory<>("id"));
            colname.setCellValueFactory(new MapValueFactory<>("name"));
            colauthor.setCellValueFactory(new MapValueFactory<>("author"));
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
            // Check if we're at the borrow limit
            ArrayList<BookBorrow> currentlyBorrowed = bookBorrowController.searchPendingBookBorrowByMemberId(rightPnlLblId.getText());
            int borrowedCount = (currentlyBorrowed != null) ? currentlyBorrowed.size() : 0;
            int selectedCount = 0;
            if (!txtId1.getText().isEmpty()) selectedCount++;
            if (!txtId2.getText().isEmpty()) selectedCount++;
            if (!txtId3.getText().isEmpty()) selectedCount++;

            if (borrowedCount + selectedCount >= MAX_BOOKS_ALLOWED) {
                showAlert(Alert.AlertType.WARNING, "Borrow Limit Reached",
                        "You cannot borrow more than " + MAX_BOOKS_ALLOWED + " books",
                        "You must return a book before selecting more.");
                return;
            }

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
        try {
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
                    Book book = bookController.searchBook(borrow.getBookId());

                    // Add null check for book
                    if (book == null) {
                        System.err.println("Book not found with ID: " + borrow.getBookId());
                        continue;
                    }

                    if (i == 0 && lblPBook1 != null) {
                        lblPBook1.setText(book.getName());
                        btnReturnBook1.setDisable(false);
                    } else if (i == 1 && lblPBook2 != null) {
                        lblPBook2.setText(book.getName());
                        btnReturnBook2.setDisable(false);
                    } else if (i == 2 && lblPBook3 != null) {
                        lblPBook3.setText(book.getName());
                        btnReturnBook3.setDisable(false);
                    }
                }
            }

            updateReturnButtonsState();
            updateBorrowLimitDisplay();
        } catch (Exception e) {
            System.err.println("Error showing pending books: " + e.getMessage());
            e.printStackTrace();
            clearPendingBookLabels();
        }
    }

    public void btnReturnBook1OnAction(ActionEvent actionEvent) {
        try {
            Book book = bookController.searchBookByName(lblPBook1.getText());
            if (book != null && book.getId() != null) {
                returnBook(book.getId());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Book not found",
                        "Unable to find book with name: " + lblPBook1.getText());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to process return",
                    "An error occurred: " + e.getMessage());
        }
    }

    public void btnReturnBook2OnAction(ActionEvent actionEvent) {
        try {
            Book book = bookController.searchBookByName(lblPBook2.getText());
            if (book != null && book.getId() != null) {
                returnBook(book.getId());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Book not found",
                        "Unable to find book with name: " + lblPBook2.getText());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to process return",
                    "An error occurred: " + e.getMessage());
        }
    }

    public void btnReturnBook3OnAction(ActionEvent actionEvent) {
        try {
            Book book = bookController.searchBookByName(lblPBook3.getText());
            if (book != null && book.getId() != null) {
                returnBook(book.getId());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Book not found",
                        "Unable to find book with name: " + lblPBook3.getText());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to process return",
                    "An error occurred: " + e.getMessage());
        }
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

            // Add null check for bookBorrows
            if (bookBorrows == null || bookBorrows.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "No pending books found",
                        "There are no pending books for this member.");
                return;
            }

            System.out.println(bookBorrows);
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
                targetBorrow.setReturnedDate(LocalDate.now().toString());

                // Update the book's availability status
                Book book = bookController.searchBook(bookId);

                // Add null check for book
                if (book == null) {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Book not found",
                            "Could not find the book with ID: " + bookId);
                    return;
                }

                book.setAvailability_status(true);
                bookController.updateBook(book);

                boolean success = bookBorrowController.updateBookBorrow(targetBorrow);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Book returned successfully",
                            "The book has been marked as returned.");

                    // Refresh the pending books display
                    showPendingBooks();
                    populateTable();
                    updateBorrowLimitDisplay();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Failed to return book",
                            "Database error occurred while updating the record.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error returning book: " + e.getMessage());
            e.printStackTrace();
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