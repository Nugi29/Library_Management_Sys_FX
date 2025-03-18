package controller.fine;

import com.jfoenix.controls.JFXButton;
import controller.member.MemberController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Fine;
import model.Member;
import controller.fine.FineController.FineDetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class fineformController {

    @FXML
    private JFXButton btnCalculate;

    @FXML
    private JFXButton btnPay;

    @FXML
    private TableColumn<FineDetail, Date> coldue;

    @FXML
    private TableColumn<FineDetail, Double> colfine;

    @FXML
    private TableColumn<FineDetail, String> colid;

    @FXML
    private TableColumn<FineDetail, String> coltitle;

    @FXML
    private Label lblID;

    @FXML
    private Label lblLate;

    @FXML
    private Label lblName;

    @FXML
    private Label lblOut;

    @FXML
    private TextField txtId;

    @FXML
    private TableView<FineDetail> tblview;

    private MemberController memberController = new MemberController();
    private FineController fineController = new FineController();

    private ObservableList<FineDetail> fineDetailsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        colid.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        coltitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        coldue.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colfine.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));

        // Set table data source
        tblview.setItems(fineDetailsList);

        // Add selection mode
        tblview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Add listener for selection change
        tblview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Update the pay button status based on selection
            btnPay.setDisable(tblview.getSelectionModel().getSelectedItems().isEmpty());
        });

        // Initialize Pay button as disabled
        btnPay.setDisable(true);
    }

    @FXML
    void btnCalculateOnAction(ActionEvent event) {
        // Clear previous selections and data
        tblview.getSelectionModel().clearSelection();
        fineDetailsList.clear();

        // Clear previous member information
        clearMemberInfo();

        // Get member details and load fines
        loadMemberDetails();
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
        // Get selected items
        ObservableList<FineDetail> selectedItems = tblview.getSelectionModel().getSelectedItems();

        if (selectedItems.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select at least one fine to pay");
            return;
        }

        // Extract fine IDs
        List<Integer> fineIds = new ArrayList<>();
        double totalAmount = 0;

        for (FineDetail item : selectedItems) {
            if (item.getFineId() > 0) {
                fineIds.add(item.getFineId());
            } else {
                // If fine doesn't exist in database yet, create it first
                Fine fine = new Fine();
                fine.setAmount(item.getFineAmount());
                fine.setDate_count(String.valueOf(item.getDaysLate()));
                fine.setBookBorrowId(item.getBorrowId());
                fine.setAdminId(1);  // Default admin ID, replace with actual logged-in admin ID

                if (fineController.createFine(fine)) {
                    fineIds.add(fine.getId());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to create fine record");
                    return;
                }
            }
            totalAmount += item.getFineAmount();
        }

        // Format total amount with 2 decimal places
        String formattedAmount = String.format("%.2f", totalAmount);

        // Confirm payment
        boolean confirmed = showConfirmation(
                "Confirm Payment",
                "Are you sure you want to record payment of Rs. " + formattedAmount + "?"
        );

        if (!confirmed) {
            return;
        }

        // Record payment
        boolean success = fineController.recordFinePayment(
                fineIds,
                1  // Default admin ID, replace with actual logged-in admin ID
        );

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment recorded successfully");
            // Refresh the data
            loadMemberDetails();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to record payment");
        }
    }

    private void loadMemberDetails() {
        if (txtId.getText() == null || txtId.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Field", "Please enter a member ID");
            return;
        }

        String memberId = txtId.getText().toUpperCase().trim();
        Member member = memberController.searchMember(memberId);

        if (member != null) {
            // Display member info
            lblID.setText(member.getId());
            lblName.setText(member.getName());

            // Get borrow stats
            int[] stats = fineController.getMemberBorrowStats(memberId);
            lblOut.setText(String.valueOf(stats[0]));  // Books out
            lblLate.setText(String.valueOf(stats[1]));  // Books late

            // Get total outstanding fine amount
            double totalFineAmount = fineController.getTotalFineAmount(memberId);
            System.out.println(totalFineAmount);
            lblOut.setText(String.format("%.2f", totalFineAmount));

            // Get late books with fine details and populate the table
            List<FineDetail> fineDetails = fineController.getLateBookDetails(memberId);
            fineDetailsList.addAll(fineDetails);

            if (fineDetailsList.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Fines", "This member has no late books or fines");
                btnPay.setDisable(true);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Member Not Found", "No member found with ID: " + memberId);
            clearMemberInfo();
        }
    }

    // Helper method to clear member information
    private void clearMemberInfo() {
        lblID.setText("");
        lblName.setText("");
        lblOut.setText("");
        lblLate.setText("");
        btnPay.setDisable(true);
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method for confirmation dialogs
    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}