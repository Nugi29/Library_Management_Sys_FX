package controller.bookborrow;

import controller.DashboardFormController;
import controller.member.MemberController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Member;

import java.io.IOException;
import java.net.URL;

public class bookborrowloginformController {

    @FXML
    public AnchorPane FormAnchor;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSelect;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTp;

    @FXML
    private TextField txtSearch;

    public static Member[] memberar = new Member[1];

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSearch.clear();
        lblName.setText("Name  - ");
        lblTp.setText("Contact - ");
    }

    @FXML
    void btnSelectOnAction(ActionEvent event) throws IOException {


        if (txtSearch == null) {
            lblName.setText("Enter Member ID to continue");
            return;
        }else {

            Member member = new MemberController().searchMember(txtSearch.getText().trim().toUpperCase());
            memberar[0] = member;

            URL resource = this.getClass().getResource("/view/bookborrowform.fxml");
            assert resource != null;
            Parent load = FXMLLoader.load(resource);
            FormAnchor.getChildren().clear();
            FormAnchor.getChildren().add(load);

        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

        Member member = new MemberController().searchMember(txtSearch.getText().trim().toUpperCase());
        if (member != null) {
            lblName.setText("Name  - "+member.getName());
            lblTp.setText("Contact - "+member.getContact());
        } else {
            lblName.setText("Member not found");

        }

    }

}
