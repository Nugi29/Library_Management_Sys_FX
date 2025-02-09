package controller.member;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Member;

import java.util.List;

public class MemberformController {

    @FXML
    private JFXButton btnAddMember;

    @FXML
    private TableColumn<Member, String> coladdress;

    @FXML
    private TableColumn<Member, String> colcontact;

    @FXML
    private TableColumn<Member, String> colemail;

    @FXML
    private TableColumn<Member, String> colid;

    @FXML
    private TableColumn<Member, String> colname;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Member> tblUserview;



    private final MemberController memberController = new MemberController();

    @FXML
    public void initialize() {
        populateTable();
        tblUserview.setOnMouseClicked(this::tblUserviewOnMouseClicked);
    }



    @FXML
    void btnAddMemberOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty()
                || txtEmail.getText().isEmpty() || txtContact.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields before adding a member.");
            return;
        }

        Member member = new Member( txtId.getText(),
                                    txtName.getText(),
                                    txtAddress.getText(),
                                    txtEmail.getText(),
                                    txtContact.getText()
        );

        boolean success;

        if (btnAddMember.getText().equals("Update Member") ) {
            success = memberController.updateMember(member);
        } else {
            success = memberController.addMember(member);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Member successfully saved!");
            clearFields();
            populateTable();
            btnAddMember.setText("Add Member");
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }
    }

    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) {
        String searchText = txtSearch.getText().trim();
        if (searchText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter a search query.");
            return;
        }

        List<Member> members = memberController.getAll();
        ObservableList<Member> filteredList = FXCollections.observableArrayList();

        for (Member member : members) {
            if (member.getId().toUpperCase().contains(searchText.toUpperCase()) ||
                    member.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(member);
            }
        }
        tblUserview.setItems(filteredList);
    }

    private void populateTable() {
        ObservableList<Member> memberList = FXCollections.observableArrayList(memberController.getAll());
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblUserview.setItems(memberList);
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtContact.clear();
        btnAddMember.setText("Add Member");
    }

    @FXML
    void tblUserviewOnMouseClicked(MouseEvent event) {
        Member selectedMember = tblUserview.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            txtId.setText(selectedMember.getId());
            txtName.setText(selectedMember.getName());
            txtAddress.setText(selectedMember.getAddress());
            txtEmail.setText(selectedMember.getEmail());
            txtContact.setText(selectedMember.getContact());
            btnAddMember.setText("Update Member");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }

    public void btnDeleteMemberOnAction(ActionEvent actionEvent) {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a member to delete.");
            return;
        }

        boolean success = memberController.deleteMember(id);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Member successfully deleted!");
            clearFields();
            populateTable();
        } else {
            showAlert(Alert.AlertType.ERROR, "Operation failed. Try again.");
        }

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();

    }
}
